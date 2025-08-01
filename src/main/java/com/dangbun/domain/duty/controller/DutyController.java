package com.dangbun.domain.duty.controller;

import com.dangbun.domain.duty.dto.request.*;
import com.dangbun.domain.duty.dto.response.*;
import com.dangbun.domain.duty.response.status.DutyExceptionResponse;
import com.dangbun.domain.duty.service.DutyService;
import com.dangbun.global.docs.DocumentedApiErrors;
import com.dangbun.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@Validated
@Tag(name = "Duty", description = "DutyController - 당번 관련 API")
@RestController
@RequiredArgsConstructor
public class DutyController {

    private final DutyService dutyService;

    @Operation(summary = "당번 생성", description = "플레이스에 새로운 당번을 생성합니다.")
    @PostMapping("/places/{placeId}/duties")
    @DocumentedApiErrors(
            value = {DutyExceptionResponse.class},
            includes = {"PLACE_NOT_FOUND", "DUTY_ALREADY_EXISTS"}
    )
    public ResponseEntity<BaseResponse<PostDutyCreateResponse>> createDuty(
            @PathVariable Long placeId,
            @RequestBody @Valid PostDutyCreateRequest request
    ) {
        return ResponseEntity.ok(BaseResponse.ok(dutyService.createDuty(placeId, request)));
    }

    @Operation(summary = "당번 목록 조회", description = "해당 플레이스의 당번 목록을 조회합니다.")
    @GetMapping("/places/{placeId}/duties")
    @DocumentedApiErrors(
            value = {DutyExceptionResponse.class},
            includes = {"PLACE_NOT_FOUND"}
    )
    public ResponseEntity<BaseResponse<List<GetDutyListResponse>>> getDutyList(@PathVariable Long placeId) {
        return ResponseEntity.ok(BaseResponse.ok(dutyService.getDutyList(placeId)));
    }

    @Operation(summary = "당번 수정", description = "해당 당번의 이름이나 아이콘을 수정합니다.")
    @PutMapping("/duties/{dutyId}")
    @DocumentedApiErrors(
            value = {DutyExceptionResponse.class},
            includes = {"DUTY_NOT_FOUND"}
    )
    public ResponseEntity<BaseResponse<PutDutyUpdateResponse>> updateDuty(
            @PathVariable Long dutyId,
            @RequestBody @Valid PutDutyUpdateRequest request
    ) {
        return ResponseEntity.ok(BaseResponse.ok(dutyService.updateDuty(dutyId, request)));
    }

    @Operation(summary = "당번 삭제", description = "해당 당번을 삭제합니다.")
    @DeleteMapping("/duties/{dutyId}")
    @DocumentedApiErrors(
            value = {DutyExceptionResponse.class},
            includes = {"DUTY_NOT_FOUND"}
    )
    public ResponseEntity<BaseResponse<Void>> deleteDuty(@PathVariable Long dutyId) {
        dutyService.deleteDuty(dutyId);
        return ResponseEntity.ok(BaseResponse.ok(null));
    }

    @Operation(summary = "당번 정보 - 멤버 이름 목록 조회", description = "당번의 멤버 이름 목록을 조회합니다.")
    @GetMapping("/duties/{dutyId}/member-names")
    @DocumentedApiErrors(
            value = {DutyExceptionResponse.class},
            includes = {"DUTY_NOT_FOUND"}
    )
    public ResponseEntity<BaseResponse<List<GetDutyMemberNameListResponse>>> getDutyMemberNameList(@PathVariable Long dutyId) {
        return ResponseEntity.ok(BaseResponse.ok(dutyService.getDutyMemberNameList(dutyId)));
    }

