package com.example.foodbe.models;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
@Table(name= "Category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String name;

    @Column(nullable = false,unique = true)
    private String slug;

    @Column(nullable = false)
    private String imgCategory;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;


}
