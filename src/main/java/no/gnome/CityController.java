package no.gnome;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

@RestController
public class CityController {
    
    @Autowired
    private CityRepository cityInterface;
    
    @RequestMapping(value = "/city/{name}", method = RequestMethod.GET)
    public List<City> cityByName(@PathVariable("name") String name) {
        return cityInterface.findByName(name);
    }
    
    @Cacheable("cities")
    @RequestMapping(value = "/cities/{latitude}/{longitude}", method = RequestMethod.GET)
    public List<City> cityByLatitudeLongitude(
            @PathVariable("latitude") String latitude,
            @PathVariable("longitude") String longitude)
    {
        // Default latitude and longitude.
        Float lat = (float) 61.7428745;
        Float lng = (float) 6.3968833;
        
        // Default radius.
        Integer radius = 20;

        // Parse latitude and longitude from path variable.
        try {
            lat = Float.parseFloat(latitude);
        } catch (NumberFormatException e) { }
        
        try {
            lng = Float.parseFloat(longitude);
        } catch (NumberFormatException e) { }

        return get_locations(lat, lng, radius);
    }

    @Cacheable("cities")
    @RequestMapping(value = "/cities/{latitude}/{longitude}/{radius}", method = RequestMethod.GET)
    public List<City> cityByLatitudeLongitudeRadius(
            @PathVariable("latitude") String latitude,
            @PathVariable("longitude") String longitude,
            @PathVariable("radius") String string_radius)
    {
        // Default latitude and longitude.
        Float lat = (float) 61.7428745;
        Float lng = (float) 6.3968833;
        
        // Default radius.
        Integer radius = 20;

        // Parse latitude and longitude and radius from path variable.
        try {
            lat = Float.parseFloat(latitude);
        } catch (NumberFormatException e) { }
        
        try {
            lng = Float.parseFloat(longitude);
        } catch (NumberFormatException e) { }

        try {
            radius = Integer.parseInt(string_radius);
        } catch (NumberFormatException e) { }
        
        return get_locations(lat, lng, radius);
    }
    
    private List<City> get_locations(Float latitude, Float longitude, Integer radius) {
        // Define center.
        LatLng center = new LatLng(latitude, longitude);

        // Get latitude west and east of center.
        LatLng latitude_west = LatLngTool.travel(center, 180, radius, LengthUnit.KILOMETER);
        LatLng latitude_east = LatLngTool.travel(center, 0, radius, LengthUnit.KILOMETER);

        // Get longitude south and north of center.
        LatLng longitude_south = LatLngTool.travel(center, 270, radius, LengthUnit.KILOMETER);
        LatLng longitude_north = LatLngTool.travel(center, 90, radius, LengthUnit.KILOMETER);

        // Convert back to latitude and longitude and form a imperfect square.
        Float lat_west = (float) latitude_west.getLatitude();
        Float lat_east = (float) latitude_east.getLatitude();
        Float lng_south = (float) longitude_south.getLongitude();
        Float lng_north = (float) longitude_north.getLongitude();
        
        List<City> cities = cityInterface.findByLatitudeBetweenAndLongitudeBetween(lat_west, lat_east, lng_south, lng_north); 
        
        Predicate<City> outside = (city) -> {
            LatLng p = new LatLng(city.getLatitude(), city.getLongitude());
            double d = LatLngTool.distance(center, p, LengthUnit.KILOMETER);
            if (d > radius) {
                return true;
            }
            return false;
        };
        
        cities.removeIf(outside);
        
        return cities;
    }


}
