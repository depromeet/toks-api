package com.tdns.toks.api.domain.study.model.dto;

import com.tdns.toks.core.common.model.entity.EnumValue;
import com.tdns.toks.core.common.utils.EnumConvertUtil;
import com.tdns.toks.core.common.utils.UrlConvertUtil;
import com.tdns.toks.core.domain.study.model.dto.TagDTO;
import com.tdns.toks.core.domain.study.model.entity.Study;
import com.tdns.toks.core.domain.study.model.entity.Tag;
import com.tdns.toks.core.domain.study.type.StudyCapacity;
import com.tdns.toks.core.domain.user.model.dto.UserDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME;

public class StudyApiDTO {
    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(name = "StudyCreateRequest", description = "STUDY 생성 요청 모델")
    public static class StudyCreateRequest {
        @NotEmpty(message = "스터디 이름은 필수 항목 입니다.")
        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "name", description = "이름이름")
        private String name;

        @NotEmpty(message = "스터디 설명은 필수 항목 입니다.")
        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "description", description = "설명설명")
        private String description;

        @NotNull(message = "시작 일자는 필수 항목 입니다.")
        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "startedAt", description = "시작 일자 ex: 2000-10-31T01:30:00.000-05:00")
        @DateTimeFormat(iso = DATE_TIME)
        private LocalDateTime startedAt;

        @NotNull(message = "종료 일자는 필수 항목 입니다.")
        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "endedAt", description = "종료 일자 ex: 2000-10-31T01:30:00.000-05:00")
        @DateTimeFormat(iso = DATE_TIME)
        private LocalDateTime endedAt;

        @NotNull(message = "스터디 규모는 필수 항목 입니다.")
        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "capacity", description = "스터디 규모")
        private StudyCapacity capacity;

        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "tagList", description = "tag 키워드 리스트")
        private List<String> tagList;

    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Schema(name = "StudyApiResponse", description = "STUDY 응답 모델")
    public static class StudyApiResponse {
        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "id", description = "study id")
        private Long id;

        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "name", description = "이름이름")
        private String name;

        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "description", description = "설명설명")
        private String description;

        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "startedAt", description = "2022-11-12")
        private LocalDateTime startedAt;

        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "endedAt", description = "2022-12-21")
        private LocalDateTime endedAt;

        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "capacity", description = "SMALL || MEDIUM || LARGE")
        private StudyCapacity capacity;

        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "inviteUrl", description = "초대 url")
        private String inviteUrl;

        private UserDTO user;

        private List<TagDTO> tagList;

        public static StudyApiResponse toResponse(Study study, UserDTO userDTO, List<Tag> tagList) {
            StudyApiResponse response = new StudyApiResponse();
            response.id = study.getId();
            response.name = study.getName();
            response.description = study.getDescription();
            response.startedAt = study.getStartedAt();
            response.endedAt = study.getEndedAt();
            response.capacity = study.getCapacity();
            response.inviteUrl = UrlConvertUtil.convertToInviteUrl(study.getId());
            response.user = userDTO;
            response.tagList = tagList.stream().map(tag -> TagDTO.of(tag)).collect(Collectors.toList());
            return response;
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Schema(name = "StudyFormResponse", description = "STUDY 생성 폼데이터 조회 모델")
    public static class StudyFormResponse {
        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "capacities", description = "스터디 규모")
        private List<EnumValue> capacities = EnumConvertUtil.convertToEnumValue(StudyCapacity.class);
    }

    @AllArgsConstructor(staticName = "of")
    @NoArgsConstructor
    @Getter
    @Setter
    @Schema(name = "TagResponse", description = "태그 조회 모델")
    public static class TagResponse {
        @Schema(accessMode = Schema.AccessMode.READ_WRITE, name = "tagList", description = "태그 리스트")
        private List<TagDTO> tagList;
    }

    @AllArgsConstructor(staticName = "of")
    @NoArgsConstructor
    @Getter
    @Setter
    @Schema(name = "TagCreateRequest", description = "태그 생성 모델")
    public static class TagCreateRequest {
        @Schema(accessMode = Schema.AccessMode.READ_WRITE, name = "keyword", description = "키워드")
        @NotBlank
        private String keyword;
    }
}
