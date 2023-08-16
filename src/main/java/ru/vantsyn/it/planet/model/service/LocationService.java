package ru.vantsyn.it.planet.model.service;

import ru.vantsyn.it.planet.model.entity.AnimalType;
import ru.vantsyn.it.planet.model.entity.Location;

public interface LocationService {
    Location createLocation(Location location);
    Location getLocationById(long id);
    Location updateLocation(long id, Location location);
    void deleteLocation(long id);
}
