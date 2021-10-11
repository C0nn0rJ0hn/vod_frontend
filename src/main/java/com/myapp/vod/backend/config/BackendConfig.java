package com.myapp.vod.backend.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class BackendConfig {

    @Value("${backend.api.endpoint}")
    private String backendApiEndpoint;
}
