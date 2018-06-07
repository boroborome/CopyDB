package com.happy3w.etl.copydb.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CustomInput {
    private String from;
    private String to;
    private List<String> datasets;

    @Override
    public String toString() {
        return "{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", datasets=" + datasets +
                '}';
    }
}
