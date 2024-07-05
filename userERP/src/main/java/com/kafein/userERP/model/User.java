package com.kafein.userERP.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "Employees")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    @Column(unique = true)
    private String email;

    private String department;

    private Long restDay;

    @CreationTimestamp
    private Timestamp createdAt;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Leave> leaves;

}





