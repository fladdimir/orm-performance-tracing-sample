package com.example.demo.sequence;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.sequence.SequenceEntityService.SequenceIdEntity100Repository;
import com.example.demo.sequence.SequenceEntityService.SequenceIdEntity1Repository;

@SpringBootTest
@Order(2)
public class SequenceTest {

    @Autowired
    SequenceEntityService service;

    @Autowired
    SequenceIdEntity1Repository repository1;

    @Autowired
    SequenceIdEntity100Repository repository100;

    private static final int N = 200;

    @Test
    void test() {
        repository100.deleteAllInBatch();
        repository1.deleteAllInBatch();

        service.createEntities100(N);
        service.createEntities1(N);

        assertThat(repository100.count()).isEqualTo(N);
        assertThat(repository1.count()).isEqualTo(N);
    }

}
