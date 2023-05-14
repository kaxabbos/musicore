package com.musicore.models;

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
public class Stores {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    @Column(nullable = false, updatable = false)
    private Long id;
    private String name;
    private String poster;
    private String address;
    private String tel;
    @Column(length = 5000)
    private String description;
    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Devices> devices;
    @ManyToOne(fetch = FetchType.LAZY)
    private Users owner;

    public Stores(String name, String poster, String address, String tel, String description) {
        this.name = name;
        this.poster = poster;
        this.address = address;
        this.tel = tel;
        this.description = description;
    }

    public void addDevice(Devices session) {
        this.devices.add(session);
        session.setStore(this);
    }

    public void removeDevice(Devices session) {
        this.devices.remove(session);
        session.setStore(null);
    }
}
