package com.tdns.toks.api.domain.study.model.dto;

import com.tdns.toks.core.common.model.entity.EnumValue;
import com.tdns.toks.core.common.utils.EnumConvertUtil;
import com.tdns.toks.core.common.utils.UrlConvertUtil;
import com.tdns.toks.core.domain.study.model.dto.StudyDTO.FinishedStudyInfoLight;
import com.tdns.toks.core.domain.study.model.dto.StudyDTO.StudyInfoLight;
import com.tdns.toks.core.domain.study.model.dto.TagDTO;
import com.tdns.toks.core.domain.study.model.entity.Study;
import com.tdns.toks.core.domain.study.model.entity.Tag;
import com.tdns.toks.core.domain.study.type.StudyCapacity;
import com.tdns.toks.core.domain.user.model.dto.UserDTO;
import com.tdns.toks.core.domain.user.model.dto.UserSimpleDTO;
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

        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "startedAt", description = "시작 일자 ex: 2000-10-31T01:30:00.000-05:00")
        private LocalDateTime startedAt;

        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "endedAt", description = "종료 일자 ex: 2000-10-31T01:30:00.000-05:00")
        private LocalDateTime endedAt;

        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "capacity", description = "SMALL || MEDIUM || LARGE")
        private StudyCapacity capacity;

        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "inviteUrl", description = "초대 url")
        private String inviteUrl;

        private UserDTO user;

        private List<TagDTO> tags;

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
            response.tags = tagList.stream().map(tag -> TagDTO.of(tag)).collect(Collectors.toList());
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

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Schema(name = "StudiesInfoResponse", description = "사용자 스터디 목록 반환 모델")
    public static class StudiesInfoResponse{
        @Schema(accessMode = Schema.AccessMode.READ_WRITE, name = "studies", description = "참여 스터디 목록")
        private List<StudyInfoLight> studies;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Schema(name = "FinishedStudiesInfoResponse", description = "사용자 종료된 스터디 목록 반환 모델")
    public static class FinishedStudiesInfoResponse{
        @Schema(accessMode = Schema.AccessMode.READ_WRITE, name = "finishedStudies", description = "종료된 참여 스터디 목록")
        private List<FinishedStudyInfoLight> finishedStudies;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    @Schema(name = "StudyDetailsResponse", description = "스터디 상세 정보 반환 모델")
    public static class StudyDetailsResponse{
        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "id", description = "스터디 id")
        private Long id;

        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "name", description = "스터디 이름")
        private String name;

        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "description", description = "스터디 설명")
        private String description;

        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "startedAt", description = "시작 일자 ex: 2000-10-31T01:30:00.000-05:00")
        private LocalDateTime startedAt;

        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "endedAt", description = "종료 일자 ex: 2000-10-31T01:30:00.000-05:00")
        private LocalDateTime endedAt;

        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "users", description = "참여중인 사용자 목록")
        private List<UserSimpleDTO> users;

        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "tags", description = "스터디 태그 목록")
        private List<TagDTO> tags;

        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "latestQuizRound", description = "최신 스터디 회차")
        private Integer latestQuizRound;

        public static StudyDetailsResponse toResponse(Study study, List<UserSimpleDTO> users, List<TagDTO> tags) {
            return StudyDetailsResponse.builder()
                    .id(study.getId())
                    .name(study.getName())
                    .description(study.getDescription())
                    .startedAt(study.getStartedAt())
                    .endedAt(study.getEndedAt())
                    .users(users)
                    .tags(tags)
                    .latestQuizRound(study.getLatestQuizRound())
                    .build();
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    @Schema(name = "StudyEntranceDetailsResponse", description = "참여 스터디 정보 반환 모델")
    public static class StudyEntranceDetailsResponse{
        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "id", description = "스터디 id")
        private Long id;

        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "name", description = "스터디 이름")
        private String name;

        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "description", description = "스터디 설명")
        private String description;

        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "startedAt", description = "시작 일자 ex: 2000-10-31T01:30:00.000-05:00")
        private LocalDateTime startedAt;

        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "endedAt", description = "종료 일자 ex: 2000-10-31T01:30:00.000-05:00")
        private LocalDateTime endedAt;

        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "capacity", description = "SMALL || MEDIUM || LARGE")
        private StudyCapacity capacity;

        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "tags", description = "스터디 태그 목록")
        private List<TagDTO> tags;

        public static StudyEntranceDetailsResponse toResponse(Study study, List<TagDTO> tags) {
            return StudyEntranceDetailsResponse.builder()
                    .id(study.getId())
                    .name(study.getName())
                    .description(study.getDescription())
                    .capacity(study.getCapacity())
                    .startedAt(study.getStartedAt())
                    .endedAt(study.getEndedAt())
                    .tags(tags)
                    .build();
        }
    }
}
