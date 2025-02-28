package com.project.catalogue.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.*;
import com.project.catalogue.model.Ad_Images;
public class AdsDto {
    private Long id;
    @NotEmpty(message = "Title is required")
    private String title;
    @NotEmpty(message = "Description is required")
    @Size(min = 5, message = "Description must be at least 20 characters long")
    @Size(max = 100, message = "Description must be at most 1000 characters long")
    private String description;
    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price must be at least 0")
    @NotNull(message = "Subcategory is required")
    private Long subCategoryId;
    private double price;
    @NotNull(message = "Location is required")
    private Long location;
    @NotNull(message = "Sub Location is required")
    private Long subLocation;
    @NotNull(message = "Category is required")
    private Long category;
    private MultipartFile[] imageFileNames;
    private List<Ad_Images> existingImages;
    private List<Long> imagesToRemove;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(Long subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public Long getLocation() {
        return location;
    }

    public void setLocation(Long location) {
        this.location = location;
    }

    public Long getSubLocation() {
        return subLocation;
    }

    public void setSubLocation(Long subLocation) {
        this.subLocation = subLocation;
    }

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    public void setImageFileNames(MultipartFile[] imageFileNames) {
        this.imageFileNames = imageFileNames;
    }

    public MultipartFile[] getImageFileNames() {
        return imageFileNames != null ? imageFileNames : new MultipartFile[0];
    }
  public List<Ad_Images> getExistingImages() {
        return existingImages;
    }

    public void setExistingImages(List<Ad_Images> existingImages) {
        this.existingImages = existingImages;
    }

    public List<Long> getImagesToRemove() {
        return imagesToRemove;
    }

    public void setImagesToRemove(List<Long> imagesToRemove) {
        this.imagesToRemove = imagesToRemove;
    }


}
