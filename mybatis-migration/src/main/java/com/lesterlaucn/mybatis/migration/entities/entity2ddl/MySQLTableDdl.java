package com.lesterlaucn.mybatis.migration.entities.entity2ddl;

import lombok.Builder;
import lombok.Data;

/**
 * Created by liuyuancheng on 2022/10/20  <br/>
 *
 * @author liuyuancheng
 */
@Builder
@Data
public class MySQLTableDdl implements ITableDdl {
    private MySQLTableDdl() {
    }

    @Override
    public String generateDdlClause() {
        return null;
    }

    public static class FieldDdl implements IFieldDdl {

        @Override
        public String generateDdlClause() {
            return null;
        }
    }

}
