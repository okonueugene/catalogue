package com.project.catalogue.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;

@Entity
@Table(name = "ads")
public class Ads {
//     CREATE TABLE ads (
//     id BIGINT PRIMARY KEY AUTO_INCREMENT,
//     user_id BIGINT NOT NULL,
//     category_id BIGINT NOT NULL,
//     title VARCHAR(255) NOT NULL,
//     description TEXT NOT NULL,
//     price DECIMAL(10,2) NOT NULL,
//     location VARCHAR(255),
//     image_url VARCHAR(255),
//     views INT DEFAULT 0,
//     status ENUM('active', 'pending', 'expired') DEFAULT 'pending',
//     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
//     updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
//     FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
//     FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE
// );

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "user_id", nullable = false)
private Users user;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "category_id", nullable = false)
private Categories category;

@Column(name = "title", nullable = false)
private String title;

@Column(name = "description", nullable = false)
private String description;

@Column(name = "price", nullable = false)
private double price;

@Column(name = "location")
private String location;

@OneToMany(mappedBy = "ad", cascade = CascadeType.ALL, orphanRemoval = true)
private java.util.List<Ad_Images> images = new java.util.ArrayList<>();

@Column(name = "views")
private int views = 0;

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

public String getLocation() {
    return location;
}

public void setLocation(String location) {
    this.location = location;
}

public java.util.List<Ad_Images> getImages() {
    return images;
}

public void setImages(java.util.List<Ad_Images> images) {
    this.images = images;
}

public int getViews() {
    return views;
}

public void setViews(int views) {
    this.views = views;
}

public AdsStatus getStatus() {
    return status;
}

public void setStatus(AdsStatus status) {   
    this.status = status;   
}



}
