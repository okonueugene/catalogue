package com.project.catalogue.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.project.catalogue.dto.AdsDto;
import com.project.catalogue.model.Ad_Images;
import com.project.catalogue.model.Ads;
import com.project.catalogue.model.Categories;
import com.project.catalogue.model.Locations;
import com.project.catalogue.model.Sub_Categories;
import com.project.catalogue.model.Sub_Location;
import com.project.catalogue.model.Users;
import com.project.catalogue.repository.AdImagesRepository;
import com.project.catalogue.repository.AdsRepository;
import com.project.catalogue.repository.CategoriesRepository;
import com.project.catalogue.repository.LocationsRepository;
import com.project.catalogue.repository.SubCategoriesRepository;
import com.project.catalogue.repository.SubLocationRepository;
import com.project.catalogue.repository.UserRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/ads")
public class AdsController {

    @Autowired
    private AdsRepository adsRepository;

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private SubCategoriesRepository subCategoriesRepository;

    @Autowired
    private LocationsRepository locationsRepository;

    @Autowired
    private SubLocationRepository subLocationRepository;


    @Autowired
    private AdImagesRepository adImagesRepository;

    @GetMapping("/list")
    public String listAds(Model model) {
        List<Ads> ads = adsRepository.findByUserId(1L); 
        model.addAttribute("ads", ads);
        return "ads/index";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        AdsDto adsDto = new AdsDto();
        var categories = categoriesRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        var locations = locationsRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));

        model.addAttribute("adsDto", adsDto);
        model.addAttribute("categories", categories);
        model.addAttribute("locations", locations);
        return "ads/create";
    }

@PostMapping("/create")
public String createAds(@Valid @ModelAttribute AdsDto adsDto, BindingResult result) {
    if (result.hasErrors()) {
        return "ads/create";
    }

    if (adsDto.getImageFileNames() == null || adsDto.getImageFileNames().length == 0) {
        throw new RuntimeException("No images were uploaded.");
    }

    // Create and save the Ad first
    Ads ad = new Ads();
    ad.setTitle(adsDto.getTitle());
    ad.setDescription(adsDto.getDescription());
    ad.setPrice(adsDto.getPrice());

    // Fetch related entities using their IDs
    Users user = userRepo.findById(1L).orElseThrow();  // Change to actual logged-in user
    Categories category = categoriesRepository.findById(adsDto.getCategory()).orElseThrow();
    Sub_Categories subCategory = subCategoriesRepository.findById(adsDto.getSubCategoryId()).orElseThrow();
    Locations location = locationsRepository.findById(adsDto.getLocation()).orElseThrow();
    Sub_Location subLocation = subLocationRepository.findById(adsDto.getSubLocation()).orElseThrow();

    ad.setUser(user);
    ad.setCategory(category);
    ad.setSubCategory(subCategory);
    ad.setLocation(location);
    ad.setSubLocation(subLocation);
    ad.setStatus(Ads.AdsStatus.PENDING);

    ad = adsRepository.save(ad); // Save the ad first to get the ID

    // Save images
    String uploadDir = "public/images/ad_images/" + ad.getId();
    new File(uploadDir).mkdirs(); // Create the ad-specific directory

    List<Ad_Images> adImages = new ArrayList<>();
    for (MultipartFile file : adsDto.getImageFileNames()) {
        try {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Save image path in the database
            Ad_Images adImage = new Ad_Images();
            adImage.setAd(ad);
            adImage.setImageUrl("/images/ad_images/" + ad.getId() + "/" + fileName);
            adImages.add(adImage);
        } catch (IOException e) {
            throw new RuntimeException("Error saving images", e);
        }
    }

    adImagesRepository.saveAll(adImages);

    return "redirect:/ads/list";
}
@GetMapping("/show")
public String showAd(@RequestParam Long id, Model model) {
    // Retrieve the current ad
    Ads ad = adsRepository.findById(id)
              .orElseThrow(() -> new RuntimeException("Ad not found"));
    model.addAttribute("ad", ad);
    
    // Retrieve related ads based on the category and subcategory
    List<Ads> relatedAds = adsRepository.findRelatedAds(
                                ad.getCategory().getId(),
                                ad.getSubCategory().getId(),
                                ad.getId());
    model.addAttribute("relatedAds", relatedAds);
    
    return "ads/show";
}



