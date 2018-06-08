package com.happy3w.etc.copydb.convert;

import com.happy3w.etl.copydb.service.TransferService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@EnableAutoConfiguration
@SpringBootTest(classes = TransferService.class)
public class TransferServiceTest {
    @Test
    public void should_transfer_from_file_to_file_success() {

    }
}
