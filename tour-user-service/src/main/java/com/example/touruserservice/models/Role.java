package com.example.touruserservice.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Roles")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer id;

    @Column(name = "name")
    private String name;
}
