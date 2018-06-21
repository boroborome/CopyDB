package com.happy3w.etl.copydb.configuration;

import com.happy3w.etl.copydb.model.AbstractDataTermination;
import com.happy3w.etl.copydb.model.DataTerminationFactory;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@ComponentScan({"com.happy3w.etl.copydb.convert"})
public class CopyDBAutoConfiguration {

    @Bean
    public BeanFactoryPostProcessor getDataTerminationFactoryInitializer() {
        return new BeanFactoryPostProcessor() {
            @Override
            public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
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
        };
    }
}