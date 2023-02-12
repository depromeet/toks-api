package com.tdns.toks.api.domain.image.model.dto;

import com.tdns.toks.core.common.utils.ArrayConvertUtil;
import com.tdns.toks.core.domain.image.model.entity.Image;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

public class ImageApiDTO {

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

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Builder
    @Schema(name = "ImageUploadResponse", description = "이미지 업로드 응답 모델")
    public static class ImageBulkUploadResponse {
        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "id", description = "image id")
        private Long id;

        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "imageUrl", description = "image s3 URL")
        private List<String> imageUrl;

        public static ImageBulkUploadResponse toResponse(Image image) {
            return ImageBulkUploadResponse.builder()
                    .id(image.getId())
                    .imageUrl(ArrayConvertUtil.convertToList(image.getImageUrl()))
                    .build();
        }
    }
}
