package com.happy3w.etl.copydb.model.type;

import com.happy3w.etl.copydb.model.ColumnDefine;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TimestampDataTypeAdapter extends AbstractDataTypeAdapter {

    public TimestampDataTypeAdapter() {
        super(Types.TIMESTAMP, "timestamp");
    }

    @Override
    public void setValue(int index, Object value, PreparedStatement ps) throws SQLException {
        if (value instanceof Number) {
            long d = ((Number) value).longValue();
            ps.setDate(index, new Date(d));
        } else if (value instanceof String) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            java.util.Date d = null;
            try {
                d = format.parse((String) value);
            } catch (ParseException e) {
                throw new SQLException("Failed to convert data to date:" + value, e);
            }
            ps.setDate(index, new Date(d.getTime()));
        } else if (value == null) {
            ps.setNull(index, code);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public String createColumnSql(ColumnDefine columnDefine) {
        return "datetime" + (columnDefine.isNullable() ? "" : " NOT NULL");
    }
}