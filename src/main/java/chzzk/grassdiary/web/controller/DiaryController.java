package chzzk.grassdiary.web.controller;

import chzzk.grassdiary.auth.common.AuthenticatedMember;
import chzzk.grassdiary.auth.service.dto.AuthMemberPayload;
import chzzk.grassdiary.service.diary.DiaryService;
import chzzk.grassdiary.service.diary.TodayQuestionService;
import chzzk.grassdiary.web.dto.diary.DiaryDTO;
import chzzk.grassdiary.web.dto.diary.DiaryResponseDTO;
import chzzk.grassdiary.web.dto.diary.DiarySaveRequestDTO;
import chzzk.grassdiary.web.dto.diary.DiaryUpdateRequestDTO;
import chzzk.grassdiary.web.dto.main.TodayInfoDTO;
import chzzk.grassdiary.web.exceptions.AlreadyLikedException;
import chzzk.grassdiary.web.exceptions.DiaryNotFoundException;
import chzzk.grassdiary.web.exceptions.ErrorObject;
import chzzk.grassdiary.web.exceptions.MemberNotFoundException;
import chzzk.grassdiary.web.exceptions.NotLikedException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/diary")
@Tag(name = "일기 컨트롤러")
public class DiaryController {
    private final DiaryService diaryService;
    private final TodayQuestionService todayQuestionService;

    @PostMapping("/{memberId}")
    public Long save(@PathVariable(name = "memberId") Long memberId, @RequestBody DiarySaveRequestDTO requestDto) {
        return diaryService.save(memberId, requestDto);
    }

    @PatchMapping("/{diaryId}")
    public Long update(@PathVariable(name = "diaryId") Long diaryId, @RequestBody DiaryUpdateRequestDTO requestDto) {
        return diaryService.update(diaryId, requestDto);
    }

    @DeleteMapping("/{diaryId}")
    public Long delete(@PathVariable(name = "diaryId") Long diaryId) {
        diaryService.delete(diaryId);
        return diaryId;
    }

    @GetMapping("/{diaryId}")
    public DiaryResponseDTO findById(@PathVariable(name = "diaryId") Long diaryId,
                                     @AuthenticatedMember AuthMemberPayload payload) {
        return diaryService.findById(diaryId, payload.id());
    }

    /**
     * 유저별 일기장 메인 페이지 1) 디폴트 API(최신순 5개씩) : api/diary/main/1?page=0 2) 오래된 순 5개씩 :
     * api/diary/main/1?page=0&sort=createdAt,ASC
     */
    @GetMapping("/main/{memberId}")
    @Operation(
            summary = "유저별 일기장 메인 페이지",
            description =
                    "최신순 5개씩(`api/diary/main/{memberId}?page=0`), 오래된 순 5개씩(`api/diary/main/1?page=0&sort=createdAt,ASC`) "
                            + "\n페이지를 나타내는 변수가 있는데, 해당 표에는 바로 나타나지 않으니 실행 시켜보시는 것을 추천드립니다.")
    @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = DiaryDTO.class)))
    public ResponseEntity<?> findAll(
            @PageableDefault(size = 5, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @PathVariable Long memberId
    ) {
        return ResponseEntity.ok(diaryService.findAll(pageable, memberId));
    }

    // todo: 로그인 공부 후 memberId 부분을 auth로? 대체할 수 있는지 알아보기
    @PostMapping("/like/{diaryId}/{memberId}")
    public Long addLike(@PathVariable("diaryId") Long diaryId, @PathVariable("memberId") Long memberId) {
        return diaryService.addLike(diaryId, memberId);
    }

    @DeleteMapping("/like/{diaryId}/{memberId}")
    public Long deleteLike(@PathVariable("diaryId") Long diaryId, @PathVariable("memberId") Long memberId) {
        return diaryService.deleteLike(diaryId, memberId);
    }
  
    @GetMapping("/todayQuestion")
    @Operation(summary = "오늘 정보 및 오늘의 질문")
    @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = TodayInfoDTO.class)))
    public ResponseEntity<?> findTodayQuestion() {
        return ResponseEntity.ok(todayQuestionService.getTodayQuestion());
    }
  
    @ExceptionHandler
    public ResponseEntity<ErrorObject> handleException(MemberNotFoundException ex) {
        ErrorObject errorObject = new ErrorObject(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(errorObject, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorObject> handleException(DiaryNotFoundException ex) {
        ErrorObject errorObject = new ErrorObject(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(errorObject, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorObject> handleException(AlreadyLikedException ex) {
        ErrorObject errorObject = new ErrorObject(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(errorObject, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorObject> handleException(NotLikedException ex) {
        ErrorObject errorObject = new ErrorObject(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(errorObject, HttpStatus.NOT_FOUND);
    }
  
}
