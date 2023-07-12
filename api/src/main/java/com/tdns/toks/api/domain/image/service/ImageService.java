package com.tdns.toks.api.domain.image.service;

import com.tdns.toks.api.domain.image.model.dto.ImageApiDTO.ImageUploadResponse;
import com.tdns.toks.core.common.service.S3UploadService;
import com.tdns.toks.core.domain.image.model.entity.Image;
import com.tdns.toks.core.domain.image.repository.ImageRepository;
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
public class ImageService {
    private final S3UploadService s3UploadService;
    private final ImageRepository imageRepository;

    public ImageUploadResponse uploadImage(MultipartFile image) {
        var uid = UserDetailDTO.get().getId();
        var imageUrl = s3UploadService.uploadSingleFile(image, uid.toString());

        var imageUploadLog = imageRepository.save(
                Image.builder()
                        .imageUrl(imageUrl)
                        .createdBy(uid)
                        .build()
        );

        log.info("uploadImage / uid : {} / imageUrl {}", uid, imageUrl);

        return ImageUploadResponse.toResponse(imageUploadLog);
    }
}
