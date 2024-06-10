package com.mixedcs.academy.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "academy")
public class Academy extends BaseEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    private int classId;

    @NotBlank(message="Name Must Not Be Blank!")
    @Size(min=3, message="Name Must Be At Least 3 Characters Long!")
    private String name;

    @OneToMany(mappedBy = "academy", fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST,targetEntity = Person.class)
    private Set<Person> persons;
}
