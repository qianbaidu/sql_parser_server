package com.qianbaidu.sqlparser.service.dto;

import javax.validation.constraints.NotNull;

/**
 * User: Alex
 * Date: 2019/10/12
 * Time: 11:44
 **/

public class MysqlParserDto {

    @NotNull
    private String sql;

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    @Override
    public String toString() {
        return "MysqlParserDto{" +
            "sql='" + sql + '\'' +
            '}';
    }
}
