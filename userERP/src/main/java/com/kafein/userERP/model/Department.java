package com.kafein.userERP.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Data
@ToString
@Table(name = "Departments")
public class Department implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private int numOfEmployees;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<User> users;

}
