package ru.vantsyn.it.planet.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vantsyn.it.planet.model.dto.location.LocationDTO;
import ru.vantsyn.it.planet.model.entity.Location;
import ru.vantsyn.it.planet.model.service.AccountService;
import ru.vantsyn.it.planet.model.service.LocationService;

@RestController
@RequestMapping("/locations")
public class LocationController {
    /**
     * Сервис для реализации бизнес логики работы с точками локаций
     */
    private final LocationService locationService;
    /**
     * Сервис для реализации бизнес логики работы с аккаунтами
     */
    private final AccountService accountService;
    /**
     * Класс для автоматического преобразования Entity в DTO и наоборот
     */
    private final ModelMapper mapper;

    /**
     * Конструктор, для реализации DI
     *
     * @param locationService экземпляр LocationService
     * @param accountService экземпляр AccountService
     * @param mapper экземпляр ModelMapper
     */
    public LocationController(LocationService locationService, AccountService accountService, ModelMapper mapper) {
        this.locationService = locationService;
        this.accountService = accountService;
        this.mapper = mapper;
    }

    @PostMapping("")
    public ResponseEntity<Location> createAnimalType(@Valid @RequestBody LocationDTO locationDTO,
                                                       HttpServletRequest request) {
        accountService.checkIsAuthorize(request);

        return new ResponseEntity<>(locationService.createLocation(mapper.map(locationDTO, Location.class)),
                HttpStatus.CREATED);
    }

    @GetMapping("/{pointId}")
    public Location getAnimalTypeById(@PathVariable("pointId") long locationId) {
        return locationService.getLocationById(locationId);
    }

    @PutMapping("/{pointId}")
    public Location updateAnimalType(@PathVariable("pointId") long locationId,
                                       @Valid @RequestBody LocationDTO locationDTO, HttpServletRequest request) {
        accountService.checkIsAuthorize(request);

        return locationService.updateLocation(locationId, mapper.map(locationDTO, Location.class));
    }

    @DeleteMapping("/{pointId}")
    public void deleteAnimalType(@PathVariable("pointId") long locationId, HttpServletRequest request) {
        accountService.checkIsAuthorize(request);
        locationService.deleteLocation(locationId);
    }
}
