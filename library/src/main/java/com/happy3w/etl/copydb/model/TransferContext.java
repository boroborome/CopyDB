package com.happy3w.etl.copydb.model;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class TransferContext {
    private Map<String, Map<String, DataSetValue>> fileValueMap = new HashMap<>();
}
