package com.tdns.toks.api.domain.suggest.service;

import com.tdns.toks.api.domain.cruiser.client.CruiserClient;
import com.tdns.toks.api.domain.cruiser.dto.CruiserRequest;
import com.tdns.toks.api.domain.suggest.model.dto.SuggestRegisterRequest;
import com.tdns.toks.api.domain.suggest.model.dto.SuggestResponse;
import com.tdns.toks.core.domain.suggest.entity.Suggest;
import com.tdns.toks.core.domain.suggest.repository.SuggestRepository;
import com.tdns.toks.core.domain.suggest.type.SuggestStatus;
import com.tdns.toks.core.domain.user.model.dto.UserDetailDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SuggestService {
    private final SuggestRepository suggestRepository;
    private final CruiserClient cruiserClient;

    @Transactional
    public SuggestResponse register(SuggestRegisterRequest request) {
        var userDTO = UserDetailDTO.get();
        var suggest = Suggest.builder()
                .uid(userDTO.getId())
                .title(request.getTitle())
                .content(request.getContent())
                .status(SuggestStatus.PROCEED)
                .build();

        var savedSuggest = suggestRepository.save(suggest);

        cruiserClient.send(new CruiserRequest(
                ":white_check_mark: *똑스에게 제안했어요* :white_check_mark:\n- uid : " + savedSuggest.getUid() + "\n- 닉네임 : " + userDTO.getNickname() + "\n- 제목 : " + savedSuggest.getTitle() + "\n- 본문 : " + savedSuggest.getContent()
        ));

        return SuggestResponse.of(savedSuggest);
    }
}
