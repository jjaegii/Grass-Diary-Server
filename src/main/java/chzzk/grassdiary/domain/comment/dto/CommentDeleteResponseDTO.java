package chzzk.grassdiary.domain.comment.dto;

import chzzk.grassdiary.domain.comment.entity.Comment;

public record CommentDeleteResponseDTO(
        boolean deleted
) {
    public static CommentDeleteResponseDTO from(Comment comment) {
        return new CommentDeleteResponseDTO(
                comment.isDeleted()
        );
    }
}
