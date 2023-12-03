package com.tdns.toks.api.domain.image.service;

import com.tdns.toks.api.domain.image.model.dto.ImageApiDTO.ImageUploadResponse;
import com.tdns.toks.core.common.service.S3UploadService;
import com.tdns.toks.core.domain.auth.model.AuthUser;
import com.tdns.toks.core.domain.image.entity.Image;
import com.tdns.toks.core.domain.image.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${cloud.aws.s3.url}")
    private String s3Url;

    @Value("${cloud.aws.s3.cdn.url}")
    private String cdnUrl;

    // TODO : image upload path 이상함
    public ImageUploadResponse uploadImage(AuthUser authUser, MultipartFile image) {
        var imageUrl = s3UploadService.uploadSingleFile(image, authUser.getId().toString());

        String cdnImageUrl = imageUrl.replaceAll(s3Url, cdnUrl);
        var imageUploadLog = imageRepository.save(
                Image.builder()
                        .imageUrl(cdnImageUrl)
                        .createdBy(authUser.getId())
                        .build()
        );

        log.info("uploadImage / uid : {} / imageUrl {}", authUser.getId(), cdnImageUrl);

        return ImageUploadResponse.toResponse(imageUploadLog);
//        return ImageUploadResponse.toResponse(imageUploadLog);
    }
}
