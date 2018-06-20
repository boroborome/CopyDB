package com.happy3w.etl.copydb.model;

public interface IConfigSuppler {
    <T> T loadConfig(Class<T> configType, String name);

    <T> boolean loadConfig(T config, String name);
}
