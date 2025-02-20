package com.project.catalogue.model;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "locations")
public class Locations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    // One location can have many sublocations
    @OneToMany(mappedBy = "location")
    private List<Sub_Location> subLocations = new ArrayList<>();

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public List<Sub_Location> getSubLocations() { return subLocations; }
    public void setSubLocations(List<Sub_Location> subLocations) { this.subLocations = subLocations; }
}
