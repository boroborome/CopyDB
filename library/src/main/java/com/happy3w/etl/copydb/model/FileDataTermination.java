package com.happy3w.etl.copydb.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.List;

@Slf4j
@Getter
@Setter
public class FileDataTermination extends AbstractDataTermination {
    public static final String Type = "file";

    private String fileName;
    private List<DataSetValue> dataSetValues;

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
    public void save(DataSetValue dataSetValue, IConfigSuppler configSuppler) {

    }

    @Override
    public DataSetValue read(String dataSetName, IConfigSuppler configSuppler) {
        return null;
    }
}
