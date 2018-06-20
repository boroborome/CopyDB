package com.happy3w.etl.copydb.model;

public abstract class AbstractDataTermination {
    private final String type;

    protected AbstractDataTermination(String type) {
        this.type = type;
    }

    public abstract void save(DataSetValue dataSetValue, IConfigSuppler configSuppler);
    public abstract DataSetValue read(String dataSetName, IConfigSuppler configSuppler);
}
