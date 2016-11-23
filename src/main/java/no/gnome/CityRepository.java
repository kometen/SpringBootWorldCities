package no.gnome;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CityRepository extends CrudRepository<City, Long> {
    
    List<City> findByName(String name);
    
    List<City> findByLatitudeBetweenAndLongitudeBetween(Float lat_east, Float lat_west, Float lng_south, Float lng_north);
    
}
