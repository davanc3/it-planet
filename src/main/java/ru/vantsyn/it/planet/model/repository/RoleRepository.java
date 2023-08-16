package ru.vantsyn.it.planet.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vantsyn.it.planet.model.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findFirstByName(String name);
}
