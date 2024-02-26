package com.tdns.toks.api.domain.admin.service;

import com.tdns.toks.api.domain.admin.dto.AdminBottomBannerResponse;
import com.tdns.toks.api.domain.admin.dto.AdminBottomBannerSaveOrUpdateRequest;
import com.tdns.toks.core.common.exception.ApplicationErrorException;
import com.tdns.toks.core.common.exception.ApplicationErrorType;
import com.tdns.toks.core.domain.auth.model.AuthUser;
import com.tdns.toks.core.domain.banner.entity.BottomBanner;
import com.tdns.toks.core.domain.banner.repository.BottomBannerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminBottomBannerService {
    private final BottomBannerRepository bottomBannerRepository;

    public AdminBottomBannerResponse get(AuthUser authUser, Long id) {
        var bottomBanner = bottomBannerRepository.findById(id)
                .orElseThrow(() -> new ApplicationErrorException(ApplicationErrorType.NOT_FOUND_BOTTOM_BANNER_ERROR));

        return AdminBottomBannerResponse.from(bottomBanner);
    }

    public Page<AdminBottomBannerResponse> getAll(AuthUser authUser, Integer page, Integer size) {
        var pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("createdAt")));
        return bottomBannerRepository.findAll(pageable).map(AdminBottomBannerResponse::from);
    }

    @Transactional
    public AdminBottomBannerResponse create(AuthUser authUser, AdminBottomBannerSaveOrUpdateRequest request) {
        var bottomBanner = BottomBanner.builder()
                .title(request.getTitle())
                .seq(request.getSeq())
                .imageUrl(request.getImageUrl())
                .landingUrl(request.getLandingUrl())
                .isActive(request.isActive())
                .build();

        var createdBottomBanner = bottomBannerRepository.save(bottomBanner);

        return AdminBottomBannerResponse.from(createdBottomBanner);
    }

    @Transactional
    public AdminBottomBannerResponse update(
            AuthUser authUser,
            Long id,
            AdminBottomBannerSaveOrUpdateRequest request
    ) {
        var bottomBanner = bottomBannerRepository.findById(id)
                .orElseThrow(() -> new ApplicationErrorException(ApplicationErrorType.NOT_FOUND_BOTTOM_BANNER_ERROR));

        bottomBanner.update(
                request.getTitle(),
                request.getSeq(),
                request.getImageUrl(),
                request.getLandingUrl(),
                request.isActive()
        );

        var updatedBottomBanner = bottomBannerRepository.save(bottomBanner);

        return AdminBottomBannerResponse.from(updatedBottomBanner);
    }

    @Transactional
    public void delete(AuthUser authUser, Long id) {
        var bottomBanner = bottomBannerRepository.findById(id)
                .orElseThrow(() -> new ApplicationErrorException(ApplicationErrorType.NOT_FOUND_BOTTOM_BANNER_ERROR));

        bottomBannerRepository.delete(bottomBanner);
    }
}
