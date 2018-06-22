package com.happy3w.etl.copydb.model.type;

import com.happy3w.etl.copydb.model.ColumnDefine;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class DecimalDataTypeAdapter extends AbstractDataTypeAdapter {

    public DecimalDataTypeAdapter() {
        super(Types.DECIMAL, "decimal");
    }

    @Override
    public void setValue(int index, Object value, PreparedStatement ps) throws SQLException {
        if (value instanceof BigDecimal) {
            ps.setBigDecimal(index, (BigDecimal) value);
        } else if (value instanceof Number) {
            double d = ((Number) value).doubleValue();
            ps.setDouble(index, d);
        } else if (value == null) {
            ps.setNull(index, code);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public String createColumnSql(ColumnDefine columnDefine) {
        int size = columnDefine.getSize();
        if (size <= 0) {
            size = 20;
        }
        return "DECIMAL(" + size + ",2)" + (columnDefine.isNullable() ? "" : " NOT NULL");
    }
}