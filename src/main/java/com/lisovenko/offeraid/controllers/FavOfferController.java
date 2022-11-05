package com.lisovenko.offeraid.controllers;


import com.lisovenko.offeraid.entities.DTOs.FavOfferDTO;
import com.lisovenko.offeraid.services.AreaService;
import com.lisovenko.offeraid.services.OfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Locale;

@Controller
@RequiredArgsConstructor
public class FavOfferController {
    public final AreaService areaService;
    public final OfferService offerService;
    public final MessageSource messageSource;

    @GetMapping("/offers/favourites")
    public String index(
            @AuthenticationPrincipal UserDetails userDetails,
            Model model,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<FavOfferDTO> favouriteOffers =
                offerService.getFavouriteOffers(userDetails.getUsername(), pageable);
        model.addAttribute("favOffers", favouriteOffers);
        return "user/offers/favourites/index";
    }

    @PostMapping("/{areaUrl}/categories/{categoryUrl}/offers/{offerId}/favourites")
    public String store(
            @PathVariable String areaUrl,
            @PathVariable String categoryUrl,
            @PathVariable Integer offerId,
            @AuthenticationPrincipal UserDetails userDetails,
            RedirectAttributes redirectAttributes,
            Locale locale) {
        offerService.storeFavouriteOffer(offerId, userDetails.getUsername());

        redirectAttributes.addFlashAttribute(
                "toastDefault", messageSource.getMessage("toast.offer.favourited", null, locale));

        return "redirect:/%s/categories/%s/offers/%d".formatted(areaUrl, categoryUrl, offerId);
    }

    @DeleteMapping({
            "/offers/{offerId}/favourites",
            "/{areaUrl}/categories/{categoryUrl}/offers/{offerId}/favourites"
    })
    public String destroy(
            @PathVariable(required = false) String areaUrl,
            @PathVariable(required = false) String categoryUrl,
            @PathVariable Integer offerId,
            @AuthenticationPrincipal UserDetails userDetails,
            RedirectAttributes redirectAttributes,
            Locale locale) {
        offerService.deleteFavouriteOffer(offerId, userDetails.getUsername());

        redirectAttributes.addFlashAttribute(
                "toastDefault", messageSource.getMessage("toast.offer.unfavourited", null, locale));

        if (areaUrl == null || categoryUrl == null) {
            return "redirect:/offers/favourites";
        }
        return "redirect:/%s/categories/%s/offers/%d".formatted(areaUrl, categoryUrl, offerId);
    }
}

