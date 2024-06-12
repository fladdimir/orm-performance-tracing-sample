package com.example.demo.sequence;

import static java.util.stream.IntStream.range;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.example.demo.telemetry.Traced;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SequenceEntityService {

    @Repository
    public interface SequenceIdEntity1Repository extends JpaRepository<SequenceIdEntity1, Long> {
    }

    @Repository
    public interface SequenceIdEntity100Repository extends JpaRepository<SequenceIdEntity100, Long> {
    }

    private final SequenceIdEntity1Repository sequenceIdEntity1Repository;

    private final SequenceIdEntity100Repository sequenceIdEntity100Repository;

    @Traced
    @Transactional
    public void createEntities1(int n) {
        var entities = range(0, n).mapToObj(i -> new SequenceIdEntity1()).toList();
        sequenceIdEntity1Repository.saveAll(entities);
    }

    @Traced
    @Transactional
    public void createEntities100(int n) {
        var entities = range(0, n).mapToObj(i -> new SequenceIdEntity100()).toList();
        sequenceIdEntity100Repository.saveAll(entities);
    }

}
