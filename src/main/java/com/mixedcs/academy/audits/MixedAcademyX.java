package com.mixedcs.academy.audits;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MixedAcademyX implements InfoContributor {

    @Override
    public void contribute(Info.Builder builder) {
        Map<String, String> academyMap = new HashMap<String, String>();
        academyMap.put("App Name", "MiXed Academy App");
        academyMap.put("App Description", "MiXed Academy Web Application for Trainee and Admin");
        academyMap.put("App Version", "v2.1.0");
        academyMap.put("Contact Email", "info@mixedcs.com");
        academyMap.put("Contact Mobile", "+880 1700 00 00 00");
        builder.withDetail("mixedcs-info", academyMap);
    }
}
