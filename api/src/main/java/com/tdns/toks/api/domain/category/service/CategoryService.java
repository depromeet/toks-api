package com.tdns.toks.api.domain.category.service;

import com.tdns.toks.api.domain.category.model.dto.CategoryModel;
import com.tdns.toks.api.domain.category.model.dto.CategoryResponse;
import com.tdns.toks.core.domain.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private Map<String, CategoryModel> categoryModels = Collections.emptyMap();

    @Scheduled(cron = "0 */5 * * * *")
    public void refreshCategories() {
        categoryModels = refresh();
    }

    private Map<String, CategoryModel> refresh() {
        var categories = categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))
                .stream()
                .map(CategoryModel::from)
                .collect(Collectors.toMap(CategoryModel::getId, Function.identity()));

        log.info("refresh categories info success");

        return categories;
    }

    public CategoryResponse getAll() {
        if (categoryModels.isEmpty()) {
            categoryModels = refresh();
        }

        return new CategoryResponse(new ArrayList<>(categoryModels.values()));
    }
}
