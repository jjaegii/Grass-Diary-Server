package chzzk.grassdiary.web.dto.diary;

import chzzk.grassdiary.domain.diary.Diary;
import chzzk.grassdiary.domain.diary.comment.Comment;
import chzzk.grassdiary.domain.member.Member;

public record CommentSaveRequestDTO(
        Long memberId,
        Long diaryId,
        String content,
        Long parentCommentId
) {
    public Comment toEntity(Member member, Diary diary, Comment parentComment) {
        return Comment.builder()
                .member(member)
                .diary(diary)
                .content(content)
                .parentComment(parentComment)
                .build();
    }
}

