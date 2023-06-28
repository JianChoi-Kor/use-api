package com.example.useapi.maxmind;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
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

    @GetMapping("/ip2")
    public ResponseEntity<?> getIpInfo2(HttpServletRequest request) throws IOException, GeoIp2Exception {
        String ip = "127.0.0.1";

        ClassPathResource resource = new ClassPathResource("data/GeoLite2-City.mmdb");
        DatabaseReader databaseReader = new DatabaseReader.Builder(resource.getFile()).build();

        InetAddress ipAddress = InetAddress.getByName(ip);
        CityResponse response = databaseReader.city(ipAddress);

        return ResponseEntity.ok(response);
    }
}
