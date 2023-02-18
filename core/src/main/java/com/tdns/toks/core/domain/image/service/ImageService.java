package com.tdns.toks.core.domain.image.service;

import com.tdns.toks.core.common.utils.ArrayConvertUtil;
import com.tdns.toks.core.domain.image.model.entity.Image;
import com.tdns.toks.core.domain.image.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;

    public Image saveImage(List<String> imageUrl, Long userId, String extraInfo) {
        var image = Image.builder()
                .imageUrl(ArrayConvertUtil.convertToString(imageUrl))
                .createdBy(userId)
                .extra(extraInfo)
                .build();
        return imageRepository.save(image);
    }
}
