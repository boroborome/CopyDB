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
package com.happy3w.etl.copydb.convert;

import com.happy3w.etl.copydb.convert.primitive.IPrimitiveConverter;
import com.happy3w.etl.copydb.convert.primitive.PrimitiveConverter;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * This Processor is used to initialize PrimitiveConverter
 * @author boroborome
 */
@Slf4j
@Component
public class ConvertFactoryProcessor implements BeanFactoryPostProcessor, Ordered {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        Reflections reflections = new Reflections(ConvertFactoryProcessor.class.getPackage().getName());
        reflections.getSubTypesOf(IPrimitiveConverter.class).forEach(c -> {
            try {
                IPrimitiveConverter instance = c.newInstance();
                PrimitiveConverter.getInstance().register(instance);
            } catch (Exception e) {
                log.error("Failed in registering Converter:{}", c.getName());
            }
        });
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
