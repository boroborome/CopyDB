package com.happy3w.etc.copydb.app;

import com.happy3w.etl.copydb.app.CopyDbCommandLineRunner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
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
        "to=file:test-data-temp/output2.json",
        "datasets=students"})
public class CopyDBApplicationFileToFileTest {
    @Autowired
    private Environment environment;

    private static File inputOrgFile = new File("test-data-temp/output.json");

    private static File outputDir = new File("test-data-temp");
    private static File outputFile = new File("test-data-temp/output2.json");

    @Before
    public void setup() throws IOException {
        outputDir.mkdirs();
        InputStream inputOrgFileStream = CopyDBApplicationFileToFileTest.class
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
        outputFile.deleteOnExit();

        CopyDbCommandLineRunner copyDbCommandLineRunner = new CopyDbCommandLineRunner(environment);
        copyDbCommandLineRunner.run();
        Assert.assertTrue(outputFile.exists());
    }
}
