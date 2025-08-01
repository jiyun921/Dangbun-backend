package com.dangbun.domain.place.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.util.Map;

public record PostRegisterPlaceRequest (
        @Schema(description = "참여코드", example = "123abc")
        @NotBlank
        String inviteCode,

        @Schema(description = "이름",example = "김철수")
        @NotBlank
        String name,

        @Schema(description = "정보", example = "{\"이메일\": \"test@test.com\" , \"전화번호\": \"01012345678\"}")
        Map<String, String> information
){}
