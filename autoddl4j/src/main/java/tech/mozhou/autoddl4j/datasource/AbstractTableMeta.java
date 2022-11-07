package tech.mozhou.autoddl4j.datasource;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Objects;

/**
 * Created by liuyuancheng on 2022/10/26  <br/>
 *
 * @author liuyuancheng
 */
public abstract class AbstractTableMeta {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public JdbcTemplate getJdbcTemplate() {
        Objects.requireNonNull(jdbcTemplate, "jdbcTemplate 未初始化");
        return jdbcTemplate;
    }

    /**
     * 表是否存在
     *
     * @param tableName
     * @return
     */
    public abstract Boolean isTableExists(String tableName);

    public List<String> showTableNames(){
        return showTableNames(null);
    }

    /**
     * 表名
     *
     * @param databaseName
     * @return
     */
    public abstract List<String> showTableNames(String databaseName);

    /**
     * 返回表结构
     *
     * @param tableName 表名称
     * @return
     */
    public abstract String showCreateTable(String tableName);

}
