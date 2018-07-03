/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.happy3w.etl.copydb.model.type;

import com.happy3w.etl.copydb.model.ColumnDefine;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

/**
 * This Adatper is used to adapter smallint in database
 * @author boroborome
 */
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