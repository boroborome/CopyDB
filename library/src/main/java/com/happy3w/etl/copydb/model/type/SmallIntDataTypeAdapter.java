package com.happy3w.etl.copydb.model.type;

import com.happy3w.etl.copydb.model.ColumnDefine;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class SmallIntDataTypeAdapter extends AbstractDataTypeAdapter {

    public SmallIntDataTypeAdapter() {
        super(Types.SMALLINT, "small int");
    }

    @Override
    public void setValue(int index, Object value, PreparedStatement ps) throws SQLException {
        if (value instanceof Number) {
            short d = ((Number) value).shortValue();
            ps.setShort(index, d);
        } else if (value == null) {
            ps.setNull(index, code);
        } else {
            throw new UnsupportedOperationException();
        }
    }
    @Override
    public String createColumnSql(ColumnDefine columnDefine) {
        return "smallint" + (columnDefine.isNullable() ? "" : " NOT NULL");
    }
}