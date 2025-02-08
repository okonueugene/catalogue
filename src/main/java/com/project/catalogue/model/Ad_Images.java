package com.project.catalogue.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;

@Entity
@Table(name = "ad_images")

public class Ad_Images {

    @Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "ad_id", nullable = false)
private Ads ad;

@Column(name = "image_url", nullable = false)
private String imageUrl;

@CreationTimestamp
private LocalDateTime created_at;

@UpdateTimestamp
private LocalDateTime updated_at;

public Long getId() {
    return id;
}

public void setId(Long id) {
    this.id = id;
}

public Ads getAd() {
    return ad;
}

public void setAd(Ads ad) {
    this.ad = ad;
}

public String getImageUrl() {
    return imageUrl;
}

public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
}

public LocalDateTime getCreated_at() {
    return created_at;
}

public void setCreated_at(LocalDateTime created_at) {
    this.created_at = created_at;
}

public LocalDateTime getUpdated_at() {
    return updated_at;
}

public void setUpdated_at(LocalDateTime updated_at) {
    this.updated_at = updated_at;
}
    
}
