package com.musicore.models;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Carts {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    @Column(nullable = false, updatable = false)
    private Long id;
    private int count;
    private int price;
    private int sum;
    @OneToOne(fetch = FetchType.LAZY)
    private Devices device;
    @ManyToOne(fetch = FetchType.LAZY)
    private Users owner;

    public Carts(int count, int price, int sum, Devices device) {
        this.count = count;
        this.price = price;
        this.sum = sum;
        this.device = device;
    }
}
