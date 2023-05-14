package com.musicore.models;

import com.musicore.models.enums.Category;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Devices {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    @Column(nullable = false, updatable = false)
    private Long id;
    private String name;
    @Column(length = 5000)
    private String description;
    private String poster;
    private String[] screenshots;
    private int count;
    @Enumerated(EnumType.STRING)
    private Category category;
    @ManyToOne(fetch = FetchType.LAZY)
    private Stores store;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Income income;
    @OneToMany(mappedBy = "device", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comments> comments;

    public Devices(String name, String description, String poster, String[] screenshots, int price, int count, Category category) {
        this.name = name;
        this.description = description;
        this.poster = poster;
        this.screenshots = screenshots;
        this.count = count;
        this.category = category;
        this.income = new Income(price);
    }

    public void addComment(Comments comment) {
        comments.add(comment);
        comment.setDevice(this);
    }

    public void removeComment(Comments comment) {
        comments.remove(comment);
        comment.setDevice(null);
    }
}
