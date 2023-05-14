package com.musicore.controllers;

import com.musicore.controllers.Main.Main;
import com.musicore.models.enums.Category;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CatalogCont extends Main {
    @GetMapping("/catalog")
    public String catalog(Model model) {
        model.addAttribute("devices", repoDevices.findAll());
        model.addAttribute("categories", Category.values());
        model.addAttribute("role", getRole());
        return "catalog";
    }

    @GetMapping("/catalog/all")
    public String catalog_main(Model model) {
        model.addAttribute("devices", repoDevices.findAll());
        model.addAttribute("categories", Category.values());
        model.addAttribute("role", getRole());
        return "catalog";
    }

    @PostMapping("/catalog/search")
    public String search(@RequestParam Category category, @RequestParam String search, Model model) {
        model.addAttribute("devices", repoDevices.findAllByCategoryAndNameContaining(category, search));
        model.addAttribute("categories", Category.values());
        model.addAttribute("role", getRole());
        return "catalog";
    }

    @GetMapping("/catalog/category/{category}")
    public String catalog_genre_search(@PathVariable(value = "category") Category category, Model model) {
        model.addAttribute("devices", repoDevices.findAllByCategory(category));
        model.addAttribute("categories", Category.values());
        model.addAttribute("role", getRole());
        return "catalog";
    }


    @PostMapping("/catalog/search/name")
    public String catalogSearch(@RequestParam String search, Model model) {
        model.addAttribute("devices", repoDevices.findAllByNameContaining(search));
        model.addAttribute("categories", Category.values());
        model.addAttribute("role", getRole());
        return "catalog";
    }
}
