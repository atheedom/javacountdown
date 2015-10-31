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

import org.adoptopenjdk.javacountdown.entity.CountryAdoption;
import org.adoptopenjdk.javacountdown.entity.Visit;

import javax.ejb.Asynchronous;
import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;
import javax.inject.Inject;

/**
 * Observes events fired by the VisitMongoDatastore
 *
 * @author AdoptOpenJDK
 */
@Asynchronous
public class VisitEventObserver {

    @Inject
    MongoDatastore mongoDatastore;

    /**
     * If the Visit object has been persisted successfully we can update the
     * adoption report data.
     *
     * @param visit
     */
    public void onSuccess(@Observes(during = TransactionPhase.AFTER_SUCCESS) Visit visit) {
        CountryAdoption countryAdoption = mongoDatastore.findCountryAdoption(visit.getCountry());
        if (countryAdoption == null) {
            countryAdoption = new CountryAdoption();
            countryAdoption.setCountry(visit.getCountry());
        }

        countryAdoption.updateTotals(visit);
        mongoDatastore.save(countryAdoption);
    }

    /**
     * If there is a failure in persisting the Visit object we log it and don't
     * update the adoption report data.
     */
    public static void onFailure(@Observes(during = TransactionPhase.AFTER_FAILURE) Visit visit) {
        System.err.println("Observed failed visit event for " + visit);
    }

}
