package com.happy3w.etl.copydb.model;

import lombok.Getter;

@Getter
public abstract class AbstractDataTermination {
    private final String type;

    protected AbstractDataTermination(String type) {
        this.type = type;
    }

    public abstract void initializeByString(String terminationString, IConfigSuppler configSuppler);
    public abstract void save(DataSetValue dataSetValue, IConfigSuppler configSuppler);
    public abstract DataSetValue read(String dataSetName, IConfigSuppler configSuppler);
    public abstract void close();
}
