package com.happy3w.etc.copydb.app;

import com.happy3w.etl.copydb.app.CopyDbCommandLineRunner;
import com.happy3w.etl.copydb.model.AbstractDataTermination;
import com.happy3w.etl.copydb.model.DataSetValue;
import com.happy3w.etl.copydb.model.DataTerminationFactory;
import com.happy3w.etl.copydb.model.EnvironmentConfigSuppler;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Rollback
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestAppConfig.class)
@ActiveProfiles("test")
@TestPropertySource(properties = {
        "from=file:test-data-temp/output.json",
        "to=db:src1",
        "datasets=students"})
public class CopyDBApplicationFileToDbTest {
    @Autowired
    private Environment environment;

    private static File outputDir = new File("test-data-temp");
    private static File inputOrgFile = new File("test-data-temp/output.json");

    @Before
    public void setup() throws IOException {
        outputDir.mkdirs();
        InputStream inputOrgFileStream = CopyDBApplicationFileToDbTest.class
                .getClassLoader()
                .getResourceAsStream("test-data/output.json");
        FileOutputStream fop = new FileOutputStream(inputOrgFile);
        byte[] buffer = new byte[inputOrgFileStream.available()];
        inputOrgFileStream.read(buffer);
        fop.write(buffer);
        fop.flush();
        fop.close();
        inputOrgFileStream.close();
    }

    @Test
    public void should_copy_from_file_to_file_success() throws Exception {
        CopyDbCommandLineRunner copyDbCommandLineRunner = new CopyDbCommandLineRunner(environment);
        copyDbCommandLineRunner.run();

        EnvironmentConfigSuppler configSuppler = new EnvironmentConfigSuppler(environment);
        AbstractDataTermination databaseTermination = DataTerminationFactory.newInstanceByTerminationStr("db:src1", configSuppler);
        DataSetValue students = databaseTermination.read("students", configSuppler);
        Assert.assertNotNull(students);
        Assert.assertEquals(2, students.getValues().size());
        Assert.assertEquals(2, students.getColumns().size());
    }
}
