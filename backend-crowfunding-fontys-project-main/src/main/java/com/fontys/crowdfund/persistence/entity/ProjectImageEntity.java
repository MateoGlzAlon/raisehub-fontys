package com.fontys.crowdfund.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "ProjectImages")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private ProjectEntity project;

    @NotBlank
    @Column(name = "image_url")
    private String imageUrl;

    @NotNull
    @Column(name = "image_order")
    private Integer imageOrder;

}
