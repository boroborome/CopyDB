package com.happy3w.etl.copydb.model.type;

import com.happy3w.etl.copydb.model.ColumnDefine;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class NvarcharDataTypeAdapter extends AbstractDataTypeAdapter {

    public NvarcharDataTypeAdapter() {
        super(Types.NVARCHAR, "nvarchar");
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
        int size = columnDefine.getSize();
        if (size <= 0) {
            size = 255;
        }
        return "NVARCHAR(" + size + ")" + (columnDefine.isNullable() ? "" : " NOT NULL");
    }

}
