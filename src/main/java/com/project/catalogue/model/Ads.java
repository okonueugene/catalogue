package com.project.catalogue.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;

@Entity
@Table(name = "ads")
public class Ads {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Categories category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_category_id", nullable = false)
    private Sub_Categories subCategory;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "price", nullable = false)
    private double price;

    // Primary (main) ad location; required for each ad.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    private Locations location;

    // Optional sublocation for further search refinement.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_location_id")
    private Sub_Location subLocation;

    @OneToMany(mappedBy = "ad", cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.List<Ad_Images> images = new java.util.ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AdsStatus status = AdsStatus.PENDING;

    @CreationTimestamp
    private LocalDateTime created_at;

    @UpdateTimestamp
    private LocalDateTime updated_at;

    public enum AdsStatus {
        PENDING, ACTIVE, EXPIRED
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Categories getCategory() {
        return category;
    }

    public void setCategory(Categories category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public java.util.List<Ad_Images> getImages() {
        return images;
    }

    public void setImages(java.util.List<Ad_Images> images) {
        this.images = images;
    }

    public Sub_Categories getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(Sub_Categories subCategory) {
        this.subCategory = subCategory;
    }
    
    public AdsStatus getStatus() {
        return status;
    }

    public void setStatus(AdsStatus status) {
        this.status = status;
    }

    public Locations getLocation() { return location; }
    public void setLocation(Locations location) { this.location = location; }
    
    public Sub_Location getSubLocation() { return subLocation; }
    public void setSubLocation(Sub_Location subLocation) { this.subLocation = subLocation; }
    

}
