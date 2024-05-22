package chzzk.grassdiary.domain.diary.service;

import chzzk.grassdiary.domain.image.service.DiaryImageService;
import chzzk.grassdiary.domain.diary.dto.*;
import chzzk.grassdiary.domain.diary.entity.Diary;
import chzzk.grassdiary.domain.diary.entity.DiaryLike;
import chzzk.grassdiary.domain.diary.entity.DiaryLikeDAO;
import chzzk.grassdiary.domain.diary.entity.DiaryDAO;
import chzzk.grassdiary.domain.diary.entity.tag.DiaryTag;
import chzzk.grassdiary.domain.diary.entity.tag.DiaryTagDAO;
import chzzk.grassdiary.domain.diary.entity.tag.MemberTags;
import chzzk.grassdiary.domain.diary.entity.tag.MemberTagsDAO;
import chzzk.grassdiary.domain.diary.entity.tag.TagList;
import chzzk.grassdiary.domain.diary.entity.tag.TagListDAO;
import chzzk.grassdiary.domain.member.entity.Member;
import chzzk.grassdiary.domain.member.entity.MemberDAO;
import chzzk.grassdiary.domain.reward.RewardHistory;
import chzzk.grassdiary.domain.reward.RewardHistoryDAO;
import chzzk.grassdiary.domain.reward.RewardType;
import chzzk.grassdiary.global.util.file.FileFolder;
import chzzk.grassdiary.global.common.error.exception.AlreadyLikedException;
import chzzk.grassdiary.global.common.error.exception.DiaryEditDateMismatchException;
import chzzk.grassdiary.global.common.error.exception.DiaryNotFoundException;
import chzzk.grassdiary.global.common.error.exception.MemberNotFoundException;
import chzzk.grassdiary.global.common.error.exception.NotLikedException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryDAO diaryDAO;
    private final DiaryLikeDAO diaryLikeDAO;
    private final TagListDAO tagListDAO;
    private final MemberTagsDAO memberTagsDAO;
    private final MemberDAO memberDAO;
    private final DiaryTagDAO diaryTagDAO;
    private final RewardHistoryDAO rewardHistoryDAO;

    private final DiaryImageService diaryImageService;

    @Transactional
    public DiarySaveResponseDTO save(Long id, DiarySaveRequestDTO requestDto, MultipartFile image) {
        // TODO: 기능별 메서드 분리
        Member member = memberDAO.findById(id)
                .orElseThrow(() -> new MemberNotFoundException("해당 사용자가 존재하지 않습니다. id = " + id));

        if (requestDto.getHashtags() != null) {
            for (String hashtag : requestDto.getHashtags()) {
                TagList tagList = tagListDAO.findByTag(hashtag)
                        .orElseGet(() -> tagListDAO.save(new TagList(hashtag)));
                tagList.incrementCount();
                MemberTags memberTags = memberTagsDAO.findByMemberIdAndTagList(member.getId(), tagList)
                        .orElseGet(() -> memberTagsDAO.save(new MemberTags(member, tagList)));
                memberTags.incrementCount();
                diaryTagDAO.save(new DiaryTag(diary, memberTags));
            }
        }

        long seed = System.currentTimeMillis();
        Random random = new Random(seed);
        int rewardPoint = random.nextInt(10) + 1;

        member.addRandomPoint(rewardPoint);
        RewardHistory rewardHistory = rewardHistoryDAO
                .save(new RewardHistory(member, RewardType.PLUS_DIARY_WRITE, rewardPoint));

        if (rewardHistory.getId() == null) {
            throw new IllegalArgumentException("일기 히스토리 저장 오류");
        }

        if (requestDto.getHasImage()) {
            String imagePath = diaryImageService.uploadDiaryImage(image, FileFolder.PERSONAL_DIARY, diary);
            return new DiarySaveResponseDTO(diary.getId(), true, imagePath);
        }
        return new DiarySaveResponseDTO(diary.getId(), false, "");
    }

    @Transactional
    public DiarySaveResponseDTO update(Long id, DiaryUpdateRequestDTO requestDto, MultipartFile image) {
        Diary diary = diaryDAO.findById(id)
                .orElseThrow(() -> new DiaryNotFoundException("해당 일기가 존재하지 않습니다. id = " + id));

        // update 가능 시간 체크
        LocalDate createdAt = diary.getCreatedAt().toLocalDate();

        LocalDate today = LocalDateTime.now().toLocalDate();
        if (!createdAt.equals(today)) {
            throw new DiaryEditDateMismatchException(
                    "일기를 수정 가능한 날짜가 아닙니다. createdAt = " + createdAt + ", today = " + today
            );
        }

        // 기존 diaryTag, memberTags, tagList 찾기
        List<DiaryTag> diaryTags = diaryTagDAO.findAllByDiaryId(diary.getId());
        List<MemberTags> memberTags = new ArrayList<>();
        List<TagList> tags = new ArrayList<>();
        for (DiaryTag diaryTag : diaryTags) {
            memberTags.add(diaryTag.getMemberTags());
            tags.add(diaryTag.getMemberTags().getTagList());
        }

        // 기존 태그 삭제 시작
        for (DiaryTag diaryTag : diaryTags) {
            diaryTagDAO.delete(diaryTag);
        }

        for (MemberTags memberTag : memberTags) {
            memberTag.decrementCount();
            if (memberTag.getMemberTagUsageCount() == 0) {
                memberTagsDAO.delete(memberTag);
            }
        }

        for (TagList tag : tags) {
            tag.decrementCount();
            if (tag.getTagUsageCount() == 0) {
                tagListDAO.delete(tag);
            }
        }

        // 새로운 태그 save
        if (requestDto.getHashtags() != null) {
            for (String hashtag : requestDto.getHashtags()) {
                TagList newTagList = tagListDAO.findByTag(hashtag)
                        .orElseGet(() -> tagListDAO.save(new TagList(hashtag)));
                newTagList.incrementCount();
                MemberTags newMemberTags = memberTagsDAO.findByMemberIdAndTagList(diary.getMember().getId(),
                                newTagList)
                        .orElseGet(() -> memberTagsDAO.save(new MemberTags(diary.getMember(), newTagList)));
                newMemberTags.incrementCount();
                diaryTagDAO.save(new DiaryTag(diary, newMemberTags));
            }
        }

        boolean originalHasImage = diary.getHasImage() != null && diary.getHasImage();
        boolean currentHasImage = requestDto.getHasImage();
        String imagePath = "";
        if (currentHasImage) {
            imagePath = diaryImageService.updateImage(
                    originalHasImage,
                    image,
                    FileFolder.PERSONAL_DIARY,
                    diary);
        }
        if (originalHasImage && !currentHasImage) {
            diaryImageService.deleteImage(diary);
        }

        // diary update 적용
        diary.update(requestDto.getContent(), requestDto.getIsPrivate(), requestDto.getHasImage(),
                requestDto.getHasTag(), requestDto.getConditionLevel());

        if (currentHasImage) {
            return new DiarySaveResponseDTO(diary.getId(), true, imagePath);
        }
        return new DiarySaveResponseDTO(diary.getId(), false, "");

    }

    @Transactional
    public void delete(Long diaryId) {
        Diary diary = diaryDAO.findById(diaryId)
                .orElseThrow(() -> new DiaryNotFoundException("해당 일기가 존재하지 않습니다. diaryId = " + diaryId));

        // diaryId를 이용해서 diaryTag를 모두 찾아내기
        List<DiaryTag> diaryTags = diaryTagDAO.findAllByDiaryId(diary.getId());

        // diaryTag를 이용해서 MemberTag를 모두 찾아내기
        List<MemberTags> memberTags = new ArrayList<>();
        List<TagList> tags = new ArrayList<>();
        for (DiaryTag diaryTag : diaryTags) {
            memberTags.add(diaryTag.getMemberTags());
            tags.add(diaryTag.getMemberTags().getTagList());
        }

        // diaryTag 삭제 -> deleteAllInBatch 고려해보기
        for (DiaryTag diaryTag : diaryTags) {
            diaryTagDAO.delete(diaryTag);
        }

        // MemberTag 삭제
        for (MemberTags memberTag : memberTags) {
            memberTag.decrementCount();
            if (memberTag.getMemberTagUsageCount() == 0) {
                memberTagsDAO.delete(memberTag);
            }
        }

        for (TagList tag : tags) {
            tag.decrementCount();
            if (tag.getTagUsageCount() == 0) {
                tagListDAO.delete(tag);
            }
        }

        // 해당 일기의 좋아요 찾기
        List<DiaryLike> diaryLikes = diaryLikeDAO.findAllByDiaryId(diaryId);

        // 좋아요 삭제
        for (DiaryLike diaryLike : diaryLikes) {
            diaryLikeDAO.delete(diaryLike);
        }

        // 이미지 삭제
        if (diary.getHasImage() != null && diary.getHasImage()) {
            diaryImageService.deleteImage(diary);
        }

        diaryDAO.delete(diary);
    }

    @Transactional(readOnly = true)
    public DiaryDTO findById(Long diaryId, Long logInMemberId) {
        Diary diary = diaryDAO.findById(diaryId)
                .orElseThrow(() -> new DiaryNotFoundException("해당 일기가 존재하지 않습니다. diaryId = " + diaryId));

        List<DiaryTag> diaryTags = diaryTagDAO.findAllByDiaryId(diary.getId());
        List<TagList> tags = new ArrayList<>();
        for (DiaryTag diaryTag : diaryTags) {
            tags.add(diaryTag.getMemberTags().getTagList());
        }

        boolean isLiked = diaryLikeDAO.findByDiaryIdAndMemberId(diaryId, logInMemberId).isPresent();

        return DiaryDTO.from(diary, tags, isLiked, getImageURL(diary.getHasImage(), diary.getId()));
    }

    @Transactional(readOnly = true)
    public Page<DiaryDTO> findAll(Pageable pageable, Long memberId, Long logInMemberId) {
        Member member = memberDAO.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 멤버 입니다. (id: " + memberId + ")"));

        return diaryDAO.findDiaryByMemberId(memberId, pageable)
                .map(diary -> {
                    List<MemberTags> diaryTags = diaryTagDAO.findMemberTagsByDiaryId(diary.getId());
                    List<TagList> tags = diaryTags.stream()
                            .map(MemberTags::getTagList)
                            .toList();
                    String imageURL = getImageURL(diary.getHasImage(), diary.getId());
                    boolean isLiked = diaryLikeDAO.findByDiaryIdAndMemberId(diary.getId(), logInMemberId).isPresent();
                    return DiaryDTO.from(diary, tags, isLiked, imageURL);
                });
    }

    @Transactional(readOnly = true)
    public DiaryDTO findByDate(Long id, String date, Long logInMemberId) {

        LocalDate localDate = LocalDate.parse(date);

        LocalDateTime startOfDay = localDate.atStartOfDay();
        LocalDateTime endOfDay = localDate.atTime(LocalTime.MAX);

        List<Diary> diaryList = diaryDAO.findByMemberIdAndCreatedAtBetween(id, startOfDay, endOfDay);
        if (diaryList.size() != 1) {
            // TODO: 일기를 찾지 못한 경우 보내는 값 설정 해야함
            return null;
        }

        Diary diary = diaryList.get(0);
        System.out.println(diary.getContent());

        // 해시태그 리스트 가져오기
        List<MemberTags> diaryTags = diaryTagDAO.findMemberTagsByDiaryId(diary.getId());
        List<TagList> tags = diaryTags.stream()
                .map(MemberTags::getTagList)
                .toList();

        boolean isLikedByLogInMember = diaryLikeDAO.findByDiaryIdAndMemberId(diary.getId(), logInMemberId).isPresent();

        return DiaryDTO.from(
                diary,
                tags,
                isLikedByLogInMember,
                getImageURL(diary.getHasImage(), diary.getId())
        );
    }

    @Transactional
    public Long addLike(Long diaryId, Long memberId) {
        Diary diary = diaryDAO.findById(diaryId)
                .orElseThrow(() -> new DiaryNotFoundException("해당 일기가 존재하지 않습니다. diaryId = " + diaryId));

        Member member = memberDAO.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("해당 멤버가 존재하지 않습니다. memberId = " + memberId));

        diaryLikeDAO.findByDiaryIdAndMemberId(diaryId, memberId)
                .ifPresent(diaryLike -> {
                    throw new AlreadyLikedException("좋아요를 이미 눌렀습니다.");
                });

        DiaryLike diaryLike = DiaryLike.builder()
                .diary(diary)
                .member(member)
                .build();

        diaryLikeDAO.save(diaryLike);

        // 추후 DTO로 return값 변경
        return diaryId;
    }

    @Transactional
    public Long deleteLike(Long diaryId, Long memberId) {
        Diary diary = diaryDAO.findById(diaryId)
                .orElseThrow(() -> new DiaryNotFoundException("해당 일기가 존재하지 않습니다. diaryId = " + diaryId));

        DiaryLike diaryLike = diaryLikeDAO.findByDiaryIdAndMemberId(diaryId, memberId)
                .orElseThrow(() -> new NotLikedException("해당 게시글에 좋아요를 누르지 않았습니다."));

        diaryLikeDAO.delete(diaryLike);

        // 추후 DTO로 return값 변경
        return diaryId;
    }

    private String getImageURL(Boolean hasImage, Long diaryId) {
        if (hasImage != null && hasImage) {
            return diaryImageService.getImageURL(diaryId);
        }
        return "";
    }
}
