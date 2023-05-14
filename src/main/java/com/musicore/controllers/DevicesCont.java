package com.musicore.controllers;

import com.musicore.controllers.Main.Main;
import com.musicore.models.*;
import com.musicore.models.enums.Category;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Objects;
import java.util.UUID;

@Controller
@RequestMapping("/devices")
public class DevicesCont extends Main {

    @GetMapping("/{id}")
    public String device(@PathVariable Long id, Model model) {
        if (!repoDevices.existsById(id)) return "redirect:/catalog";
        model.addAttribute("s", repoDevices.getReferenceById(id));
        model.addAttribute("role", getRole());
        model.addAttribute("user", getUser());
        return "device";
    }

    @PostMapping("/comment/add/{id}")
    public String comment_add(@PathVariable Long id, @RequestParam String comment) {
        Devices device = repoDevices.getReferenceById(id);
        device.addComment(new Comments(getUser().getUsername(), DateNow(), comment));
        repoDevices.save(device);
        return "redirect:/devices/{id}";
    }

    @PostMapping("/buy/{id}")
    public String buy(@PathVariable Long id, @RequestParam int count) {
        Devices device = repoDevices.getReferenceById(id);

        Users user = getUser();
        user.addCart(new Carts(count, device.getIncome().getPrice(), (device.getIncome().getPrice() * count), device));
        repoUsers.save(user);

        device.setCount(device.getCount() - count);

        device.getIncome().setCount(device.getIncome().getCount() + count);
        device.getIncome().setIncome(device.getIncome().getIncome() + (count * device.getIncome().getPrice()));

        repoDevices.save(device);

        return "redirect:/devices/{id}";
    }

    @GetMapping("/add/{id}")
    public String device_add(@PathVariable Long id, Model model) {
        model.addAttribute("store", repoStores.getReferenceById(id));
        model.addAttribute("role", getRole());
        model.addAttribute("categories", Category.values());
        return "device_add";
    }

    @PostMapping("/add")
    public String device_add(Model model, @RequestParam long storeId, @RequestParam String name, @RequestParam MultipartFile poster, @RequestParam MultipartFile[] screenshots, @RequestParam int price, @RequestParam int count, @RequestParam Category category, @RequestParam String description) {
        try {
            String uuidFile = UUID.randomUUID().toString();
            String result_poster = "";
            if (poster != null && !Objects.requireNonNull(poster.getOriginalFilename()).isEmpty()) {
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) uploadDir.mkdir();
                result_poster = uuidFile + "_" + poster.getOriginalFilename();
                poster.transferTo(new File(uploadPath + "/" + result_poster));
            }

            String[] result_screenshots = new String[0];
            if (screenshots != null && !Objects.requireNonNull(screenshots[0].getOriginalFilename()).isEmpty()) {
                uuidFile = UUID.randomUUID().toString();
                String result_screenshot;
                result_screenshots = new String[screenshots.length];
                for (int i = 0; i < result_screenshots.length; i++) {
                    result_screenshot = uuidFile + "_" + screenshots[i].getOriginalFilename();
                    screenshots[i].transferTo(new File(uploadPath + "/" + result_screenshot));
                    result_screenshots[i] = result_screenshot;
                }
            }

            Stores store = repoStores.getReferenceById(storeId);
            store.addDevice(new Devices(name, description, result_poster, result_screenshots, price, count, category));
            repoStores.save(store);

        } catch (Exception e) {
            model.addAttribute("author", repoStores.getReferenceById(storeId));
            model.addAttribute("role", getRole());
            model.addAttribute("message", "Некорректные данные!");
            model.addAttribute("categories", Category.values());
            return "device_add";
        }
        return "redirect:/catalog/all";
    }

    @GetMapping("/edit/{id}")
    public String device_edit(@PathVariable(value = "id") Long id, Model model) {
        if (!repoDevices.existsById(id)) return "redirect:/catalog";
        model.addAttribute("s", repoDevices.getReferenceById(id));
        model.addAttribute("role", getRole());
        model.addAttribute("categories", Category.values());
        return "device_edit";
    }

    @PostMapping("/edit/{id}")
    public String device_edit(@PathVariable Long id, Model model, @RequestParam String name, @RequestParam MultipartFile poster, @RequestParam MultipartFile[] screenshots, @RequestParam int price, @RequestParam int count, @RequestParam Category category, @RequestParam String description) {
        try {
            Devices device = repoDevices.getReferenceById(id);

            device.setDescription(description);
            device.setName(name);
            device.setCount(count);
            device.getIncome().setPrice(price);
            device.setCategory(category);

            String uuidFile = UUID.randomUUID().toString();
            if (poster != null && !Objects.requireNonNull(poster.getOriginalFilename()).isEmpty()) {
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) uploadDir.mkdir();
                String result_poster = uuidFile + "_" + poster.getOriginalFilename();
                poster.transferTo(new File(uploadPath + "/" + result_poster));
                device.setPoster(result_poster);
            }

            if (screenshots != null && !Objects.requireNonNull(screenshots[0].getOriginalFilename()).isEmpty()) {
                uuidFile = UUID.randomUUID().toString();
                String result_screenshot;
                String[] result_screenshots = new String[screenshots.length];
                for (int i = 0; i < result_screenshots.length; i++) {
                    result_screenshot = uuidFile + "_" + screenshots[i].getOriginalFilename();
                    screenshots[i].transferTo(new File(uploadPath + "/" + result_screenshot));
                    result_screenshots[i] = result_screenshot;
                }
                device.setScreenshots(result_screenshots);
            }
            repoDevices.save(device);
        } catch (Exception e) {
            model.addAttribute("device", repoDevices.getReferenceById(id));
            model.addAttribute("role", getRole());
            model.addAttribute("message", "Некорректные данные!");
            model.addAttribute("categories", Category.values());
            return "device_edit";
        }
        return "redirect:/devices/{id}/";
    }

    @GetMapping("/delete/{id}")
    public String device_delete(@PathVariable Long id) {
        repoDevices.deleteById(id);
        return "redirect:/catalog/all";
    }
}
