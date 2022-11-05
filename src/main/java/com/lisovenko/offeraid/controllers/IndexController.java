package com.lisovenko.offeraid.controllers;

import com.lisovenko.offeraid.entities.DTOs.AreaDTO;
import com.lisovenko.offeraid.services.AreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class IndexController {
    private final AreaService areaService;

    @GetMapping("/")
    public String index(Model model) {
        List<AreaDTO> areas = areaService.getAllAreas();

        model.addAttribute("areas", areas);
        return "index";
    }
}
