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
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public static FileDataTermination fromJson(String json, String fileName) {
        if (StringUtils.isEmpty(json))
            return null;
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

    private List<DataSetValue> loadDataSetValues(String fileName) throws IOException {
        File jsonFile = new File(fileName);
        if (!jsonFile.exists()) {
            return new ArrayList<>();
        }

        FileInputStream jsonStream = new FileInputStream(fileName);
        ObjectMapper map = new ObjectMapper();
        JavaType parametricListType = map.getTypeFactory().constructParametricType(List.class, DataSetValue.class);

        return map.readValue(jsonStream, parametricListType);
    }

    @Override
    public void close() throws IOException {
        if (!changed) {
            return;
        }
        File outputFile = new File(fileName);
//        if (!outputFile.exists()) {
//            outputFile.createNewFile();
//        }

        ObjectMapper map = new ObjectMapper();
        map.writeValue(outputFile, dataSetValues);

    }
}
