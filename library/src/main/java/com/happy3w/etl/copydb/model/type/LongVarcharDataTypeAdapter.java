package com.happy3w.etl.copydb.model.type;

import com.happy3w.etl.copydb.model.ColumnDefine;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class LongVarcharDataTypeAdapter extends AbstractDataTypeAdapter {

    public LongVarcharDataTypeAdapter() {
        super(Types.LONGVARCHAR, "long varchar");
    }

    @Override
    public void setValue(int index, Object value, PreparedStatement ps) throws SQLException {
        if (value == null) {
            ps.setNull(index, code);
        } else {
            ps.setString(index, String.valueOf(value));
        }
    }

    @Override
    public String createColumnSql(ColumnDefine columnDefine) {
        return "text" + (columnDefine.isNullable() ? "" : " NOT NULL");
    }
}