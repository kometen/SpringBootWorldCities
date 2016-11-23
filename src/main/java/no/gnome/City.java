/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.gnome;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author claus
 */
@Entity
@Table(name = "city")
public class City implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "serial")
    private final Integer id;
    private final String countrycode;
    private final String name_lowercase;
    private final String name;
    private final String region;
    private Integer population;
    private Float latitude;
    private Float longitude;

    public City() {
        this.id = -1;
        this.countrycode = "N/A";
        this.name_lowercase = "N/A";
        this.name = "N/A";
        this.region = "N/A";
        this.population = -1;
        this.latitude = Float.valueOf(-360);
        this.longitude = Float.valueOf(-360);
    }

    public City(String record) {
        String[] r = record.split((","));
        if (r.length != 7) {
            throw new StringIndexOutOfBoundsException("Incorrect number of fields in record.");
        }

        this.id = -1;
        this.countrycode = r[0];
        this.name_lowercase = r[1];
        this.name = r[2];
        this.region = r[3];
        try {
            this.population = Integer.parseInt(r[4]);
        } catch (NumberFormatException e) {
            //this.population = -1;
        }
        try {
            this.latitude = Float.parseFloat(r[5]);
        } catch (NumberFormatException e) {
            //this.latitude = Float.valueOf("-360");
            //throw new NumberFormatException("error converting");
        }
        try {
            this.longitude = Float.parseFloat(r[6]);
        } catch (NumberFormatException e) {
            //this.latitude = Float.valueOf("-360");
            //throw new NumberFormatException("error converting");
        }
    }
    /**
     * @return the countryCode
     */
    public String getCountry_code() {
        return countrycode;
    }

    /**
     * @return the nameLowerCase
     */
    public String getName_lowerCase() {
        return name_lowercase;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the region
     */
    public String getRegion() {
        return region;
    }

    /**
     * @return the population
     */
    public Integer getPopulation() {
        return population;
    }

    /**
     * @return the latitude
     */
    public Float getLatitude() {
        return latitude;
    }

    /**
     * @return the longitude
     */
    public Float getLongitude() {
        return longitude;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

}
