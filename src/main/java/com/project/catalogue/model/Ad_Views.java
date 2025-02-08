package com.project.catalogue.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;

@Entity
@Table(name = "ad_views")

public class Ad_Views {
//     CREATE TABLE ad_views (
//     id BIGINT PRIMARY KEY AUTO_INCREMENT,
//     ad_id BIGINT NOT NULL,
//     user_id BIGINT,  -- NULL for unregistered users
//     ip_address VARCHAR(45) NOT NULL, 
//     viewed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
//     FOREIGN KEY (ad_id) REFERENCES ads(id) ON DELETE CASCADE,
//     FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL
// );


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ad_id", nullable = false)
    private Ads ad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @Column(name = "ip_address", nullable = false)
    private String ipAddress;

    @Column(name = "viewed_at")
    @CreationTimestamp
    private LocalDateTime viewedAt;

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

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public LocalDateTime getViewedAt() {
        return viewedAt;
    }

    public void setViewedAt(LocalDateTime viewedAt) {
        this.viewedAt = viewedAt;
    }



}
