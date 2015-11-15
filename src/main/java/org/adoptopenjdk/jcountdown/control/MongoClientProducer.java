/*
 * Copyright 2013 Adopt OpenJDK.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.adoptopenjdk.jcountdown.control;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

/**
 * Produces a Mongo client objects used by the Datastores to persist data in MongoDB.
 *
 * @author AdoptOpenJDK
 */
public class MongoClientProducer {
    
    @Inject
    MongoConfiguration mongoConfiguration;

    @Produces
    @ApplicationScoped
    public MongoClient produceClient() {
        // default host is localhost, default port is 27017       
       
        ServerAddress serverAddress = new ServerAddress(mongoConfiguration.getHost(), mongoConfiguration.getPort());
             
        MongoCredential mongoCredential = MongoCredential
                                                .createCredential(
                                                        mongoConfiguration.getUsername(),
                                                        mongoConfiguration.getDatabaseName(),
                                                        mongoConfiguration.getPassword()
                                                        );
       
        List<MongoCredential> credentials = new ArrayList<>();
        credentials.add(mongoCredential);
        return new MongoClient(serverAddress, credentials);
    }

 
}
