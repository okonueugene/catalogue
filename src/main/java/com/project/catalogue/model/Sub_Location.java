package com.project.catalogue.model;

import jakarta.persistence.*;

@Entity
@Table(name = "sub_locations")
public class Sub_Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;

    // Link to parent location
    @ManyToOne(fetch = jakarta.persistence.FetchType.LAZY)
    @JoinColumn(name = "location_id" , nullable = false)
    private Locations location;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Locations getLocation() { return location; }
    public void setLocation(Locations location) { this.location = location; }

}
