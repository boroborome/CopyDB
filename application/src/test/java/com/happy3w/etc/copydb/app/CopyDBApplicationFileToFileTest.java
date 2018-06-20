package com.happy3w.etc.copydb.app;

import com.happy3w.etl.copydb.app.CopyDbCommandLineRunner;
import org.junit.Assert;
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

@Rollback
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestAppConfig.class)
@ActiveProfiles("test")
@TestPropertySource(properties = {"from=file:test-data/output.json", "to=file:test-data/output2.json", "datasets=students"})
public class CopyDBApplicationFileToFileTest {
    @Autowired
    private Environment environment;

    @Test
    public void should_copy_from_file_to_file_success() throws Exception {
        File outputFile = new File("test-data/output2.json");
        outputFile.deleteOnExit();

        CopyDbCommandLineRunner copyDbCommandLineRunner = new CopyDbCommandLineRunner(environment);
        copyDbCommandLineRunner.run();
        Assert.assertTrue(outputFile.exists());
    }
}
