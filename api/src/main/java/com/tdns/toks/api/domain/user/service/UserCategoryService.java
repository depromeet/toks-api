package com.tdns.toks.api.domain.user.service;

import com.tdns.toks.core.common.exception.ApplicationErrorException;
import com.tdns.toks.core.common.exception.ApplicationErrorType;
import com.tdns.toks.core.domain.auth.model.AuthUser;
import com.tdns.toks.core.domain.user.entity.UserCategory;
import com.tdns.toks.core.domain.user.repository.UserCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.tdns.toks.api.domain.user.model.dto.UserCategoryResponse.GetUserCategoriesResponse;
import static com.tdns.toks.api.domain.user.model.dto.UserCategoryResponse.SetUserCategoriesResponse;

@Service
@RequiredArgsConstructor
public class UserCategoryService {
    private final UserCategoryRepository userCategoryRepository;

    public GetUserCategoriesResponse getUserCategories(AuthUser authUser) {
        var userCategory = userCategoryRepository.findByUserId(authUser.getId())
                .orElseThrow(() -> new ApplicationErrorException(ApplicationErrorType.NOT_SET_USER_CATEGORY));

        return new GetUserCategoriesResponse(userCategory.getCategoryIds());
    }

    @Transactional
    public SetUserCategoriesResponse setUserCategories(AuthUser authUser, List<String> categories) {
        var userCategory = userCategoryRepository.findByUserId(authUser.getId())
                .orElse(
                        UserCategory.builder()
                                .userId(authUser.getId())
                                .categoryIds(categories)
                                .build()
                );

        userCategory.updateCategoryIds(categories);

        userCategoryRepository.save(userCategory);

        return new SetUserCategoriesResponse(userCategory);
    }
}
