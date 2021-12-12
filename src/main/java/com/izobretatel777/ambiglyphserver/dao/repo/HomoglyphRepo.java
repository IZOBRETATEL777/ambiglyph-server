package com.izobretatel777.ambiglyphserver.dao.repo;

import com.izobretatel777.ambiglyphserver.dao.entity.Homoglyph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface HomoglyphRepo extends JpaRepository<Homoglyph, Long> {
    List<Homoglyph> getHomoglyphBySpoofedIn(Collection<Character> character);
}
