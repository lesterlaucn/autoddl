package tech.mozhou.autoddl4j.target;

import tech.mozhou.autoddl4j.target.definition.DbType;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.persistence.Transient;
import java.util.Objects;

/**
 * Created by liuyuancheng on 2022/10/27  <br/>
 *
 * @author liuyuancheng
 */
@Builder
@Getter
public class JdbcBound {

    @Transient
    private volatile JdbcTemplate jdbcTemplate;

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

    public synchronized JdbcTemplate getJdbcTemplate() {
        if (Objects.isNull(jdbcTemplate)) {
            synchronized (JdbcBound.class) {
                jdbcTemplate = new JdbcTemplate(getDataSource());
            }
        }
        return jdbcTemplate;
    }

    private DriverManagerDataSource getDataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUsername(username);
        dataSource.setUrl(url.trim());
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driverClassName);
        return dataSource;
    }

}
