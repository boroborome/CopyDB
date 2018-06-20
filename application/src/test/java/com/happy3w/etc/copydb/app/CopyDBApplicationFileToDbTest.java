package com.happy3w.etc.copydb.app;

import com.happy3w.etl.copydb.app.CopyDBApplication;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;

@Rollback
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CopyDBApplication.class)
@ActiveProfiles("test")
@TestPropertySource(properties = {"from=file:test-data/output.json", "to=db:src1"})
public class CopyDBApplicationFileToDbTest {
    @Autowired
    private CommandLineRunner copyDBApplication;

    @Test
    public void should_copy_from_file_to_db_success() throws Exception {
        File outputFile = new File("test-data/output2.json");
        outputFile.deleteOnExit();

        copyDBApplication.run();
        Assert.assertTrue(outputFile.exists());
    }
}
