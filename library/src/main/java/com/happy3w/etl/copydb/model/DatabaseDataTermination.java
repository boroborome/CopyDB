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
package com.happy3w.etl.copydb.model;

import com.happy3w.etl.copydb.model.type.AbstractDataTypeAdapter;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This DataTermination is used to save or read data to database
 * @author boroborome
 */
public class DatabaseDataTermination extends AbstractDataTermination {
    public static final String Type = "db";

    private JdbcTemplate jdbcTemplate;

    public DatabaseDataTermination() {
        super(Type);
    }

    @Override
    public void initializeByString(String terminationString, IConfigSuppler configSuppler) {
        DataSource dataSource = DataSourceBuilder.create().build();
        boolean result = configSuppler.loadConfig(dataSource, "database." + terminationString);
        if (!result) {
            throw new IllegalArgumentException("Can't load datasource for :" + terminationString);
        }
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(DataSetValue dataSetValue, IConfigSuppler configSuppler) throws SQLException {
        List<ColumnDefine> columnDefines = getColumnDefines(dataSetValue.getTable());
        if (CollectionUtils.isEmpty(columnDefines)) {
            columnDefines = createTableByDataSet(dataSetValue);
        }

        List<ColumnDefine> dataColumnDefines = columnDefines;
        jdbcTemplate.batchUpdate(createInsertSql(dataSetValue.getTable(), dataColumnDefines),
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        Map<String, Object> currentObject = dataSetValue.getValues().get(i);
                        for (int columnIndex = 0; columnIndex < dataColumnDefines.size(); columnIndex++) {
                            ColumnDefine columnDefine = dataColumnDefines.get(columnIndex);
                            AbstractDataTypeAdapter typeAdapter = columnDefine.findAdapter();
                            typeAdapter.setValue(columnIndex + 1, currentObject.get(columnDefine.getName()), ps);
                        }
                    }

                    @Override
                    public int getBatchSize() {
                        return dataSetValue.getValues().size();
                    }
                });
    }

    private List<ColumnDefine> createTableByDataSet(DataSetValue dataSetValue) {
        StringBuilder sqlBuffer = new StringBuilder();
        sqlBuffer.append("create table ")
                .append(dataSetValue.getTable())
                .append("(");
        for (ColumnDefine columnDefine : dataSetValue.getColumns()) {
            AbstractDataTypeAdapter typeAdapter = columnDefine.findAdapter();
            sqlBuffer.append(columnDefine.getName())
                    .append(' ')
                    .append(typeAdapter.createColumnSql(columnDefine))
                    .append(',');
        }
        sqlBuffer.setLength(sqlBuffer.length() - 1);

        sqlBuffer.append(")");
        jdbcTemplate.update(sqlBuffer.toString());
        return dataSetValue.getColumns();
    }


    private String createInsertSql(String tableName, List<ColumnDefine> columnDefines) {
        StringBuilder sqlBuf = new StringBuilder();
        sqlBuf.append("insert into ")
                .append(tableName)
                .append("(");
        for (ColumnDefine columnDefine : columnDefines) {
            if (columnDefine.isAutoIncrease()) {
                continue;
            }
            sqlBuf.append(columnDefine.getName())
                    .append(',');
        }
        sqlBuf.setLength(sqlBuf.length() - 1);

        sqlBuf.append(") values(");
        for (ColumnDefine columnDefine : columnDefines) {
            if (columnDefine.isAutoIncrease()) {
                continue;
            }
            sqlBuf.append("?,");
        }
        sqlBuf.setLength(sqlBuf.length() - 1);

        sqlBuf.append(")");
        return sqlBuf.toString();
    }

    @Override
    public DataSetValue read(String dataSetName, IConfigSuppler configSuppler) throws SQLException {
        DataSetDefine dataSetDefine = configSuppler.loadConfig(DataSetDefine.class, DataSetDefine.dataSetDefineName(dataSetName));
        return DataSetValue.builder()
                .table(dataSetDefine.getTable())
                .columns(getColumnDefines(dataSetDefine.getTable()))
                .values(jdbcTemplate.queryForList(dataSetDefine.getSql()))
                .build();
    }

    private List<ColumnDefine> getColumnDefines(String tableName) throws SQLException {
        List<ColumnDefine> columnDefines = new ArrayList<>();
        DatabaseMetaData metaData = jdbcTemplate.getDataSource().getConnection().getMetaData();
        outputResultSet(metaData.getTypeInfo());
        ResultSet rs = metaData.getColumns(null, null, tableName.toUpperCase(), null);
        while (rs.next()) {
            int typeCode = rs.getInt("DATA_TYPE");
            AbstractDataTypeAdapter typeAdapter = AbstractDataTypeAdapter.parseByCode(typeCode);
            columnDefines.add(ColumnDefine.builder()
                    .name(rs.getString("COLUMN_NAME"))
                    .dataType(typeAdapter.getName())
                    .size(rs.getInt("COLUMN_SIZE"))
                    .nullable(rs.getBoolean("NULLABLE"))
                    .autoIncrease("yes".equalsIgnoreCase(rs.getString("IS_AUTOINCREMENT")))
                    .build());
        }
        return columnDefines;
    }

    private void outputResultSet(ResultSet rs) throws SQLException {
        List<String> columns = new ArrayList<>();
        int columnSize = rs.getMetaData().getColumnCount();
        for (int i = 1; i <= columnSize; i ++) {
            columns.add(rs.getMetaData().getColumnName(i));
        }

        System.out.println(columns);

        List<Object> values = new ArrayList<>();
        while (rs.next()) {
            values.clear();

            for (String column : columns) {
                values.add(rs.getString(column));
            }
            System.out.println(values);
        }
    }
    @Override
    public void close() {

    }
}
