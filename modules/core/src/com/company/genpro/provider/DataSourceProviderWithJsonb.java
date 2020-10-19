package com.company.genpro.provider;

import com.google.common.base.Preconditions;
import com.haulmont.cuba.core.global.Stores;
import com.haulmont.cuba.core.sys.DataSourceProvider;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.jdbc.datasource.lookup.DataSourceLookup;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

import javax.annotation.Nullable;
import javax.sql.DataSource;
import java.util.Map;

public class DataSourceProviderWithJsonb extends DataSourceProvider {
    @Override
    public DataSource getDataSource(String storeName, @Nullable String jndiName) {
        return super.getDataSource(storeName,jndiName);
    }
    @Override
    protected DataSource getJndiDataSource(String jndiName) {
        Preconditions.checkNotNull(jndiName, "Jndi name is null");
        DataSourceLookup lookup = new JndiDataSourceLookup();
        return new ProxyDataSourceWithJsonB(lookup.getDataSource(jndiName));
    }
    @Override
    protected DataSource getApplicationDataSource(String storeName) {
        String actualStoreName = storeName == null ? Stores.MAIN : storeName;

        Map<String, String> dsParameters = getDataSourceParameters(actualStoreName);

        HikariConfig config = getConnectionPoolConfig(actualStoreName, dsParameters);

        return new ProxyDataSourceWithJsonB(new HikariDataSource(config));
    }
}
