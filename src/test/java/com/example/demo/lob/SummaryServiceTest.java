package com.example.demo.lob;

import static java.util.stream.Collectors.joining;
import static java.util.stream.LongStream.range;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.lob.SummaryService.SummaryAndContentEntityRepository;

@SpringBootTest
@Order(1)
public class SummaryServiceTest {

    @Autowired
    SummaryAndContentEntityRepository summaryAndContentEntityRepository;

    @Autowired
    SummaryService summaryService;

    @Test
    void test() {

        var id = createEntity();

        String newSummary1 = "long string 1";
        summaryService.updateSummaryWithoutFetchingContent(id, newSummary1);
        assertThat(summaryAndContentEntityRepository.findById(id).orElseThrow().getSummary()).isEqualTo(newSummary1);

        String newSummary2 = "long string 2";
        summaryService.updateSummary(id, newSummary2);
        assertThat(summaryAndContentEntityRepository.findById(id).orElseThrow().getSummary()).isEqualTo(newSummary2);
    }

    private UUID createEntity() {
        var entity = new SummaryAndContentEntity();
        String longString = range(0, 1234567L).mapToObj(Long::toString).collect(joining());
        entity.setContent(longString);
        return summaryAndContentEntityRepository.save(entity).getId();
    }

}
