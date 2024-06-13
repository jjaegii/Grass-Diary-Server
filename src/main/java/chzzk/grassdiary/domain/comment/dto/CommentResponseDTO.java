package chzzk.grassdiary.domain.comment.dto;

import chzzk.grassdiary.domain.comment.entity.Comment;
import java.time.format.DateTimeFormatter;

public record CommentResponseDTO(
        // 멤버 정보(사진, 아이디), 댓글내용, 작성 시간
        // todo: 사진정보 추가
        Long memberId,
        String content,
        boolean deleted,
        String createdDate,
        String createdAt
) {
    public static CommentResponseDTO from(Comment comment) {
        return new CommentResponseDTO(
                comment.getMember().getId(),
                comment.getContent(),
                comment.isDeleted(),
                comment.getCreatedAt().format(DateTimeFormatter.ofPattern("yy년 MM월 dd일")),
                comment.getCreatedAt().format(DateTimeFormatter.ofPattern("HH:mm"))
        );
    }

    public static CommentResponseDTO fromDeleted(Comment comment) {
        return new CommentResponseDTO(
                null,
                null,
                true,
                null,
                null
        );
    }
}
