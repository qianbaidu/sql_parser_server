package com.qianbaidu.sqlparser.web.rest.vo;

import com.alibaba.druid.stat.TableStat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Alex
 * Date: 2019/10/12
 * Time: 12:53
 **/

public class SqlParserVo {

    private List<String> table ;

    private List<Map<String, String>> column;

    private Map<String, String> alias;

    public SqlParserVo(List<String> table, List<Map<String, String>> column, Map<String, String> alias) {
        this.table = table;
        this.column = column;
        this.alias = alias;
    }

    public SqlParserVo() {
    }


    public List<String> getTable() {
        return table;
    }

    public void setTable(List<String> table) {
        this.table = table;
    }

    public List<Map<String, String>> getColumn() {
        return column;
    }

    public void setColumn(List<Map<String, String>> column) {
        this.column = column;
    }

    public Map<String, String> getAlias() {
        return alias;
    }

    public void setAlias(Map<String, String> alias) {
        this.alias = alias;
    }
}
