package com.happy3w.etl.copydb.model;

public class DatabaseDataTermination extends AbstractDataTermination {
    public static final String Type = "db";

    private String fileName;

    public DatabaseDataTermination() {
        super(Type);
    }

}
