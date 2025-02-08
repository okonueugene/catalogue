package com.project.catalogue.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;

@Entity
@Table(name = "reports")

public class Reports {
    // CREATE TABLE reports (
    //     id BIGINT PRIMARY KEY AUTO_INCREMENT,
    //     user_id BIGINT NOT NULL,
    //     ad_id BIGINT NOT NULL,
    //     reason TEXT NOT NULL,
    //     reported_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    //     FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    //     FOREIGN KEY (ad_id) REFERENCES ads(id) ON DELETE CASCADE
    // );
    
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "user_id", nullable = false)
private Users user;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "ad_id", nullable = false)
private Ads ad;

@Column(name = "reason", nullable = false)
private String reason;

@CreationTimestamp
private LocalDateTime created_at;

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

public Ads getAd() {
    return ad;
}

public void setAd(Ads ad) {
    this.ad = ad;
}

public String getReason() {
    return reason;
}

public void setReason(String reason) {
    this.reason = reason;
}

public LocalDateTime getCreated_at() {
    return created_at;
}

public void setCreated_at(LocalDateTime created_at) {
    this.created_at = created_at;
}

}
