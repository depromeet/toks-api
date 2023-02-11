package com.tdns.toks.api.domain.image.model.dto;

import com.tdns.toks.core.common.utils.ArrayConvertUtil;
import com.tdns.toks.core.domain.image.model.entity.Image;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public class ImageApiDTO {
    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(name = "ImageUploadRequest", description = "이미지 업로드 요청 모델")
    public static class ImageUploadRequest {
        @NotEmpty(message = "업로드 info")
        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "uploadInfo", description = "이미지 업로드 정보 (퀴즈 생성시 이미지 [QuizID])")
        private String uploadInfo;
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
        private List<String> imageUrl;

        public static ImageUploadResponse toResponse(Image image) {
            return ImageUploadResponse.builder()
                    .id(image.getId())
                    .imageUrl(ArrayConvertUtil.convertToList(image.getImageUrl()))
                    .build();
        }
    }
}
