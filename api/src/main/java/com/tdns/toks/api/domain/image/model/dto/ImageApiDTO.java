package com.tdns.toks.api.domain.image.model.dto;

import com.tdns.toks.core.domain.image.entity.Image;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;

public class ImageApiDTO {
    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(name = "ImageUploadRequest", description = "이미지 업로드 요청 모델")
    public static class ImageUploadRequest {
        @NotEmpty(message = "이미지 리소스 필수")
        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "imageFile", description = "이미지 파일")
        private MultipartFile imageFile;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Builder
    @Schema(name = "ImageUploadResponse", description = "이미지 업로드 응답 모델")
    public static class ImageUploadResponse {
        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "id", description = "image id")
        private Long id;

        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "imageUrl", description = "image s3 URL")
        private String imageUrl;

        public static ImageUploadResponse toResponse(Image image) {
            return ImageUploadResponse.builder()
                    .id(image.getId())
                    .imageUrl(image.getImageUrl())
                    .build();
        }
    }
}
