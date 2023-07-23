package com.tdns.toks.api.domain.category.service;

import com.tdns.toks.api.domain.category.model.CategoryModel;
import com.tdns.toks.api.domain.category.model.dto.CategoryResponse.GetAllCategoriesResponse;
import com.tdns.toks.api.domain.category.model.dto.CategoryResponse.GetUserCategoriesResponse;
import com.tdns.toks.api.domain.category.model.dto.CategoryResponse.SetUserCategoriesResponse;
import com.tdns.toks.core.common.exception.ApplicationErrorException;
import com.tdns.toks.core.common.exception.ApplicationErrorType;
import com.tdns.toks.core.domain.category.entity.UserCategory;
import com.tdns.toks.core.domain.category.repository.CategoryRepository;
import com.tdns.toks.core.domain.category.repository.UserCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;
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
        categoryModels = refresh();
    }

    private Map<String, CategoryModel> refresh() {
        var categories = categoryRepository.findAll()
                .stream()
                .map(CategoryModel::from)
                .collect(Collectors.toMap(CategoryModel::getId, Function.identity()));

        log.info("refresh categories info success");

        return new TreeMap<>(categories);
    }

    public GetAllCategoriesResponse getAll() {
        if (categoryModels.isEmpty()) {
            categoryModels = refresh();
        }

        return new GetAllCategoriesResponse(new ArrayList<>(categoryModels.values()));
    }

    public Optional<CategoryModel> get(String categoryId) {
        return Optional.of(categoryModels.get(categoryId));
    }

    public GetUserCategoriesResponse getUserCategories(long userId) {
        UserCategory userCategory = userCategoryRepository.findByUserId(userId).orElseThrow(() -> new ApplicationErrorException(ApplicationErrorType.NOT_SET_USER_CATEGORY));
        return new GetUserCategoriesResponse(userCategory.getCategoryIds());
    }

    public SetUserCategoriesResponse setUserCategories(long userId, List<String> categories) {
        UserCategory savedUserCategories = userCategoryRepository.save(UserCategory.builder()
                .userId(userId)
                .categoryIds(categories)
                .build());
        return new SetUserCategoriesResponse(savedUserCategories);
    }
}
