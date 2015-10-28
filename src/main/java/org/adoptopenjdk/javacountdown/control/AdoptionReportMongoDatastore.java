/*
 * Copyright [2013] Adopt OpenJDK Programme
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.adoptopenjdk.javacountdown.control;

import org.adoptopenjdk.javacountdown.entity.AdoptionReportCountry;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Datastore for the JDK adoption collection.
 *
 * @author AdoptOpenJDK
 */
public class AdoptionReportMongoDatastore extends BasicDAO<AdoptionReportCountry, Key<AdoptionReportCountry>> {

    @Inject
    public AdoptionReportMongoDatastore(Datastore datastore) {
        super(datastore);
    }

    /**
     * Finds the document for the given country in the JDK adoption collecting
     *
     * @param country
     * @return
     */
    public AdoptionReportCountry getCountryTotals(String country) {
        Query<AdoptionReportCountry> query = createQuery().field("country").equal(country);
        return query.get();
    }

    /**
     * Returns the data used to generate the world map of JDK 7 adoption.
     *
     * @return
     */
    public Map<String, Integer> getJdkAdoption() {
        // TODO ensure that this query does not return null for country
        List<AdoptionReportCountry> adoptionByCountry = createQuery().retrievedFields(true, "country", "percentage").asList();

        Map<String, Integer> countryPercentageAdoption = new HashMap<>();
        for (AdoptionReportCountry country : adoptionByCountry) {
            if (country.getCountry() != null) {
                countryPercentageAdoption.put(country.getCountry(), country.getPercentage());
            }
        }

        System.out.println("Retrieved JDK adoption: " + countryPercentageAdoption);
        return countryPercentageAdoption;
    }

}
