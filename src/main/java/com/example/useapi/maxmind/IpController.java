package com.example.useapi.maxmind;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.AsnResponse;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.model.CountryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.InetAddress;

@Slf4j
@RequiredArgsConstructor
@RestController
public class IpController {

    private final IpService ipService;

    @GetMapping("/ip")
    public ResponseEntity<?> getIpInfo(HttpServletRequest request) {
        String ip = ipService.getClientIp(request);
        log.info("client ip: " + ip);

        IpLocationInfo ipLocationInfo = null;
        try {
            ipLocationInfo = ipService.getLocationInfo(ip);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok(ipLocationInfo);
    }

    @GetMapping("/example/city")
    public ResponseEntity<?> getIpInfoFromCityDB(HttpServletRequest request) throws IOException, GeoIp2Exception {
        String ip = "127.0.0.1";

        ClassPathResource resource = new ClassPathResource("data/GeoLite2-City.mmdb");
        DatabaseReader databaseReader = new DatabaseReader.Builder(resource.getFile()).build();

        InetAddress ipAddress = InetAddress.getByName(ip);
        CityResponse response = databaseReader.city(ipAddress);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/example/country")
    public ResponseEntity<?> getIpInfoFromCountryDB(HttpServletRequest request) throws IOException, GeoIp2Exception {
        String ip = "127.0.0.1";

        ClassPathResource resource = new ClassPathResource("data/GeoLite2-Country.mmdb");
        DatabaseReader databaseReader = new DatabaseReader.Builder(resource.getFile()).build();

        InetAddress ipAddress = InetAddress.getByName(ip);
        CountryResponse response = databaseReader.country(ipAddress);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/example/asn")
    public ResponseEntity<?> getIpInfoFromASNDB(HttpServletRequest request) throws IOException, GeoIp2Exception {
        String ip = "127.0.0.1";

        ClassPathResource resource = new ClassPathResource("data/GeoLite2-ASN.mmdb");
        DatabaseReader databaseReader = new DatabaseReader.Builder(resource.getFile()).build();

        InetAddress ipAddress = InetAddress.getByName(ip);
        AsnResponse response = databaseReader.asn(ipAddress);

        return ResponseEntity.ok(response);
    }
}
