package com.musicore.controllers;

import com.musicore.controllers.Main.Main;
import com.musicore.models.Stores;
import com.musicore.models.Devices;
import com.musicore.models.enums.Category;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;

@Controller
public class StatsCont extends Main {

    @GetMapping("/stats")
    public String sales(Model model) {
        List<Stores> storesList = getUser().getStores();
        List<Devices> devices = new ArrayList<>();
        for (Stores d : storesList) {
            devices.addAll(d.getDevices());
        }

        int income = devices.stream().reduce(0,(i, d) -> i + d.getIncome().getIncome(),Integer::sum);

        model.addAttribute("income", income);
        model.addAttribute("devices", devices);
        model.addAttribute("role", getRole());

        devices.sort(Comparator.comparing(Devices::getCount));
        Collections.reverse(devices);

        String[] topName = new String[5];
        int[] topNum = new int[5];

        for (int i = 0; i < devices.size(); i++) {
            if (i == 5) break;
            topName[i] = devices.get(i).getName();
            topNum[i] = devices.get(i).getIncome().getIncome();
        }
        model.addAttribute("topName", topName);
        model.addAttribute("topNum", topNum);

        return "stats";
    }
}
