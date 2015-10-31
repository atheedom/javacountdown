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
package org.adoptopenjdk.javacountdown.boundary;

import org.adoptopenjdk.javacountdown.control.MongoDatastore;
import org.adoptopenjdk.javacountdown.entity.*;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.util.Map;

/**
 * The main Data provider for the JAX-RS services.
 *
 * @author AdoptOpenJDK
 */
@Stateless
public class DataProvider {

    @Inject
    MongoDatastore mongoDatastore;

    @Inject
    Event<Visit> visitEvent;

    /**
     * Persists a Visit entity.
     *
     * @param visitTransfer The visit to persist
     */
    public void persistVisit(VisitTransfer visitTransfer) {
        Visit visit = createVisit(visitTransfer);
        correlateGeoPosition(visit, visitTransfer.getLatitude(), visitTransfer.getLongitude());

        mongoDatastore.save(visit);
        visitEvent.fire(visit);
    }

    /**
     * Gets a list of all countries with data to display on the map.
     *
     * @return List of countries and percentage adoption
     */
    public Map<String, Integer> getJdkAdoptionReport() {
        return mongoDatastore.getJdkAdoption();
    }

    private Visit createVisit(VisitTransfer transfer) {
        VersionInfo versionInfo = constructVersionInfo(transfer);

        Visit visit = new Visit();
        visit.setVersion(versionInfo.getMinorVersion());
        visit.setVersionInfo(versionInfo);
        visit.setBrowserInfo(new BrowserInfo(transfer.getBrowserName(), transfer.getBrowserVersion()));
        visit.setOs(transfer.getOs());
        return visit;
    }

    private static VersionInfo constructVersionInfo(VisitTransfer visitTransfer) {
        VersionInfo versionInfo = new VersionInfo();
        if (visitTransfer.getVersion() != null) {
            try {

                String delims = "[.]+";
                String[] tokens = visitTransfer.getVersion().split(delims);
                versionInfo.setMajorVersion(Integer.parseInt(tokens[0]));
                versionInfo.setMinorVersion(Integer.parseInt(tokens[1]));
                versionInfo.setPatchVersion(Integer.parseInt(tokens[2]));
                versionInfo.setBuildVersion(Integer.parseInt(tokens[3]));
            } catch (NumberFormatException | NullPointerException e) {
                System.err.println("Failed to parse version " + visitTransfer.getVersion());
            }
        }
        return versionInfo;
    }

    private void correlateGeoPosition(Visit visit, double latitude, double longitude) {
        GeoPosition geoPosition = mongoDatastore.findGeoPosition(latitude, longitude);
        visit.setGeoPosition(geoPosition);
        visit.setCountry(geoPosition.getCountry());
    }

}
