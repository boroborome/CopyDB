package com.happy3w.etl.copydb.model.type;

import com.happy3w.etl.copydb.model.ColumnDefine;
import lombok.Getter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class AbstractDataTypeAdapter {
    protected final int code;
    protected final String name;


    protected AbstractDataTypeAdapter(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public abstract void setValue(int index, Object value, PreparedStatement ps) throws SQLException;

    private static Map<Integer, AbstractDataTypeAdapter> codeAdapterMap = new HashMap<>();
    private static Map<String, AbstractDataTypeAdapter> nameAdapterMap = new HashMap<>();
    private static Map<Class<? extends AbstractDataTypeAdapter>, AbstractDataTypeAdapter> typeAdapterMap = new HashMap<>();

    public static void registerDataType(AbstractDataTypeAdapter dataTypeAdapter) {
        codeAdapterMap.put(dataTypeAdapter.code, dataTypeAdapter);
        nameAdapterMap.put(dataTypeAdapter.name, dataTypeAdapter);
        typeAdapterMap.put(dataTypeAdapter.getClass(), dataTypeAdapter);
    }

    public static AbstractDataTypeAdapter parseByCode(int code) {
        return codeAdapterMap.get(code);
    }

    public static AbstractDataTypeAdapter parseByName(String name) {
        return nameAdapterMap.get(name);
    }

    public static<T extends AbstractDataTypeAdapter> T parseByType(Class<T> type) {
        return (T) typeAdapterMap.get(type);
    }

    public abstract String createColumnSql(ColumnDefine columnDefine);
}
