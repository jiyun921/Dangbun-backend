package com.dangbun.domain.place.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.util.Map;

public record PostCreatePlaceRequest(
        @Schema(description = "플레이스 이름", example = "메가박스")
        @NotBlank
        String placeName,

        @Schema(description = "카테고리", example = "카페")
        @NotBlank
        String category,

        @Schema(description = "매니저 이름", example = "홍길동")
        @NotBlank
        String managerName,

        @Schema(description = "정보", example = "{\"이메일\": \"test@test.com\" , \"전화번호\": \"01012345678\"}")
        Map<String, String> information
) {
}