@GetMapping("/edit")
public String showEditForm(@RequestParam Long id, Model model) {
    Ads ad = adsRepository.findById(id).orElseThrow(() -> new RuntimeException("Ad not found"));
    AdsDto adsDto = new AdsDto();
    adsDto.setId(ad.getId());
    adsDto.setTitle(ad.getTitle());
    adsDto.setDescription(ad.getDescription());
    adsDto.setPrice(ad.getPrice());
    adsDto.setCategory(ad.getCategory().getId());
    adsDto.setSubCategoryId(ad.getSubCategory().getId());
    adsDto.setLocation(ad.getLocation().getId());
    adsDto.setSubLocation(ad.getSubLocation().getId());

    var categories = categoriesRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    var locations = locationsRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    var subCategories = subCategoriesRepository.findByCategoryId(ad.getCategory().getId());
    var subLocations = subLocationRepository.findByLocationId(ad.getLocation().getId());

    // Pass the existing images
    adsDto.setExistingImages(ad.getImages());

    model.addAttribute("adsDto", adsDto);
    model.addAttribute("categories", categories);
    model.addAttribute("locations", locations);
    model.addAttribute("subCategories", subCategories);
    model.addAttribute("subLocations", subLocations);
    return "ads/edit";
}
@PostMapping("/edit/{id}")
@Transactional  // Ensure all DB changes are rolled back on exception
public String updateAds(@PathVariable Long id,
                        @Valid @ModelAttribute AdsDto adsDto,
                        BindingResult result,
                        @RequestParam(value = "removeImageIds", required = false) List<Long> removeImageIds,
                        @RequestParam(value = "imageFileNames", required = false) MultipartFile[] newImages) {
    if (result.hasErrors()) {
        return "ads/edit";
    }

    // Retrieve the existing ad
    Ads ad = adsRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Ad not found"));

    // Update ad fields
    ad.setTitle(adsDto.getTitle());
    ad.setDescription(adsDto.getDescription());
    ad.setPrice(adsDto.getPrice());

    Categories category = categoriesRepository.findById(adsDto.getCategory())
            .orElseThrow(() -> new RuntimeException("Category not found"));
    Sub_Categories subCategory = subCategoriesRepository.findById(adsDto.getSubCategoryId())
            .orElseThrow(() -> new RuntimeException("Subcategory not found"));
    Locations location = locationsRepository.findById(adsDto.getLocation())
            .orElseThrow(() -> new RuntimeException("Location not found"));
    Sub_Location subLocation = subLocationRepository.findById(adsDto.getSubLocation())
            .orElseThrow(() -> new RuntimeException("Sub location not found"));

    ad.setCategory(category);
    ad.setSubCategory(subCategory);
    ad.setLocation(location);
    ad.setSubLocation(subLocation);

    // Save the ad update (without images) first
    adsRepository.save(ad);

    // Remove images marked for deletion
    if (removeImageIds != null && !removeImageIds.isEmpty()) {
        for (Long imageId : removeImageIds) {
            Ad_Images image = adImagesRepository.findById(imageId)
                    .orElseThrow(() -> new RuntimeException("Image not found with id: " + imageId));
            String imageUrl = image.getImageUrl();
            // Remove leading slash if present
            if (imageUrl.startsWith("/")) {
                imageUrl = imageUrl.substring(1);
            }
            // Build the absolute file path (using the working directory)
            Path filePath = Paths.get(System.getProperty("user.dir"), "public", imageUrl);
            try {
                // Delete the file if it exists; throw exception if deletion fails
                boolean deleted = Files.deleteIfExists(filePath);
                if (!deleted) {
                    throw new RuntimeException("Failed to delete file: " + filePath.toAbsolutePath());
                }
            } catch (IOException ex) {
                throw new RuntimeException("Error deleting file: " + filePath.toAbsolutePath(), ex);
            }
            // Remove the DB record
            adImagesRepository.delete(image);
        }
    }

    // Add new images if provided
    if (newImages != null && newImages.length > 0) {
        String uploadDir = Paths.get(System.getProperty("user.dir"), "public", "images", "ad_images", String.valueOf(ad.getId())).toString();
        File adDir = new File(uploadDir);
        if (!adDir.exists()) {
            adDir.mkdirs();
        }

        List<Ad_Images> newAdImages = new ArrayList<>();
        for (MultipartFile file : newImages) {
            if (!file.isEmpty()) {
                try {
                    String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                    Path filePath = Paths.get(uploadDir, fileName);
                    Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                    Ad_Images adImage = new Ad_Images();
                    adImage.setAd(ad);
                    // Construct the image URL relative to the public folder
                    String imageUrlPath = "/images/ad_images/" + ad.getId() + "/" + fileName;
                    adImage.setImageUrl(imageUrlPath);
                    newAdImages.add(adImage);
                } catch (IOException e) {
                    throw new RuntimeException("Error saving new image: " + file.getOriginalFilename(), e);
                }
            }
        }
        adImagesRepository.saveAll(newAdImages);
    }

    return "redirect:/ads/list";
}

    @GetMapping("/subcategories")
    @ResponseBody
    public List<Map<String, Object>> getSubCategories(@RequestParam Long categoryId) {
        List<Sub_Categories> subCategories = subCategoriesRepository.findByCategoryId(categoryId);
        List<Map<String, Object>> result = new ArrayList<>();

        for (Sub_Categories subCategory : subCategories) {
            Map<String, Object> subCategoryMap = new HashMap<>();
            subCategoryMap.put("id", subCategory.getId());
            subCategoryMap.put("name", subCategory.getName());
            result.add(subCategoryMap);
        }

        return result;
    }

    @GetMapping("/sublocations")
    @ResponseBody
    public List<Map<String, Object>> getSubLocations(@RequestParam Long locationId) {
        List<Sub_Location> subLocations = subLocationRepository.findByLocationId(locationId);
        List<Map<String, Object>> result = new ArrayList<>();

        for (Sub_Location subLocation : subLocations) {
            Map<String, Object> subLocationMap = new HashMap<>();
            subLocationMap.put("id", subLocation.getId());
            subLocationMap.put("name", subLocation.getName());
            result.add(subLocationMap);
        }

        return result;
    }
}
