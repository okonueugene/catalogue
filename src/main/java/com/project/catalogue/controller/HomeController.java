package com.project.catalogue.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.catalogue.model.Locations;
import com.project.catalogue.repository.CategoriesRepository;
import com.project.catalogue.repository.LocationsRepository;
import com.project.catalogue.repository.AdsRepository;

@Controller
public class HomeController {
    @Autowired
    private CategoriesRepository categoriesRepository;

    @Autowired
    private LocationsRepository locationsRepository;

    @Autowired
    private AdsRepository adsRepository;

    @GetMapping("/")
    public String index(Model model) {// Fetch all locations sorted alphabetically by name
        List<Locations> locations = locationsRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));

        var defaultLocations = locationsRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        var categories = categoriesRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        var ads = adsRepository.findAll();

        Map<Long, List<Object[]>> sublocationsMap = new HashMap<>();
        Map<Long, Long> locationAdsCount = new HashMap<>();

        for (Locations location : locations) {
            List<Object[]> subs = locationsRepository.findSubLocationsByLocationId(location.getId());
            sublocationsMap.put(location.getId(), subs != null ? subs : new ArrayList<>());

            Long count = locationsRepository.getAdsCountByLocationId(location.getId());
            locationAdsCount.put(location.getId(), count != null ? count : 0L);
        }

        // Add the default lists to the model.
        model.addAttribute("ads", ads);
        model.addAttribute("locations", locations);
        model.addAttribute("categories", categories);
        model.addAttribute("defaultLocations", defaultLocations);
        model.addAttribute("sublocationsMap", sublocationsMap);
        model.addAttribute("locationAdsCount", locationAdsCount);
        model.addAttribute("listPartitioner", new com.project.catalogue.utils.ListPartitioner());

        return "index";
    }

    @GetMapping("/search-locations")
    @ResponseBody
    public List<Map<String, Object>> searchLocations(@RequestParam String keyword) {
        // Search for locations based on the keyword only
        List<Object[]> locationResults = locationsRepository.searchLocations(keyword);
        List<Map<String, Object>> results = new ArrayList<>();

        // For each matching location...
        for (Object[] loc : locationResults) {
            Map<String, Object> locationData = new HashMap<>();
            locationData.put("id", loc[0]);
            locationData.put("name", loc[1]);
            locationData.put("ads", loc[2]);
            locationData.put("type", "location");
            results.add(locationData);

            // Fetch ALL sublocations for this location, regardless of keyword matching
            List<Object[]> sublocations = locationsRepository.findSubLocationsByLocationId((Long) loc[0]);
            for (Object[] sub : sublocations) {
                Map<String, Object> subLocationData = new HashMap<>();
                subLocationData.put("id", sub[0]);
                subLocationData.put("name", sub[1]);
                subLocationData.put("ads", sub[2]);
                subLocationData.put("parentId", loc[0]);
                subLocationData.put("type", "subLocation");
                results.add(subLocationData);
            }
        }

        return results;
    }

    @GetMapping("/search-ads")
    @ResponseBody
    public List<Map<String, Object>> searchAds(@RequestParam String keyword) {
        // Call the repository to get grouped results
        List<Object[]> results = adsRepository.searchAdsByKeywordGroupByCategory(keyword);
        List<Map<String, Object>> suggestions = new ArrayList<>();

        for (Object[] row : results) {
            Long categoryId = (Long) row[0];
            String categoryName = (String) row[1];
            Long adsCount = (Long) row[2];

            // Build a suggestion text like "flowers in For her"
            String suggestionText = keyword + " in " + categoryName;

            Map<String, Object> suggestion = new HashMap<>();
            suggestion.put("categoryId", categoryId);
            suggestion.put("categoryName", categoryName);
            suggestion.put("suggestion", suggestionText);
            suggestion.put("adsCount", adsCount);
            suggestions.add(suggestion);
        }

        return suggestions;
    }

}
