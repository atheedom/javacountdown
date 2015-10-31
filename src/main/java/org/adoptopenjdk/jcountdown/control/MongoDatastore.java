package org.adoptopenjdk.jcountdown.control;

import com.mongodb.MongoClient;
import org.adoptopenjdk.jcountdown.entity.CountryAdoption;
import org.adoptopenjdk.jcountdown.entity.GeoPosition;
import org.adoptopenjdk.jcountdown.entity.Visit;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.geo.GeoJson;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Control / service to access objects stored in MongoDB.
 *
 * @author AdoptOpenJDK
 */
public class MongoDatastore {

    private static final String DB = "jcountdown";

    @Inject
    MongoClient mongoClient;
    Datastore datastore;

    @PostConstruct
    private void setupDatastore() {
        Morphia morphia = new Morphia();
        datastore = morphia.createDatastore(mongoClient, DB);
    }

    public Key<Visit> save(Visit visit) {
        return datastore.save(visit);
    }

    public Key<CountryAdoption> save(CountryAdoption countryAdoption) {
        return datastore.save(countryAdoption);
    }

    /**
     * This method finds the nearest geo position based on the given latitude/longitude.
     * Refer to http://www.maxmind.com/en/worldcities for the data behind it.
     *
     * @param latitude  The latitude
     * @param longitude The longitude
     * @return A GeoPosition entity
     */
    public GeoPosition findGeoPosition(double latitude, double longitude) {
        return datastore.find(GeoPosition.class)
                .field("location.coordinates")
                .near(GeoJson.point(latitude, longitude))
                .limit(1).get();
    }

    /**
     * Finds the document for the given country in the JDK adoption collecting
     */
    public CountryAdoption findCountryAdoption(String country) {
        return datastore.createQuery(CountryAdoption.class).field("country").equal(country).limit(1).get();
    }

    /**
     * Returns the data used to generate the world map of JDK 7 (or later) adoption.
     */
    public Map<String, Integer> getJdkAdoption() {
        // TODO ensure that this query does not return null for country
        List<CountryAdoption> adoptionByCountry = datastore.createQuery(CountryAdoption.class).retrievedFields(true, "country", "percentage").asList();

        Map<String, Integer> countryPercentageAdoption = adoptionByCountry.stream()
                .filter(a -> a.getCountry() != null)
                .collect(HashMap::new, (m, a) -> m.put(a.getCountry(), a.getPercentage()), Map::putAll);

        System.out.println("Retrieved JDK adoption: " + countryPercentageAdoption);
        return countryPercentageAdoption;
    }

}
