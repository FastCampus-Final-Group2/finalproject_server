package com.team2.finalproject.domain.dispatchnumber.model.dto.request;

import com.team2.finalproject.domain.dispatchnumber.model.type.DispatchNumberStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record DispatchNumberSearchRequest(
        @Schema(example = "WAITING", description = "배차 상태", requiredMode = Schema.RequiredMode.REQUIRED)
        DispatchNumberStatus status,

        @Schema(example = "true", description = "관리자 여부", requiredMode = Schema.RequiredMode.REQUIRED)
        Boolean isManager,

        @Schema(example = "2024-06-01T09:00:00", description = "검색 시작일", requiredMode = Schema.RequiredMode.REQUIRED)
        LocalDateTime startDateTime,

        @Schema(example = "2024-08-30T09:00:00", description = "검색 종료일", requiredMode = Schema.RequiredMode.REQUIRED)
        LocalDateTime endDateTime,

        @Schema(example = "driver", description = "검색 옵션")
        String searchOption,

        @Schema(example = "john", description = "검색 키워드")
        String searchKeyword
) {}