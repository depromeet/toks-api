package com.tdns.toks.api.domain.category.service;

import com.tdns.toks.api.domain.category.model.CategoryModel;
import com.tdns.toks.api.domain.category.model.dto.CategoryResponse.GetAllCategoriesResponse;
import com.tdns.toks.api.domain.category.model.dto.CategoryResponse.GetUserCategoriesResponse;
import com.tdns.toks.api.domain.category.model.dto.CategoryResponse.SetUserCategoriesResponse;
import com.tdns.toks.core.common.exception.ApplicationErrorException;
import com.tdns.toks.core.common.exception.ApplicationErrorType;
import com.tdns.toks.core.domain.auth.model.AuthUser;
import com.tdns.toks.core.domain.category.repository.CategoryRepository;
import com.tdns.toks.core.domain.user.entity.UserCategory;
import com.tdns.toks.core.domain.user.repository.UserCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final UserCategoryRepository userCategoryRepository;
    private Map<String, CategoryModel> categoryModels = Collections.emptyMap();

    @Scheduled(fixedRate = 1000 * 60 * 3, initialDelayString = "0")
    public void refreshCategories() {
        log.info("refresh categories info start");
        categoryModels = refresh();
        log.info("refresh categories info complete");
    }

    private Map<String, CategoryModel> refresh() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryModel::from)
                .collect(Collectors.toMap(CategoryModel::getId, Function.identity()));
    }

    public GetAllCategoriesResponse getAll() {
        if (categoryModels.isEmpty()) {
            categoryModels = refresh();
        }

        var categories = categoryModels.values().stream()
                .sorted(Comparator.comparingInt(category -> Integer.parseInt(category.getId())))
                .collect(Collectors.toList());

        return new GetAllCategoriesResponse(categories);
    }

    public Optional<CategoryModel> getOrNull(String categoryId) {
        return Optional.of(categoryModels.get(categoryId));
    }

    public CategoryModel getOrThrow(String categoryId) {
        return getOrNull(categoryId)
                .orElseThrow(() -> new ApplicationErrorException(ApplicationErrorType.NOT_FOUND_CATEGORY_ERROR));
    }

    public GetUserCategoriesResponse getUserCategories(AuthUser authUser) {
        var userCategory = userCategoryRepository.findByUserId(authUser.getId())
                .orElseThrow(() -> new ApplicationErrorException(ApplicationErrorType.NOT_SET_USER_CATEGORY));

        return new GetUserCategoriesResponse(userCategory.getCategoryIds());
    }

    @Transactional
    public SetUserCategoriesResponse setUserCategories(AuthUser authUser, List<String> categories) {
        var savedUserCategories = userCategoryRepository.save(
                UserCategory.builder()
                        .userId(authUser.getId())
                        .categoryIds(categories)
                        .build()
        );

        return new SetUserCategoriesResponse(savedUserCategories);
    }
}
