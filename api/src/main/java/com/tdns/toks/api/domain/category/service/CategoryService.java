package com.tdns.toks.api.domain.category.service;

import com.tdns.toks.api.domain.category.model.CategoryModel;
import com.tdns.toks.api.domain.category.model.dto.CategoryResponse;
import com.tdns.toks.api.domain.category.model.dto.CategoryResponse.GetAllCategoriesResponse;
import com.tdns.toks.core.common.exception.ApplicationErrorException;
import com.tdns.toks.core.common.exception.ApplicationErrorType;
import com.tdns.toks.core.domain.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
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

    public CategoryResponse.GetMainCategories getMainCategories() {
        var groupedCategories = categoryModels.values()
                .stream()
                .collect(Collectors.groupingBy(category -> category.getId().substring(0, 2), HashMap::new, Collectors.toList()));

        var mainCategories = categoryModels.values().stream()
                .filter(categoryModel -> categoryModel.getDepth() == 1)
                .map(category -> {
                    var subCategories = groupedCategories.getOrDefault(category.getId().substring(0, 2), Collections.emptyList())
                            .stream()
                            .filter(c -> c.getDepth() != 1)
                            .sorted(Comparator.comparingInt(c -> Integer.parseInt(c.getId())))
                            .collect(Collectors.toList());

                    return CategoryResponse.GetMainCategory.of(category, subCategories);
                })
                .sorted(Comparator.comparingInt(c -> Integer.parseInt(c.getId())))
                .collect(Collectors.toList());

        return new CategoryResponse.GetMainCategories(mainCategories);
    }

    public Optional<CategoryModel> getOrNull(String categoryId) {
        return Optional.of(categoryModels.get(categoryId));
    }

    public CategoryModel getOrThrow(String categoryId) {
        return getOrNull(categoryId)
                .orElseThrow(() -> new ApplicationErrorException(ApplicationErrorType.NOT_FOUND_CATEGORY_ERROR));
    }
}
