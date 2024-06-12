package com.example.demo.lob;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.example.demo.telemetry.Traced;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SummaryService {

    @Repository
    public interface SummaryAndContentEntityRepository extends JpaRepository<SummaryAndContentEntity, UUID> {

    }

    @Repository
    public interface SummaryEntityRepository extends JpaRepository<SummaryEntity, UUID> {

    }

    private final SummaryAndContentEntityRepository summaryAndContentEntityRepository;

    private final SummaryEntityRepository summaryEntityRepository;

    @Traced
    @Transactional
    public void updateSummary(UUID id, String summary) {

        var entity = summaryAndContentEntityRepository.findById(id).orElseThrow();
        entity.setSummary(summary);
    }

    @Traced
    @Transactional
    public void updateSummaryWithoutFetchingContent(UUID id, String summary) {

        var entity = summaryEntityRepository.findById(id).orElseThrow();
        entity.setSummary(summary);
    }

}
