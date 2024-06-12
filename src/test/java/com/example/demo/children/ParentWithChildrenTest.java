package com.example.demo.children;

import static java.util.stream.IntStream.range;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.children.ParentChildrenService.ParentRepository;

@SpringBootTest
@Order(3)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ParentWithChildrenTest {

    @Autowired
    ParentRepository parentRepository;

    @Autowired
    ParentChildrenService service;

    @BeforeEach
    void beforeEach() {
        parentRepository.deleteAllInBatch();
    }

    @Test
    @Order(1)
    void testSingleFetch() {

        List<UUID> parentIds = createParentsWithChildren();

        service.getParentsWithChildrenContentsSingleFetch(parentIds);
    }

    @Test
    @Order(2)
    void testN1() {

        List<UUID> parentIds = createParentsWithChildren();

        service.getParentsWithChildrenContents(parentIds);
    }

    private List<UUID> createParentsWithChildren() {

        int nParents = 100;
        int nChildrenPerParent = 100;

        var parents = range(0, nParents).mapToObj(pI -> {
            var parent = new Parent();
            parent.getChildren().addAll(range(0, nChildrenPerParent).mapToObj(l -> new Child()).toList());
            return parent;
        }).toList();
        parents = parentRepository.saveAll(parents);
        return parents.stream().map(Parent::getId).toList();
    }

}
