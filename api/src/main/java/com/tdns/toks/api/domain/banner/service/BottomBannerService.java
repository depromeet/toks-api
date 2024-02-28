package com.tdns.toks.api.domain.banner.service;

import com.tdns.toks.api.domain.banner.model.BottomBannerModel;
import com.tdns.toks.api.domain.banner.model.dto.BottomBannerResponse;
import com.tdns.toks.core.domain.auth.model.AuthUser;
import com.tdns.toks.core.domain.banner.repository.BottomBannerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BottomBannerService {
    private final BottomBannerRepository bottomBannerRepository;
    private Map<Long, BottomBannerModel> bottomBanners = Collections.emptyMap();

    @Scheduled(fixedRate = 1000 * 60 * 3, initialDelayString = "0")
    public void refreshBottomBanners() {
        log.info("refresh bottomBanners info start");
        bottomBanners = refresh();
        log.info("refresh bottomBanners info complete");
    }

    public Map<Long, BottomBannerModel> refresh() {
        return bottomBannerRepository.findAllByIsActive(true)
                .stream()
                .map(BottomBannerModel::from)
                .collect(Collectors.toMap(BottomBannerModel::getId, Function.identity()));
    }

    public List<BottomBannerModel> getAll() {
        return bottomBanners.values()
                .stream()
                .sorted(Comparator.comparingInt(BottomBannerModel::getSeq))
                .collect(Collectors.toList());
    }

    public BottomBannerResponse.GetAllBottomBannerResponse getAllBottomBanners(@Nullable AuthUser authUser) {
        return new BottomBannerResponse.GetAllBottomBannerResponse(getAll());
    }
}
