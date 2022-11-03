package com.lesterlaucn.autoddl4j.datasource;

import com.lesterlaucn.autoddl4j.datasource.definition.DbType;
import lombok.*;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * Created by liuyuancheng on 2022/10/27  <br/>
 *
 * @author liuyuancheng
 */
@Builder
@Getter
public class JdbcBound {

    private DbType dbType;

    @NonNull
    private String[] packageScan;

    @NonNull
    private String username;

    @NonNull
    private String password;

    @NonNull
    private String url;

    @NonNull
    private String driverClassName;

    public DriverManagerDataSource getDataSource(){
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUsername(username);
        dataSource.setUrl(url.trim());
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driverClassName);
        return dataSource;
    }

}
