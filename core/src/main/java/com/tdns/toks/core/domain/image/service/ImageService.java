package com.tdns.toks.core.domain.image.service;

import com.tdns.toks.core.domain.image.model.entity.Image;
import com.tdns.toks.core.domain.image.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;

    @Transactional
    public Image saveImageUrl(String imageUrl, Long userId) {
        var image = Image.builder()
                .imageUrl(imageUrl)
                .createdBy(userId)
                .build();

        return imageRepository.save(image);
    }
}
