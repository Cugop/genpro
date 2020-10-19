package com.company.genpro.provider;

import com.haulmont.cuba.core.sys.jdbc.ProxyDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ProxyDataSourceWithJsonB extends ProxyDataSource {
    public ProxyDataSourceWithJsonB(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Connection getConnection() throws SQLException {
        return new ProxyConnectionWithJsonB(dataSource.getConnection());
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return new ProxyConnectionWithJsonB(dataSource.getConnection(username, password));
    }
}
