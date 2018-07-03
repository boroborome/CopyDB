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
package com.happy3w.etl.copydb.configuration;

import com.happy3w.etl.copydb.model.AbstractDataTermination;
import com.happy3w.etl.copydb.model.DataTerminationFactory;
import com.happy3w.etl.copydb.model.type.AbstractDataTypeAdapter;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * This Configuration is used to initialize DataTermination and DataTypeAdapter
 * @author boroborome
 */
@Slf4j
@Configuration
@ComponentScan({"com.happy3w.etl.copydb.convert"})
public class CopyDBAutoConfiguration {

    @Bean
    public BeanFactoryPostProcessor getDataTerminationFactoryInitializer() {
        return new BeanFactoryPostProcessor() {
            @Override
            public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
                initializeDataTermination();
                initializeDataTypeAdapter();
            }
        };
    }

    private void initializeDataTermination() {
        Reflections reflections = new Reflections(AbstractDataTermination.class.getPackage().getName());
        reflections.getSubTypesOf(AbstractDataTermination.class).forEach(c -> {
            try {
                AbstractDataTermination instance = c.newInstance();
                DataTerminationFactory.register(instance.getType(), c);
            } catch (Exception e) {
                log.error("Failed in registering DataTermination:" + c.getName(), e);
            }
        });
    }

    private void initializeDataTypeAdapter() {
        Reflections reflections = new Reflections(AbstractDataTypeAdapter.class.getPackage().getName());
        reflections.getSubTypesOf(AbstractDataTypeAdapter.class).forEach(c -> {
            try {
                AbstractDataTypeAdapter instance = c.newInstance();
                AbstractDataTypeAdapter.registerDataType(instance);
            } catch (Exception e) {
                log.error("Failed in registering AbstractDataTypeAdapter:" + c.getName(), e);
            }
        });
    }
}