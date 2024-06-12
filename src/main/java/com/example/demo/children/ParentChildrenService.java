package com.example.demo.children;

import static java.util.stream.Collectors.joining;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import com.example.demo.telemetry.Traced;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ParentChildrenService {

    @Repository
    public static interface ParentRepository extends EntityGraphJpaRepository<Parent, UUID> {
    }

    private final ParentRepository parentRepository;

    @Traced
    @Transactional(readOnly = true)
    Collection<String> getParentsWithChildrenContents(Collection<UUID> parentIds) {

        var parents = parentRepository.findAllById(parentIds);

        return getContents(parents);
    }

    @Traced
    @Transactional(readOnly = true)
    Collection<String> getParentsWithChildrenContentsSingleFetch(Collection<UUID> parentIds) {

        var parents = Streamable
                .of(parentRepository.findAllById(parentIds, ParentEntityGraph.____().children().____.____()))
                .toList();

        return getContents(parents);
    }

    private List<String> getContents(List<Parent> parents) {
        return parents.stream().map(p -> p.getContent()
                + p.getChildren().stream().map(Child::getContent).collect(joining(""))).toList();
    }

}
