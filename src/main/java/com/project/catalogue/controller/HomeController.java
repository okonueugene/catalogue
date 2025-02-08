package com.project.catalogue.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.project.catalogue.repository.CategoriesRepository;

@Controller
public class HomeController {
 @Autowired
    private CategoriesRepository categoriesRepository;
     @GetMapping("/")
    public String index(Model model){

        var categories = categoriesRepository.findAll();
        model.addAttribute("categories", categories);
        return "index";
    }
    


}
