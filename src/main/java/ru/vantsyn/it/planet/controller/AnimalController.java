package ru.vantsyn.it.planet.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vantsyn.it.planet.model.dto.animal.AnimalCreateDTO;
import ru.vantsyn.it.planet.model.dto.animal.AnimalResultDTO;
import ru.vantsyn.it.planet.model.dto.animal.AnimalSearchFilterDTO;
import ru.vantsyn.it.planet.model.dto.animal.AnimalUpdateDTO;
import ru.vantsyn.it.planet.model.dto.animalType.AnimalTypeIdsDTO;
import ru.vantsyn.it.planet.model.service.AccountService;
import ru.vantsyn.it.planet.model.service.AnimalService;

import java.util.List;

@RestController
@RequestMapping("/animals")
public class AnimalController {
    private final AnimalService animalService;
    private final AccountService accountService;

    public AnimalController(AnimalService animalService, AccountService accountService) {
        this.animalService = animalService;
        this.accountService = accountService;
    }

    @GetMapping("/{animalId}")
    public AnimalResultDTO getAnimalById(@PathVariable("animalId") long animalId) {
        return animalService.getAnimalById(animalId);
    }

    @GetMapping("/search")
    public List<AnimalResultDTO> searchAnimals(@Valid AnimalSearchFilterDTO animalFilter) {
        return animalService.searchAnimals(animalFilter);
    }

    @PostMapping("")
    public ResponseEntity<AnimalResultDTO> createAnimal(@Valid @RequestBody AnimalCreateDTO animalCreateDTO,
                                                       HttpServletRequest request) {
        accountService.checkIsAuthorize(request);

        return new ResponseEntity<>(animalService.createAnimal(animalCreateDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{animalId}")
    public AnimalResultDTO updateAnimal(@PathVariable("animalId") long animalId,
                                        @Valid @RequestBody AnimalUpdateDTO animalUpdateDTO, HttpServletRequest request) {
        accountService.checkIsAuthorize(request);

        return animalService.updateAnimal(animalId, animalUpdateDTO);
    }

    @DeleteMapping("/{animalId}")
    public void deleteAnimal(@PathVariable("animalId") long animalId, HttpServletRequest request) {
        accountService.checkIsAuthorize(request);
        animalService.deleteAnimal(animalId);
    }

    @PostMapping("/{animalId}/types/{typeId}")
    public ResponseEntity<AnimalResultDTO> addAnimalTypeToAnimal(@PathVariable("animalId") long animalId,
                                                 @PathVariable("typeId") long typeId, HttpServletRequest request) {
        accountService.checkIsAuthorize(request);

        return new ResponseEntity<>(animalService.addAnimalTypeToAnimal(animalId, typeId), HttpStatus.CREATED);
    }

    @PutMapping("/{animalId}/types")
    public AnimalResultDTO updateAnimalTypeToAnimal(@PathVariable("animalId") long animalId,
                                                    @Valid @RequestBody AnimalTypeIdsDTO animalTypeIdsDTO,
                                                    HttpServletRequest request) {
        accountService.checkIsAuthorize(request);

        return animalService.updateAnimalTypeToAnimal(animalId, animalTypeIdsDTO);
    }

    @DeleteMapping("/{animalId}/types/{typeId}")
    public AnimalResultDTO deleteAnimalTypeToAnimal(@PathVariable("animalId") long animalId,
                                                 @PathVariable("typeId") long typeId, HttpServletRequest request) {
        accountService.checkIsAuthorize(request);

        return animalService.deleteAnimalTypeToAnimal(animalId, typeId);
    }
}
