package com.lisovenko.offeraid.services;

import com.lisovenko.offeraid.entities.Area;
import com.lisovenko.offeraid.entities.DTOs.AreaDTO;
import com.lisovenko.offeraid.processing.AreaNotFoundException;
import com.lisovenko.offeraid.repositories.AreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.lisovenko.offeraid.entities.DTOs.OfferDTO.getAreaDTO;

@Service
public class AreaService {

    private final AreaRepository areaRepository;

    @Autowired
    public AreaService(AreaRepository areaRepository) {
        this.areaRepository = areaRepository;
    }

    @Transactional(readOnly = true)
    public List<AreaDTO> getAllAreas() {
        List<Area> areas = areaRepository.findAll();

        return areas.stream()
                .map(area -> getAreaDTO(area))
                .toList();
    }

    public AreaDTO findByUrl(String url) {
        return areaRepository
                .findByUrl(url)
                .map(AreaDTO::new)
                .orElseThrow(AreaNotFoundException::new);
    }
}
