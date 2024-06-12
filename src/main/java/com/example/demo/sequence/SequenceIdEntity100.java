package com.example.demo.sequence;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@SequenceGenerator(sequenceName = "sequence_id_entity_100", name = "sequence_id_entity_100", allocationSize = 100)
public class SequenceIdEntity100 {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_id_entity_100")
    private Long id;

}
