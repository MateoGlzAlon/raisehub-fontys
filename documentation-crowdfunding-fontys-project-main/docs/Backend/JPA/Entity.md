---
sidebar_position: 2
---

# Entity

Entities are the core part of JPA, representing the data we need to store in our database. Below is an example of an `Entity` class called `User`, which defines how the `users` table is mapped in the database:

```java, title ="com/fontys/crowdfund/persistence/entity/UserEntity.java"
package com.fontys.crowdfund.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "Users")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotBlank
    @Column(name = "name")
    private String name;

    @NotBlank
    @Column(name = "email")
    private String email;

    @NotBlank
    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<ProjectEntity> projects;


}
```

This `User` entity is annotated with `@Entity` and `@Table` to indicate that it is a JPA entity mapped to the `users` table in the database. Each field is annotated with `@Column` to define the corresponding columns in the table. The `@Id` annotation marks the primary key, and `@GeneratedValue` is used to specify how the ID should be generated (e.g., automatically by the database).
