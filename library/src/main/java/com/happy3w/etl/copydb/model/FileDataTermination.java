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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This DataTermination is used to save or read data to json file
 * @author boroborome
 */
@Slf4j
@Getter
@Setter
public class FileDataTermination extends AbstractDataTermination {
    public static final String Type = "file";

    private String fileName;
    private List<DataSetValue> dataSetValues;
    private boolean changed = false;
    public FileDataTermination() {
        super(Type);
    }

    public String getJson() {
        ObjectMapper map = new ObjectMapper();
        try {
            return map.writeValueAsString(dataSetValues);
        } catch (JsonProcessingException e) {
            log.error("Failed to format object to json.", e);
            throw new IllegalArgumentException(
                    MessageFormat.format("FileDataTermination({0}) can't be convert to json.", fileName),
                    e);
        }
    }

    @Override
    public String toString() {
        return fileName;
    }

    @Override
    public void initializeByString(String terminationString, IConfigSuppler configSuppler) {
        this.fileName = terminationString;
    }

    @Override
    public void save(DataSetValue dataSetValue, IConfigSuppler configSuppler) {
        DataSetValue orgDataSetValue = findDataSetValueByTableName(dataSetValue.getTable());
        if (orgDataSetValue == null) {
            this.dataSetValues.add(dataSetValue);
        } else {
            orgDataSetValue.getValues().addAll(dataSetValue.getValues());
        }
        changed = true;
    }

    private List<DataSetValue> getAllDataSetValues() {
        if (dataSetValues == null) {
            try {
                dataSetValues = loadDataSetValues(fileName);
            } catch (IOException e) {
                log.error("Failed to load file:" + fileName, e);
                return null;
            }
        }
        return dataSetValues;
    }

    private DataSetValue findDataSetValueByDataSetName(String dataSetName, IConfigSuppler configSuppler) {
        DataSetDefine dataSetDefine = configSuppler.loadConfig(DataSetDefine.class, DataSetDefine.dataSetDefineName(dataSetName));
        return findDataSetValueByTableName(dataSetDefine.getTable());
    }

    private DataSetValue findDataSetValueByTableName(String tableName) {
        List<DataSetValue> allDataSetValues = getAllDataSetValues();

        DataSetValue dataSetValue = null;
        for (DataSetValue value : allDataSetValues) {
            if (Objects.equals(tableName, value.getTable())) {
                dataSetValue = value;
                break;
            }
        }
        return dataSetValue;
    }

    @Override
    public DataSetValue read(String dataSetName, IConfigSuppler configSuppler) {
        return findDataSetValueByDataSetName(dataSetName, configSuppler);
    }

    private static List<DataSetValue> loadDataSetValues(String fileName) throws IOException {
        File jsonFile = new File(fileName);
        if (!jsonFile.exists()) {
            return new ArrayList<>();
        }

        FileInputStream jsonStream = new FileInputStream(fileName);
        return loadDataSetValues(jsonStream);
    }

    private static List<DataSetValue> loadDataSetValues(InputStream jsonFileInputStream) throws IOException {
        ObjectMapper map = new ObjectMapper();
        JavaType parametricListType = map.getTypeFactory().constructParametricType(List.class, DataSetValue.class);

        return map.readValue(jsonFileInputStream, parametricListType);
    }

    @Override
    public void close() throws IOException {
        if (!changed || StringUtils.isEmpty(fileName)) {
            return;
        }
        File outputFile = new File(fileName);

        ObjectMapper map = new ObjectMapper();
        map.writeValue(outputFile, dataSetValues);

    }

    public static FileDataTermination fromJson(String json, String fileName) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        ObjectMapper map = new ObjectMapper();
        JavaType parametricUpgradeDataType = map.getTypeFactory().constructParametricType(List.class, DataSetValue.class);

        List<DataSetValue> objResult = null;
        try {
            objResult = map.readValue(json, parametricUpgradeDataType);
        } catch (java.io.IOException e) {
            log.error("Failed to convert json to object. json is", e);
        }

        FileDataTermination fileDataTermination = new FileDataTermination();
        fileDataTermination.fileName = fileName;
        fileDataTermination.dataSetValues = objResult;
        return fileDataTermination;
    }

    public static FileDataTermination fromJsonFile(String jsonFileName) throws IOException {
        if (StringUtils.isEmpty(jsonFileName)) {
            return null;
        }

        FileDataTermination fileDataTermination = new FileDataTermination();
        fileDataTermination.fileName = jsonFileName;
        fileDataTermination.dataSetValues = loadDataSetValues(jsonFileName);
        return fileDataTermination;
    }


    public static FileDataTermination fromJsonFile(InputStream jsonFileInputStream) throws IOException {
        if (jsonFileInputStream == null) {
            return null;
        }

        FileDataTermination fileDataTermination = new FileDataTermination();
        fileDataTermination.fileName = null;
        fileDataTermination.dataSetValues = loadDataSetValues(jsonFileInputStream);
        return fileDataTermination;
    }
}
