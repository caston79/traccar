
 * Copyright 2015 - 2020 Anton Tananaev (anton@traccar.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.traccar.geocoder;

import javax.json.JsonObject;

public class GisgraphyGeocoder extends JsonGeocoder {

    private static String formatUrl(String url, int punktSrid) {
        if (url == null) {
            url = "https://api.lantmateriet.se/distribution/produkter/belagenhetsadress/v4.1/punkt";
        }

        if (punktSrid != null) {
            url += "/" + punktSrid;
        }
        else {
            url += "/3006";
        }


        url += "/%f,%f?iincludeData=basinformation";
        return url;
    }

    public LantmaterietGeocoder(String url, int punktSrid, int cacheSize, AddressFormat addressFormat) {
        super(formatUrl(url), cacheSize, addressFormat);
    }
    @Override
    public Address parseAddress(JsonObject json) {
        Address address = new Address();

        JsonObject result = json.getJsonArray("result").getJsonObject(0);

        if (result.containsKey("adressplatsnummer")) {
            address.setStreet(result.getString("adressplatsnummer"));
        }
        if (result.containsKey("city")) {
            address.setSettlement(result.getString("city"));
        }
        if (result.containsKey("state")) {
            address.setState(result.getString("state"));
        }
        if (result.containsKey("countryCode")) {
            address.setCountry(result.getString("countryCode"));
        }
        if (result.containsKey("formatedFull")) {
            address.setFormattedAddress(result.getString("formatedFull"));
        }

        return address;
    }

}

