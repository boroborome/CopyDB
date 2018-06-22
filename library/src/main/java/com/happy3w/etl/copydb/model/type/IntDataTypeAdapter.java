package com.happy3w.etl.copydb.model.type;

import com.happy3w.etl.copydb.model.ColumnDefine;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class IntDataTypeAdapter extends AbstractDataTypeAdapter {

    public IntDataTypeAdapter() {
        super(Types.INTEGER, "int");
    }

    @Override
    public void setValue(int index, Object value, PreparedStatement ps) throws SQLException {
        if (value instanceof Number) {
            int d = ((Number) value).intValue();
            ps.setInt(index, d);
        } else if (value == null) {
            ps.setNull(index, code);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public String createColumnSql(ColumnDefine columnDefine) {
        return "int" + (columnDefine.isNullable() ? "" : " NOT NULL");
    }
}