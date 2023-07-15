package com.tdns.toks.api.domain.image.controller;

import com.tdns.toks.api.domain.image.service.ImageService;
import com.tdns.toks.core.common.model.dto.ResponseDto;
import com.tdns.toks.core.domain.auth.model.AuthUser;
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
    private final ImageService imageService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "이미지 생성")
    public ResponseEntity<?> uploadImage(
            AuthUser authUser,
            @RequestPart(name = "image") MultipartFile image
    ) {
        var response = imageService.uploadImage(authUser, image);
        return ResponseDto.created(response);
    }
}
