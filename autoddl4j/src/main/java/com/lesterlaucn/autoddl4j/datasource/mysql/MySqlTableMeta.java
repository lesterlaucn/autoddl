package com.lesterlaucn.autoddl4j.datasource.mysql;

import com.lesterlaucn.autoddl4j.datasource.AbstractTableMeta;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by liuyuancheng on 2022/10/26  <br/>
 *
 * @author liuyuancheng
 */
@Slf4j
public class MySqlTableMeta extends AbstractTableMeta {

    public MySqlTableMeta(JdbcTemplate jdbcTemplate) {
        super.setJdbcTemplate(jdbcTemplate);
    }

    @Override
    public Boolean isTableExists(String tableName) {
        return null;
    }

    @Override
    public List<String> showTableNames(String databaseName) {
        if (StringUtils.isEmpty(databaseName)){
            try {
                final DataSource dataSource = getJdbcTemplate().getDataSource();
                Objects.requireNonNull(dataSource);
                final String url = dataSource.getConnection().getMetaData().getURL();
                Pattern pattern = Pattern.compile("/([a-zA-Z0-9_-]*?)\\?");
                Matcher matcher = pattern.matcher(url);
                if (matcher.find()){
                    databaseName = matcher.toMatchResult().group(1);
                }else{
                    throw new RuntimeException("error");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        final List<Map<String, Object>> resultInMaps = getJdbcTemplate().queryForList("show tables;");
        String finalDatabaseName = databaseName;
        return resultInMaps.stream().map(e -> e.get("Tables_in_" + finalDatabaseName).toString()).collect(Collectors.toList());
    }

    /**
     * show create table table_name
     *
     * @return
     */
    @Override
    public String showCreateTable(@NonNull String tableName) {
        if (this.isTableExists(tableName)) {
            getJdbcTemplate().queryForMap("show create table " + StringUtils.wrapIfMissing(tableName, '`'));
        }
        log.warn("Showing table {} failed, in case of dose not exists.", tableName);
        return null;
    }


}
