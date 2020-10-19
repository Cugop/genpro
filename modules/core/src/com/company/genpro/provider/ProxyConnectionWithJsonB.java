package com.company.genpro.provider;

import com.haulmont.cuba.core.sys.jdbc.ProxyConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class ProxyConnectionWithJsonB extends ProxyConnection {

    public ProxyConnectionWithJsonB(Connection connection) {
        super(connection);
    }

    @Override
    public Statement createStatement() throws SQLException {
        return new ProxyStatementWithJsonB(connection.createStatement(), this);
    }

    @Override
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return new ProxyPreparedStatementWithJsonB(connection.prepareStatement(sql), this);
    }
}
