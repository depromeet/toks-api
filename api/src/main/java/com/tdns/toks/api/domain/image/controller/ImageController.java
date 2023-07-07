package com.tdns.toks.api.domain.image.controller;

import com.tdns.toks.api.domain.image.model.dto.ImageApiDTO.ImageUploadResponse;
import com.tdns.toks.api.domain.image.service.ImageApiService;
import com.tdns.toks.core.common.model.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "이지미 관리", description = "IMAGE API")
@RestController
@RequestMapping(path = "/api/v1/images")
@RequiredArgsConstructor
public class ImageController {
    private final ImageApiService imageApiService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "이미지 생성")
    public ResponseEntity<?> uploadImage(
            @RequestPart(name = "image") MultipartFile image
    ) {
        var response = imageApiService.uploadImage(image);
        return ResponseDto.created(response);
    }
}
