package com.happy3w.etl.copydb.model.type;

import com.happy3w.etl.copydb.model.ColumnDefine;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class BigintDataTypeAdapter extends AbstractDataTypeAdapter {
    public static int Type = Types.BIGINT;
    public BigintDataTypeAdapter() {
        super(Type, "bigint");
    }

    @Override
    public void setValue(int index, Object value, PreparedStatement ps) throws SQLException {
        if (value instanceof Number) {
            long d = ((Number) value).longValue();
            ps.setLong(index, d);
        } else if (value == null) {
            ps.setNull(index, code);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public String createColumnSql(ColumnDefine columnDefine) {
        return "BIGINT" + (columnDefine.isAutoIncrease() ? " IDENTITY": "");
    }
}