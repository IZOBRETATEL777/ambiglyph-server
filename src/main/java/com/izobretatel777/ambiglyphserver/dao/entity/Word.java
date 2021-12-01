package com.izobretatel777.ambiglyphserver.dao.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "word")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Word {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "word", nullable = false)
    private String text;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_word",
            joinColumns = {@JoinColumn(name = "word_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")})
    private List<User> users;
}
