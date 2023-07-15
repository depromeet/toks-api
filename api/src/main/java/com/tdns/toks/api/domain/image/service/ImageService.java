package com.tdns.toks.api.domain.image.service;

import com.tdns.toks.api.domain.image.model.dto.ImageApiDTO.ImageUploadResponse;
import com.tdns.toks.core.common.service.S3UploadService;
import com.tdns.toks.core.domain.auth.model.AuthUser;
import com.tdns.toks.core.domain.image.entity.Image;
import com.tdns.toks.core.domain.image.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ImageService {
    private final S3UploadService s3UploadService;
    private final ImageRepository imageRepository;

    // TODO : image upload path 이상함
    public ImageUploadResponse uploadImage(AuthUser authUser, MultipartFile image) {
        var imageUrl = s3UploadService.uploadSingleFile(image, authUser.getId().toString());

        var imageUploadLog = imageRepository.save(
                Image.builder()
                        .imageUrl(imageUrl)
                        .createdBy(authUser.getId())
                        .build()
        );

        log.info("uploadImage / uid : {} / imageUrl {}", authUser.getId(), imageUrl);

        return ImageUploadResponse.toResponse(imageUploadLog);
    }
}
