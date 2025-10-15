package com.puzzlix.solid_task.domain.issue;

import com.puzzlix.solid_task._global.dto.CommonResponseDto;
import com.puzzlix.solid_task.domain.issue.dto.IssueRequest;
import com.puzzlix.solid_task.domain.issue.dto.IssueResponse;
import com.puzzlix.solid_task.domain.user.Role;
import com.solapi.sdk.SolapiClient;
import com.solapi.sdk.message.exception.SolapiMessageNotReceivedException;
import com.solapi.sdk.message.model.Message;
import com.solapi.sdk.message.service.DefaultMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/issues")
public class IssueController {

    private final IssueService issueService;

    // ### 특정 이슈 상태 변경 (PATCH) - http 메세지 body 가질 수 있음
    //# @name updateIssueStatus
    //# URL의 {id} 부분에 수정할 이슈의 ID를, ?status= 뒤에 변경할 상태(TODO, IN_PROGRESS, DONE)를 입력합니다.
    //PATCH http://localhost:8080/api/issues/1/status?status=IN_PROGRESS
    //Authorization: Bearer {{loginToken}}
    @PatchMapping("/{issueId}/status")
    public ResponseEntity<?> updateStatus(@PathVariable("issueId") Long issueId,
                                          @RequestParam("type") IssueStatus status,
                                          @RequestAttribute("userEmail") String userEmail) {

        return ResponseEntity.ok().body(CommonResponseDto.success(issueService.updateIssueStatus(issueId, status, userEmail)));
    }

    /**
     * 이슈 삭제 API
     * DELETE /api/issues/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteIssue(
            @PathVariable(name = "id") Long id,
            @RequestAttribute("userEmail") String userEmail,
            @RequestAttribute("userRole")Role userRole) {

        issueService.deleteIssue(id, userEmail, userRole);

        return ResponseEntity.ok(CommonResponseDto
                .success(null, "이슈가 성공적으로 삭제 되었습니다"));
    }

    /**
     * 이슈 수정 API
     * PUT /api/issues/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<CommonResponseDto<IssueResponse.FindById>> updateIssue(
            @PathVariable(name = "id") Long id,
            @RequestBody IssueRequest.Update request,
            @RequestAttribute("userEmail") String userEmail) {

        Issue issue =  issueService.updateIssue(id, request, userEmail);
        IssueResponse.FindById findByIdDto = new IssueResponse.FindById(issue);
        return ResponseEntity
                .ok(CommonResponseDto.success(findByIdDto,
                        "이슈가 성공적으로 변경 되었습니다"));
    }



    /**
     * 이슈 생성 API
     * POST /api/issues
     */
    @PostMapping
    public ResponseEntity<CommonResponseDto<Issue>> createIssue(@RequestBody IssueRequest.Create request) {

        Issue createdIssue = issueService.createIssue(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponseDto.success(createdIssue));
    }

    /**
     * 이슈 목록 조회 API
     * GET /api/issues
     */
    @GetMapping
    public ResponseEntity<CommonResponseDto<List<IssueResponse.FindAll>>> getIssues() {
        // 서비스에서 조회 요청
        List<Issue> issues = issueService.findIssues();
        // 조회된 도메인 이슈 리스트를 DTO로 변환
        List<IssueResponse.FindAll> responseDtos = IssueResponse.FindAll.from(issues);
        return ResponseEntity.ok(CommonResponseDto.success(responseDtos));
    }


}


