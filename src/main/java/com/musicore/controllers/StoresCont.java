package com.musicore.controllers;

import com.musicore.controllers.Main.Main;
import com.musicore.models.Stores;
import com.musicore.models.Users;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Controller
@RequestMapping("/stores")
public class StoresCont extends Main {

    @GetMapping
    public String stores(Model model) {
        model.addAttribute("stores", repoStores.findAll());
        model.addAttribute("role", getRole());
        return "stores";
    }

    @GetMapping("/{id}")
    public String store(Model model, @PathVariable Long id) {
        model.addAttribute("store", repoStores.getReferenceById(id));
        model.addAttribute("role", getRole());
        model.addAttribute("user", getUser());
        return "store";
    }

    @GetMapping("/add")
    public String store_add(Model model) {
        model.addAttribute("role", getRole());
        model.addAttribute("user", getUser());
        return "store_add";
    }

    @PostMapping("/add")
    public String store_add(Model model, @RequestParam String name, @RequestParam MultipartFile poster, @RequestParam String address, @RequestParam String tel, @RequestParam String description) {
        try {
            String result_poster = "";
            if (poster != null && !Objects.requireNonNull(poster.getOriginalFilename()).isEmpty()) {
                String uuidFile = UUID.randomUUID().toString();
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) uploadDir.mkdir();
                result_poster = uuidFile + "_" + poster.getOriginalFilename();
                poster.transferTo(new File(uploadPath + "/" + result_poster));
            }
            Users user = getUser();
            user.addStore(new Stores(name, result_poster, address, tel, description));
            repoUsers.save(user);
        } catch (IOException e) {
            model.addAttribute("role", getRole());
            model.addAttribute("user", getUser());
            model.addAttribute("message", "Некорректные данные!");
            return "store_add";
        }
        return "redirect:/stores";
    }

    @GetMapping("/edit/{id}")
    public String store_edit(@PathVariable Long id, Model model) {
        if (!repoStores.existsById(id)) return "redirect:/stores";
        model.addAttribute("role", getRole());
        model.addAttribute("user", getUser());
        model.addAttribute("store", repoStores.getReferenceById(id));
        return "store_edit";
    }

    @PostMapping("/edit/{id}")
    public String store_edit(Model model, @PathVariable Long id, @RequestParam String name, @RequestParam MultipartFile poster, @RequestParam String address, @RequestParam String tel, @RequestParam String description) {
        try {
            Stores stores = repoStores.getReferenceById(id);

            stores.setName(name);
            stores.setAddress(address);
            stores.setTel(tel);
            stores.setDescription(description);

            if (poster != null && !Objects.requireNonNull(poster.getOriginalFilename()).isEmpty()) {
                String uuidFile = UUID.randomUUID().toString();
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) uploadDir.mkdir();
                String result_poster = uuidFile + "_" + poster.getOriginalFilename();
                poster.transferTo(new File(uploadPath + "/" + result_poster));
                stores.setPoster(result_poster);
            }

            repoStores.save(stores);
        } catch (Exception e) {
            model.addAttribute("role", getRole());
            model.addAttribute("user", getUser());
            model.addAttribute("store", repoStores.getReferenceById(id));
            model.addAttribute("message", "Некорректные данные!");
            return "store_edit";
        }
        return "redirect:/stores";
    }

    @GetMapping("/delete/{id}")
    public String store_delete(@PathVariable Long id) {
        repoStores.deleteById(id);
        return "redirect:/stores";
    }
}
