package com.happy3w.etl.copydb.copydb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@SpringBootApplication
public class CopyDBApplication implements CommandLineRunner {

	@Autowired
	private Environment environment;

	public static void main(String[] args) {
		SpringApplication.run(CopyDBApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Iterable<ConfigurationPropertySource> it = ConfigurationPropertySources.get(environment);
		List<ConfigurationPropertySource> configurationPropertySources = new ArrayList<>();
		it.forEach(cps -> configurationPropertySources.add(cps));
		Binder binder = new Binder(configurationPropertySources.toArray(new ConfigurationPropertySource[configurationPropertySources.size()]));

		CustomInput input = readParameters(binder);
		if (input == null) {
			System.out.println("Please run this program like this:" +
					"java -Dspring.profiles.active=export -jar copydb.jar --from=db:src1 --to=file:output.json --datasets=shpmntStatusStage,deliveryOdrStage" +
					"or config params:[from,to,datasets] in config file application.properties");
			return;
		}
		System.out.println("Start to copy data using this params:" + input.toString());

//		String from = environment.getProperty("from");
//		String to = environment.getProperty("to");
//		String datasets = environment.getProperty("datasets");
//
//
//		System.out.println(Arrays.asList(args));
//		System.out.println(System.getenv("spring.profiles.active"));
//		System.out.println(System.getProperty("spring.profiles.active"));
//		System.out.println(Arrays.asList(environment.getActiveProfiles()));
//		System.out.println(environment.getProperty("from"));
//
//		DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
		BindResult<DataSource> result = binder.bind("db.src1", Bindable.ofInstance(DataSourceBuilder.create().build()));
		System.out.println(result.get().getConnection().isValid(1000));

	}

	private CustomInput readParameters(Binder binder) {
		try {
			BindResult<CustomInput> inputResult = binder.bind("", Bindable.of(CustomInput.class));
			return inputResult.get();

		} catch (NoSuchElementException e) {
			return null;
		}
	}
}
