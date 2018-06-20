package com.happy3w.etl.copydb.model;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class EnvironmentConfigSuppler implements IConfigSuppler {
    private final Binder binder;

    public EnvironmentConfigSuppler(Environment environment) {
        Iterable<ConfigurationPropertySource> it = ConfigurationPropertySources.get(environment);
        List<ConfigurationPropertySource> configurationPropertySources = new ArrayList<>();
        it.forEach(cps -> configurationPropertySources.add(cps));
        binder = new Binder(configurationPropertySources.toArray(new ConfigurationPropertySource[configurationPropertySources.size()]));
    }

    @Override
    public <T> T loadConfig(Class<T> configType, String name) {
        BindResult<T> result = binder.bind(name, Bindable.of(configType));
        return result.get();
    }

    @Override
    public <T> boolean loadConfig(T config, String name) {
        BindResult<T> result = binder.bind(name, Bindable.ofInstance(config));
        return result.isBound();
    }
}
