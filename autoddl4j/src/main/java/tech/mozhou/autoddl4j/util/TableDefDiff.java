package tech.mozhou.autoddl4j.util;

import com.google.common.collect.Lists;
import tech.mozhou.autoddl4j.TableDef;
import lombok.NonNull;

import java.util.List;

/**
 * Created by liuyuancheng on 2022/11/4  <br/>
 *
 * @author liuyuancheng
 */
public class TableDefDiff {
    private TableDef tableDefInDataBase = null;
    private TableDef tableDefInCode = null;

    private List<String> ddlList = Lists.newArrayList();

    public TableDefDiff(@NonNull TableDef tableDefInDataBase, @NonNull TableDef tableDefInCode) {
        this.tableDefInDataBase = tableDefInDataBase;
        this.tableDefInCode = tableDefInCode;
    }

    public List<String> toDdl() {
        return ddlList;
    }
}
