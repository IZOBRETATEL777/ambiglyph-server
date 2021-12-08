package com.izobretatel777.ambiglyphserver.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="homoglyph")
public class Homoglyph {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "original")
    private char original;

    @Column(name = "spoofed")
    private char spoofed;

}
