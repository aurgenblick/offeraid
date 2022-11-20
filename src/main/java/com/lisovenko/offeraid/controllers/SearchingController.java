package com.lisovenko.offeraid.controllers;

import com.lisovenko.offeraid.entities.DTOs.OfferDTO;
import com.lisovenko.offeraid.services.OfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class SearchingController {
    private final OfferService offerService;

    @GetMapping("/search")
    public String index(
            @RequestParam String query,
            Model model,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<OfferDTO> offers = offerService.findByQuery(query, pageable);

        model.addAttribute("query", query);
        model.addAttribute("offers", offers);

        return "search/index";
    }
}
