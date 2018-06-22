package com.happy3w.etl.copydb.model;

import lombok.Getter;

import java.io.IOException;
import java.sql.SQLException;

@Getter
public abstract class AbstractDataTermination {
    private final String type;

    protected AbstractDataTermination(String type) {
        this.type = type;
    }

    public abstract void initializeByString(String terminationString, IConfigSuppler configSuppler);
    public abstract void save(DataSetValue dataSetValue, IConfigSuppler configSuppler) throws SQLException;
    public abstract DataSetValue read(String dataSetName, IConfigSuppler configSuppler) throws SQLException;
    public abstract void close() throws IOException;
}
