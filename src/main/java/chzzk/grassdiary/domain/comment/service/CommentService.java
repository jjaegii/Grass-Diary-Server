package chzzk.grassdiary.domain.comment.service;

import chzzk.grassdiary.domain.comment.dto.CommentDeleteRequestDTO;
import chzzk.grassdiary.domain.comment.dto.CommentDeleteResponseDTO;
import chzzk.grassdiary.domain.comment.dto.CommentSaveRequestDTO;
import chzzk.grassdiary.domain.comment.dto.CommentUpdateRequestDTO;
import chzzk.grassdiary.domain.comment.entity.Comment;
import chzzk.grassdiary.domain.comment.entity.CommentDAO;
import chzzk.grassdiary.domain.diary.entity.Diary;
import chzzk.grassdiary.domain.diary.entity.DiaryDAO;
import chzzk.grassdiary.domain.comment.dto.CommentResponseDTO;
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
    public CommentResponseDTO save(Long diaryId, CommentSaveRequestDTO requestDTO, Long logInMemberId) {
        Member member = getMemberById(logInMemberId);
        Diary diary = getDiaryById(diaryId);
        Comment parentComment = getParentCommentById(requestDTO.parentCommentId());
        Comment comment = requestDTO.toEntity(member, diary, parentComment);
        commentDAO.save(comment);

        return CommentResponseDTO.from(comment);
    }

    @Transactional
    public CommentResponseDTO update(Long CommentId, CommentUpdateRequestDTO requestDTO, Long logInMemberId) {
        Member member = getMemberById(logInMemberId);
        Comment comment = getCommentById(CommentId);
        validateCommentAuthor(member, comment);
        comment.update(requestDTO.content());

        return CommentResponseDTO.from(comment);
    }

    @Transactional
    public CommentDeleteResponseDTO delete(Long CommentId, CommentDeleteRequestDTO requestDTO, Long logInMemberId) {
        Member member = getMemberById(logInMemberId);
        Comment comment = getCommentById(CommentId);
        validateCommentAuthor(member, comment);
        comment.delete(requestDTO.deleted());

        return CommentDeleteResponseDTO.from(comment);
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

    private void validateCommentAuthor(Member member, Comment comment) {
        if (!member.equals(comment.getMember())) {
            throw new SystemException(ClientErrorCode.AUTHOR_MISMATCH_ERR);
        }
    }
}
