package com.qianbaidu.sqlparser.web.rest;

import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlSelectQueryBlock;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.qianbaidu.sqlparser.common.CommonResponse;
import com.qianbaidu.sqlparser.common.ResponseEnum;
import com.qianbaidu.sqlparser.service.dto.MysqlParserDto;
import com.qianbaidu.sqlparser.web.rest.vo.SqlParserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.LoggerFactory;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlSchemaStatVisitor;
import com.alibaba.druid.stat.TableStat;

import java.util.*;

/**
 * User: Alex
 * Date: 2019/10/12
 * Time: 11:36
 **/

@Api(value = "sql解析", tags = "sql解析Api")
@RestController
@RequestMapping("/parser/")
public class SqlParser {

    private static final Logger logger = LoggerFactory.getLogger(SqlParser.class);

    @ApiOperation(value = "mysql sql解析", notes = "sql解析,返回sql中tables、columns")
    @PostMapping("/mysql")
    public CommonResponse mysqlParser(@Valid @RequestBody MysqlParserDto mysqlParamers, HttpServletRequest request) {

        CommonResponse commonResponse = new CommonResponse();
        String sql = mysqlParamers.getSql();
        try{
            SqlParserVo parse = parse(sql);
            commonResponse.setResult(parse);
        } catch (Exception e){
            commonResponse.setCode(ResponseEnum.FAIL.getCode());
            commonResponse.setMessage(e.getMessage());
            return commonResponse;
        }

        commonResponse.setCode(ResponseEnum.SUCCESS.getCode());
        commonResponse.setMessage("success");
        return commonResponse;
    }


    public SqlParserVo parse(String sql) {

        MySqlStatementParser parser = new MySqlStatementParser(sql);
        List<SQLStatement> statementList = parser.parseStatementList();
        SQLStatement statemen = statementList.get(0);

        SqlParserVo sqlParserVo = new SqlParserVo();

        MySqlSchemaStatVisitor2 visitor = new MySqlSchemaStatVisitor2();
        statemen.accept(visitor);

        //alias
//        Map<String, String> aliasMap = visitor.getAliasMap();
//        sqlParserVo.setAlias(aliasMap);

        Map<String, String> aliasMap =  visitor.getNewAliasMap();
        sqlParserVo.setAlias(aliasMap);

        List<String> tables = new ArrayList<String>();

        // tables
        for (TableStat.Name name : visitor.getTables().keySet()) {
            tables.add(name.getName());
        }
        sqlParserVo.setTable(tables);

        // columns
        List<Map<String, String>> columns = new ArrayList<Map<String, String>>();
        for (TableStat.Column column : visitor.getColumns()) {
            // System.out.println( column.toString() );
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("column_name", column.getName());
            map.put("table_name", column.getTable());
            map.put("is_select", String.valueOf(column.isSelect()));
            columns.add(map);
        }
        sqlParserVo.setColumn(columns);

        logger.info("SQL: {} , Tables: {} , Columns: {} ,Alias: {}", sql, tables, columns, aliasMap);
        return sqlParserVo;
    }


    private static class MySqlSchemaStatVisitor2 extends MySqlSchemaStatVisitor {
        private Map<String, String> aliasMap = new HashMap<String, String>();

        @Override
        public boolean visit(MySqlSelectQueryBlock x) {
            x.getSelectList().stream().forEach(e -> {
                String key = "";
                if (e.getAlias() != null) {
                    key = e.getAlias().toString();
                }
                aliasMap.put(key, e.getExpr().toString());
            });
            return true;
        }

        public Map<String, String> getNewAliasMap() {
            return aliasMap;
        }
    }

}
