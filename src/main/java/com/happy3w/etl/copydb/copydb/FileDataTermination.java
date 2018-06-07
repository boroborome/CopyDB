package com.happy3w.etl.copydb.copydb;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileDataTermination extends AbstractDataTermination {
    public static final String Type = "file";

    private String fileName;

    protected FileDataTermination() {
        super(Type);
    }
}
