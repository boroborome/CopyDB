package com.happy3w.etl.copydb.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@ComponentScan({"com.happy3w.etl.copydb.convert", "com.happy3w.etl.copydb.service"})
public class CopyDBAutoConfiguration {

}