package chzzk.grassdiary.domain.comment.service;

import chzzk.grassdiary.domain.comment.dto.CommentSaveRequestDTO;
import chzzk.grassdiary.domain.comment.entity.Comment;
import chzzk.grassdiary.domain.comment.entity.CommentDAO;
import chzzk.grassdiary.domain.diary.dto.DiarySaveRequestDTO;
import chzzk.grassdiary.domain.diary.entity.Diary;
import chzzk.grassdiary.domain.diary.entity.DiaryDAO;
import chzzk.grassdiary.domain.comment.dto.CommentSaveResponseDTO;
import chzzk.grassdiary.domain.member.entity.Member;
import chzzk.grassdiary.domain.member.entity.MemberDAO;
import chzzk.grassdiary.global.common.error.exception.SystemException;
import chzzk.grassdiary.global.common.response.ClientErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentDAO commentDAO;
    private final DiaryDAO diaryDAO;
    private final MemberDAO memberDAO;

    @Transactional
    public CommentSaveResponseDTO save(Long logInMemberId, Long diaryId, CommentSaveRequestDTO requestDTO) {

        Member member = getMemberById(logInMemberId);

        Diary diary = getDiaryById(diaryId);

        Comment parentComment = getParentCommentById(requestDTO.parentCommentId());

        Comment comment = requestDTO.toEntity(member, diary, parentComment);

        commentDAO.save(comment);

        return CommentSaveResponseDTO.from(member, comment);
    }

    private Member getMemberById(Long id) {
        return memberDAO.findById(id)
                .orElseThrow(() -> new SystemException(ClientErrorCode.MEMBER_NOT_FOUND_ERR));
    }

    private Diary getDiaryById(Long id) {
        return diaryDAO.findById(id)
                .orElseThrow(() -> new SystemException(ClientErrorCode.DIARY_NOT_FOUND_ERR));
    }

    private Comment getParentCommentById(Long id) {
        if (id == null) {
            return null;
        }
        return getCommentById(id);
    }

    private Comment getCommentById(Long id) {
        return commentDAO.findById(id)
                .orElseThrow(() -> new SystemException(ClientErrorCode.COMMENT_NOT_FOUND_ERR));
    }
}
