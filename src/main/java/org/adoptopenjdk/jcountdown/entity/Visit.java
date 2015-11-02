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
package org.adoptopenjdk.jcountdown.entity;


import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Visit class, represents an end user hitting a website with their Java applet
 * enabled event.
 *
 * @author AdoptOpenJDK
 */
@Entity(value = "visitors", noClassnameStored = true)
public class Visit {

    @Id
    private ObjectId id;
    private int version;
    private VersionInfo versionInfo;
    private String country;

    @Reference
    private GeoPosition geoPosition;
    private BrowserInfo browserInfo;
    private String os;

    @Transient
    private LocalDateTime time;
    private Date date;

    public Visit() {
        setTime(LocalDateTime.now());
    }

    @PrePersist
    private void dateTimeToDate() {
        System.out.println("dateTimeToDate called");
        setDate(Date.from(getTime().atZone(ZoneId.systemDefault()).toInstant()));
    }

    @PostLoad
    private void dateToDateTime() {
        System.out.println("dateToDateTime called");
        setTime(getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public VersionInfo getVersionInfo() {
        return versionInfo;
    }

    public void setVersionInfo(VersionInfo versionInfo) {
        this.versionInfo = versionInfo;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public GeoPosition getGeoPosition() {
        return geoPosition;
    }

    public void setGeoPosition(GeoPosition geoPosition) {
        this.geoPosition = geoPosition;
    }

    public BrowserInfo getBrowserInfo() {
        return browserInfo;
    }

    public void setBrowserInfo(BrowserInfo browserInfo) {
        this.browserInfo = browserInfo;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Visit other = (Visit) obj;
        return this.id == other.id || (this.id != null && this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return "Visit [id=" + id + ", version=" + version + ", versionInfo=" + versionInfo + ", country=" + country
                + ", geoPosition=" + geoPosition + ", browser=" + browserInfo + ", os=" + os + ", time=" + time + "]";
    }

}
