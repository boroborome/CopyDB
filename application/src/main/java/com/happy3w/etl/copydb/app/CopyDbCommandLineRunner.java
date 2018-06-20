package com.happy3w.etl.copydb.app;

import com.happy3w.etl.copydb.model.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;

public class CopyDbCommandLineRunner implements CommandLineRunner {

    private final Environment environment;

    public CopyDbCommandLineRunner(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void run(String... args) throws Exception {
        EnvironmentConfigSuppler configSuppler = new EnvironmentConfigSuppler(environment);

        CustomInput input = configSuppler.loadConfig(CustomInput.class, "");
        if (input == null) {
            System.out.println("Please run this program like this:" +
                    "java -Dspring.profiles.active=export -jar copydb.jar --from=db:src1 --to=file:output.json --datasets=shpmntStatusStage,deliveryOdrStage" +
                    "or config params:[from,to,datasets] in config file application.properties");
            return;
        }
        System.out.println("Start to copy data using this params:" + input.toString());

        AbstractDataTermination from = DataTerminationFactory.newInstanceByTerminationStr(input.getFrom(), configSuppler);
        AbstractDataTermination to = DataTerminationFactory.newInstanceByTerminationStr(input.getTo(), configSuppler);
        for (String dataSetName : input.getDatasets()) {
            DataSetValue dataSetValue = from.read(dataSetName, configSuppler);
            to.save(dataSetValue, configSuppler);
        }

        from.close();
        to.close();
    }
}
