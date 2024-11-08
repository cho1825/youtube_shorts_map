package com.youtube_shorts_map.repository;

import com.youtube_shorts_map.domain.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Long> {

    City findByNameContaining(String cityName);

    Optional<City> findByRegionCode(String regionCode);


    @Query("SELECT DISTINCT c FROM City c WHERE EXISTS (SELECT cy FROM CityYoutuber cy WHERE cy.city = c)")
    List<City> findAllCitiesWithYoutubers();

}
