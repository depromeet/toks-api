package com.tdns.toks.api.domain.image.service;

import com.tdns.toks.api.domain.image.model.dto.ImageApiDTO.ImageUploadResponse;
import com.tdns.toks.core.common.service.S3UploadService;
import com.tdns.toks.core.domain.image.service.ImageService;
import com.tdns.toks.core.domain.user.model.dto.UserDetailDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ImageApiService {
    private final S3UploadService s3UploadService;

    private final ImageService imageService;

    public ImageUploadResponse uploadImage(MultipartFile image) {
        var uid = UserDetailDTO.get().getId();
        var imageUrl = s3UploadService.uploadSingleFile(image, uid.toString());

        log.info("uploadImage / uid : {} / imageUrl {}", uid, imageUrl);

        var imageUploadLog = imageService.saveImageUrl(imageUrl, uid);
        return ImageUploadResponse.toResponse(imageUploadLog);
    }
}
