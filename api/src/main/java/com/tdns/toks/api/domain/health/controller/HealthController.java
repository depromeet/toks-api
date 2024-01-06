package com.tdns.toks.api.domain.health.controller;

import com.tdns.toks.core.common.model.dto.ResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "health", description = "Health API")
@RestController
public class HealthController {
    @GetMapping("/health")
    public ResponseEntity<ResponseDto<String>> health() {
        return ResponseDto.ok("Health Good!");
    }
}
