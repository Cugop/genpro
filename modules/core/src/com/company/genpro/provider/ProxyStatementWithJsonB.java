package com.company.genpro.provider;


import com.haulmont.cuba.core.sys.jdbc.ProxyStatement;

import java.sql.Connection;
import java.sql.Statement;

public class ProxyStatementWithJsonB extends ProxyStatement {

    public ProxyStatementWithJsonB(Statement statement, Connection connection) {
        super(statement, connection);
    }
}
