package com.monitoring.demo.entity;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Book")
@Getter
public class Book {

    @Id
    private Long id;

    private String name;

    private String author;

    private int amount;

}
