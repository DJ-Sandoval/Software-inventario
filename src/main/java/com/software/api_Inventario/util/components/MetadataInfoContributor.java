package com.software.api_Inventario.util.components;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MetadataInfoContributor implements InfoContributor {

    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetails(Map.of(
                "name", "api-inventario",
                "description","api para la gestion de inventario para un pequenio negocio",
                "autor", "DevSandoval DevSofi",
                "proyecto", "api-Inventario",
                "tecnologia", "Spring Boot + MySQL + docker + jenkins",
                "version","2.5"
        ));
    }
}
