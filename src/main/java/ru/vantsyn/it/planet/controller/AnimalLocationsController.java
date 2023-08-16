package ru.vantsyn.it.planet.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vantsyn.it.planet.model.dto.animalLocation.AnimalLocationResultDTO;
import ru.vantsyn.it.planet.model.dto.animalLocation.AnimalLocationSearchDTO;
import ru.vantsyn.it.planet.model.dto.animalLocation.AnimalLocationUpdateDTO;
import ru.vantsyn.it.planet.model.service.AccountService;
import ru.vantsyn.it.planet.model.service.AnimalLocationService;

import java.util.List;

@RequestMapping("/animals/{animalId}/locations")
@RestController
public class AnimalLocationsController {
    /**
     * Сервис для реализации бизнес логики связанной с аккаунтами
     */
    private final AnimalLocationService animalLocationService;
    private final AccountService accountService;
    private final ModelMapper mapper;

    public AnimalLocationsController(AnimalLocationService animalLocationService, AccountService accountService, ModelMapper mapper) {
        this.animalLocationService = animalLocationService;
        this.accountService = accountService;
        this.mapper = mapper;
    }

    @GetMapping("")
    public List<AnimalLocationResultDTO> getLocationPointsByAnimal(@PathVariable("animalId") long animalId,
                                                          @Valid AnimalLocationSearchDTO searchDTO) {
        return animalLocationService.getVisitedLocationsByAnimal(animalId, searchDTO);
    }

    @PostMapping("/{locationId}")
    public ResponseEntity<AnimalLocationResultDTO> addLocationPointToAnimal(@PathVariable("animalId") long animalId,
                                                            @PathVariable("locationId") long locationId,
                                                            HttpServletRequest request) {
        accountService.checkIsAuthorize(request);

        return new ResponseEntity<>(mapper.map(animalLocationService.addVisitedLocationToAnimal(animalId, locationId),
                AnimalLocationResultDTO.class), HttpStatus.CREATED);
    }

    @PutMapping("")
    public AnimalLocationResultDTO updateLocationPointToAnimal(@PathVariable("animalId") long animalId,
                                                               @Valid @RequestBody AnimalLocationUpdateDTO updateDTO,
                                                               HttpServletRequest request) {
        accountService.checkIsAuthorize(request);

        return mapper.map(animalLocationService.updateVisitedLocationToAnimal(animalId, updateDTO), AnimalLocationResultDTO.class);
    }

    @DeleteMapping("/{visitedLocationId}")
    public void deleteVisitedLocationPointToAnimal(@PathVariable("animalId") long animalId,
                                                   @PathVariable("visitedLocationId") long visitedLocationId,
                                                   HttpServletRequest request) {
        accountService.checkIsAuthorize(request);
        animalLocationService.deleteAnimalLocation(animalId, visitedLocationId);
    }
}
