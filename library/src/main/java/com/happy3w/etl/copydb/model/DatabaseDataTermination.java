package com.happy3w.etl.copydb.model;

import javax.sql.DataSource;

public class DatabaseDataTermination extends AbstractDataTermination {
    public static final String Type = "db";

    private final DataSource dataSource;

    public DatabaseDataTermination(DataSource dataSource) {
        super(Type);
        this.dataSource = dataSource;
    }

    @Override
    public void initializeByString(String terminationString, IConfigSuppler configSuppler) {

    }

    @Override
    public void save(DataSetValue dataSetValue, IConfigSuppler configSuppler) {

    }

    @Override
    public DataSetValue read(String dataSetName, IConfigSuppler configSuppler) {
        return null;
    }

    @Override
    public void close() {

    }
}