    @Operation(summary = "당번 정보 - 청소 이름 목록 조회", description = "당번의 청소 이름 목록을 조회합니다.")
    @GetMapping("/duties/{dutyId}/cleaning-names")
    @DocumentedApiErrors(
            value = {DutyExceptionResponse.class},
            includes = {"DUTY_NOT_FOUND"}
    )
    public ResponseEntity<BaseResponse<List<GetDutyCleaningNameListResponse>>> getDutyCleaningNameList(@PathVariable Long dutyId) {
        return ResponseEntity.ok(BaseResponse.ok(dutyService.getDutyCleaningNameList(dutyId)));
    }

    @Operation(summary = "당번 정보 - 멤버 추가", description = "당번에 멤버를 추가합니다.")
    @PostMapping("/duties/{dutyId}/members")
    @DocumentedApiErrors(
            value = {DutyExceptionResponse.class},
            includes = {"DUTY_NOT_FOUND", "MEMBER_NOT_FOUND"}
    )
    public ResponseEntity<BaseResponse<PostAddMembersResponse>> addMembers(
            @PathVariable Long dutyId,
            @RequestBody PostAddMembersRequest request
    ) {
        return ResponseEntity.ok(BaseResponse.ok(dutyService.addMembers(dutyId, request)));
    }

    @Operation(summary = "당번 역할 분담 (공통/랜덤/직접)", description = "해당 당번에 해당하는 청소에 멤버를 지정합니다.")
    @PatchMapping("/duties/{dutyId}/cleanings/members")
    @DocumentedApiErrors(
            value = {DutyExceptionResponse.class},
            includes = {"DUTY_NOT_FOUND", "CLEANING_NOT_FOUND", "MEMBER_NOT_EXISTS"}
    )
    public ResponseEntity<BaseResponse<Void>> assignMember(
            @PathVariable Long dutyId,
            @RequestBody @Valid PatchAssignMemberRequest request
    ) {
        dutyService.assignMember(dutyId, request);
        return ResponseEntity.ok(BaseResponse.ok(null));
    }

    @Operation(summary = "당번 역할 분담 - 청소 목록 조회 (청소 상세 정보 포함)", description = "해당 당번에 해당하는 청소 목록(청소 상세 정보 포함)을 조회합니다.")
    @DocumentedApiErrors(
            value = {DutyExceptionResponse.class},
            includes = {"DUTY_NOT_FOUND"}
    )
    @GetMapping("/duties/{dutyId}/cleaning-info")
    public ResponseEntity<BaseResponse<List<GetCleaningInfoListResponse>>> getCleaningInfoList(
            @PathVariable Long dutyId) {
        return ResponseEntity.ok(BaseResponse.ok(dutyService.getCleaningInfoList(dutyId)));
    }


    @Operation(summary = "당번 정보 - 미지정 청소 추가", description = "당번에 미지정 청소를 추가합니다.")
    @PostMapping("/duties/{dutyId}/cleanings")
    @DocumentedApiErrors(
            value = {DutyExceptionResponse.class},
            includes = {"DUTY_NOT_FOUND"}
    )
    public ResponseEntity<BaseResponse<PostAddCleaningsResponse>> addCleanings(
            @PathVariable Long dutyId,
            @RequestBody PostAddCleaningsRequest request
    ) {
        return ResponseEntity.ok(BaseResponse.ok(dutyService.addCleanings(dutyId, request)));
    }

    @Operation(summary = "당번에서 청소 항목 제거", description = "지정된 당번에서 특정 청소 항목을 제거하면 해당 청소는 미지정 상태로 되돌아갑니다.")
    @DeleteMapping("/duties/{dutyId}/cleanings/{cleaningId}")
    @DocumentedApiErrors(
            value = DutyExceptionResponse.class,
            includes = {"DUTY_NOT_FOUND", "CLEANING_NOT_FOUND", "CLEANING_NOT_ASSIGNED"}
    )
    public ResponseEntity<BaseResponse<Void>> removeCleaning(
            @PathVariable Long dutyId,
            @PathVariable Long cleaningId) {
        dutyService.removeCleaningFromDuty(dutyId, cleaningId);
        return ResponseEntity.ok(BaseResponse.ok(null));
    }

}
