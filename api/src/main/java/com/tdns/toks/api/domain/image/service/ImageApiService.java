package com.tdns.toks.api.domain.image.service;

import com.tdns.toks.api.domain.image.model.dto.ImageApiDTO.ImageUploadResponse;
import com.tdns.toks.core.common.exception.ApplicationErrorType;
import com.tdns.toks.core.common.exception.SilentApplicationErrorException;
import com.tdns.toks.core.common.service.S3UploadService;
import com.tdns.toks.core.domain.image.service.ImageService;
import com.tdns.toks.core.domain.user.model.dto.UserDetailDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ImageApiService {

    @Value("${config.image-limit}")
    private int imageLimit;

    private final S3UploadService s3UploadService;

    private final ImageService imageService;

    public ImageUploadResponse uploadImage(List<MultipartFile> images, String extraInfo) {
        var uid = UserDetailDTO.get().getId();

        if(extraInfo == null) extraInfo = "extra";
        validateMultipartFile(images);

        var imageUrl = s3UploadService.uploadFiles(images, uid.toString());
        log.info("uploadImage / uid : {} / imageUrl {}", uid, imageUrl);
        var imageUploadLog = imageService.saveImage(imageUrl, uid, extraInfo);
        return ImageUploadResponse.toResponse(imageUploadLog);
    }

    // 첨부된 파일이 없을 때, 10장 초과일 때 Exception
    private void validateMultipartFile(List<MultipartFile> images) {
        if (images.size() == 1 && Objects.equals(images.get(0).getOriginalFilename(), "")) {
            throw new SilentApplicationErrorException(ApplicationErrorType.NO_IMAGE);
        }
        if (images.size() > imageLimit) {
            throw new SilentApplicationErrorException(ApplicationErrorType.EXCEEDED_IMAGE_UPLOAD_LIMIT);
        }
    }
}
