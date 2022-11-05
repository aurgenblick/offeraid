package com.lisovenko.offeraid.controllers;

import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class OfferController {

    @RequestMapping("/offer")
    public String getIndex() {
        return "offer";
    }

}
