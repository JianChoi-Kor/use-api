package com.example.useapi.maxmind;

import com.maxmind.geoip2.model.CityResponse;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IpLocationInfo {

    private String ipAddress;
    private String country;
    private String city;
    private String isoCode;
    private Double latitude;
    private Double longitude;

    public static IpLocationInfo from(CityResponse cityResponse) {
        if (cityResponse == null) {
            return null;
        }

        return IpLocationInfo.builder()
                .ipAddress(cityResponse.getTraits().getIpAddress())
                .country(cityResponse.getCountry().getName())
                .city(cityResponse.getCity().getName())
                .isoCode(cityResponse.getCountry().getIsoCode())
                .latitude(cityResponse.getLocation().getLatitude())
                .longitude(cityResponse.getLocation().getLongitude())
                .build();
    }
}
