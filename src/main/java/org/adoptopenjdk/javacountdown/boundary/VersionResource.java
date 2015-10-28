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

import org.adoptopenjdk.javacountdown.entity.VisitTransfer;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.Map;

/**
 * REST Web Service for the javacountdown website.
 *
 * @author AdoptOpenJDK
 */
@Path("version")
@Stateless
public class VersionResource {

    @Inject
    private DataProvider dataProvider;

    /**
     * Retrieves visitor information from web client in JSON format.
     *
     * @param visit The client visit information
     */
    @POST
    @Consumes("application/json")
    public void sendVisit(VisitTransfer visit) {
        dataProvider.persistVisit(visit);
    }

    /**
     * Returns the JDK adoption data.
     *
     * @return A map containing the JDK adoption for the countries
     */
    @GET
    @Produces("application/json")
    public Map<String, Integer> getJdkAdoption() {
        return dataProvider.getJdkAdoptionReport();
    }
}