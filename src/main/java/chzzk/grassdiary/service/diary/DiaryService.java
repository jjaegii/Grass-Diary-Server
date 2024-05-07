package chzzk.grassdiary.service.diary;

import chzzk.grassdiary.domain.color.ColorCode;
import chzzk.grassdiary.domain.diary.Diary;
import chzzk.grassdiary.domain.diary.DiaryImageRepository;
import chzzk.grassdiary.domain.diary.DiaryLike;
import chzzk.grassdiary.domain.diary.DiaryLikeRepository;
import chzzk.grassdiary.domain.diary.DiaryRepository;
import chzzk.grassdiary.domain.diary.tag.DiaryTag;
import chzzk.grassdiary.domain.diary.tag.DiaryTagRepository;
import chzzk.grassdiary.domain.diary.tag.MemberTags;
import chzzk.grassdiary.domain.diary.tag.MemberTagsRepository;
import chzzk.grassdiary.domain.diary.tag.TagList;
import chzzk.grassdiary.domain.diary.tag.TagListRepository;
import chzzk.grassdiary.domain.member.Member;
import chzzk.grassdiary.domain.member.MemberRepository;
import chzzk.grassdiary.domain.reward.RewardHistory;
import chzzk.grassdiary.domain.reward.RewardHistoryRepository;
import chzzk.grassdiary.domain.reward.RewardType;
import chzzk.grassdiary.web.dto.diary.CountAndMonthGrassDTO;
import chzzk.grassdiary.web.dto.diary.DiaryDTO;
import chzzk.grassdiary.web.dto.diary.DiaryResponseDTO;
import chzzk.grassdiary.web.dto.diary.DiarySaveRequestDTO;
import chzzk.grassdiary.web.dto.diary.DiaryUpdateRequestDTO;
import chzzk.grassdiary.web.dto.member.GrassInfoDTO;
import chzzk.grassdiary.web.exceptions.AlreadyLikedException;
import chzzk.grassdiary.web.exceptions.DiaryEditDateMismatchException;
import chzzk.grassdiary.web.exceptions.DiaryNotFoundException;
import chzzk.grassdiary.web.exceptions.MemberNotFoundException;
import chzzk.grassdiary.web.exceptions.NotLikedException;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final DiaryLikeRepository diaryLikeRepository;
    private final DiaryImageRepository diaryImageRepository;
    private final TagListRepository tagListRepository;
    private final MemberTagsRepository memberTagsRepository;
    private final MemberRepository memberRepository;
    private final DiaryTagRepository diaryTagRepository;
    private final RewardHistoryRepository rewardHistoryRepository;

    @Transactional
    public Long save(Long id, DiarySaveRequestDTO requestDto) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException("해당 사용자가 존재하지 않습니다. id = " + id));

        Diary diary = diaryRepository.save(requestDto.toEntity(member));

        if (requestDto.getHashtags() != null) {
            for (String hashtag : requestDto.getHashtags()) {
                TagList tagList = tagListRepository.findByTag(hashtag)
                        .orElseGet(() -> tagListRepository.save(new TagList(hashtag)));
                tagList.incrementCount();
                MemberTags memberTags = memberTagsRepository.findByMemberIdAndTagList(member.getId(), tagList)
                        .orElseGet(() -> memberTagsRepository.save(new MemberTags(member, tagList)));
                memberTags.incrementCount();
                diaryTagRepository.save(new DiaryTag(diary, memberTags));
            }
        }

        long seed = System.currentTimeMillis();
        Random random = new Random(seed);
        int rewardPoint = random.nextInt(10) + 1;

        member.addRandomPoint(rewardPoint);
        RewardHistory rewardHistory = rewardHistoryRepository
                .save(new RewardHistory(member, RewardType.PLUS_DIARY_WRITE, rewardPoint));

        if (rewardHistory.getId() == null) {
            throw new IllegalArgumentException("일기 히스토리 저장 오류");
        }
        return diary.getId();
    }

    @Transactional
    public Long update(Long id, DiaryUpdateRequestDTO requestDto) {
        Diary diary = diaryRepository.findById(id)
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
        List<DiaryTag> diaryTags = diaryTagRepository.findAllByDiaryId(diary.getId());
        List<MemberTags> memberTags = new ArrayList<>();
        List<TagList> tags = new ArrayList<>();
        for (DiaryTag diaryTag : diaryTags) {
            memberTags.add(diaryTag.getMemberTags());
            tags.add(diaryTag.getMemberTags().getTagList());
        }

        // 기존 태그 삭제 시작
        for (DiaryTag diaryTag : diaryTags) {
            diaryTagRepository.delete(diaryTag);
        }

        for (MemberTags memberTag : memberTags) {
            memberTag.decrementCount();
            if (memberTag.getMemberTagUsageCount() == 0) {
                memberTagsRepository.delete(memberTag);
            }
        }

        for (TagList tag : tags) {
            tag.decrementCount();
            if (tag.getTagUsageCount() == 0) {
                tagListRepository.delete(tag);
            }
        }

        // 새로운 태그 save
        if (requestDto.getHashtags() != null) {
            for (String hashtag : requestDto.getHashtags()) {
                TagList newTagList = tagListRepository.findByTag(hashtag)
                        .orElseGet(() -> tagListRepository.save(new TagList(hashtag)));
                newTagList.incrementCount();
                MemberTags newMemberTags = memberTagsRepository.findByMemberIdAndTagList(diary.getMember().getId(),
                                newTagList)
                        .orElseGet(() -> memberTagsRepository.save(new MemberTags(diary.getMember(), newTagList)));
                newMemberTags.incrementCount();
                diaryTagRepository.save(new DiaryTag(diary, newMemberTags));
            }
        }

        // diary update 적용
        diary.update(requestDto.getContent(), requestDto.getIsPrivate(), requestDto.getHasImage(),
                requestDto.getHasTag(), requestDto.getConditionLevel());

        return id;
    }

    @Transactional
    public void delete(Long diaryId) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new DiaryNotFoundException("해당 일기가 존재하지 않습니다. diaryId = " + diaryId));

        // diaryId를 이용해서 diaryTag를 모두 찾아내기
        List<DiaryTag> diaryTags = diaryTagRepository.findAllByDiaryId(diary.getId());

        // diaryTag를 이용해서 MemberTag를 모두 찾아내기
        List<MemberTags> memberTags = new ArrayList<>();
        List<TagList> tags = new ArrayList<>();
        for (DiaryTag diaryTag : diaryTags) {
            memberTags.add(diaryTag.getMemberTags());
            tags.add(diaryTag.getMemberTags().getTagList());
        }

        // diaryTag 삭제 -> deleteAllInBatch 고려해보기
        for (DiaryTag diaryTag : diaryTags) {
            diaryTagRepository.delete(diaryTag);
        }

        // MemberTag 삭제
        for (MemberTags memberTag : memberTags) {
            memberTag.decrementCount();
            if (memberTag.getMemberTagUsageCount() == 0) {
                memberTagsRepository.delete(memberTag);
            }
        }

        for (TagList tag : tags) {
            tag.decrementCount();
            if (tag.getTagUsageCount() == 0) {
                tagListRepository.delete(tag);
            }
        }

        // 해당 일기의 좋아요 찾기
        List<DiaryLike> diaryLikes = diaryLikeRepository.findAllByDiaryId(diaryId);

        // 좋아요 삭제
        for (DiaryLike diaryLike : diaryLikes) {
            diaryLikeRepository.delete(diaryLike);
        }

        diaryRepository.delete(diary);
    }

    @Transactional(readOnly = true)
    public DiaryResponseDTO findById(Long diaryId, Long logInMemberId) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new DiaryNotFoundException("해당 일기가 존재하지 않습니다. diaryId = " + diaryId));
        //조회한 결과를 담은 DTO 객체를 생성해서 반환
        List<DiaryTag> diaryTags = diaryTagRepository.findAllByDiaryId(diary.getId());
        List<TagList> tags = new ArrayList<>();
        for (DiaryTag diaryTag : diaryTags) {
            tags.add(diaryTag.getMemberTags().getTagList());
        }

        boolean isLiked = diaryLikeRepository.findByDiaryIdAndMemberId(diaryId, logInMemberId).isPresent();

        return new DiaryResponseDTO(diary, tags, isLiked);
    }

    @Transactional(readOnly = true)
    public Page<DiaryDTO> findAll(Pageable pageable, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 멤버 입니다. (id: " + memberId + ")"));

        return diaryRepository.findDiaryByMemberId(memberId, pageable)
                .map(diary -> {
                    List<MemberTags> diaryTags = diaryTagRepository.findMemberTagsByDiaryId(diary.getId());
                    List<TagList> tags = diaryTags.stream()
                            .map(MemberTags::getTagList)
                            .toList();
                    return DiaryDTO.from(diary, tags);
                });
    }

    @Transactional(readOnly = true)
    public DiaryDTO findByDate(Long id, String date) {

        LocalDate localDate = LocalDate.parse(date);

        LocalDateTime startOfDay = localDate.atStartOfDay();
        LocalDateTime endOfDay = localDate.atTime(LocalTime.MAX);

        List<Diary> diaryList = diaryRepository.findByMemberIdAndCreatedAtBetween(id, startOfDay, endOfDay);
        if (diaryList.size() != 1) {
            // TODO: 일기를 찾지 못한 경우 보내는 값 설정 해야함
            return null;
        }

        Diary diary = diaryList.get(0);
        System.out.println(diary.getContent());

        // 해시태그 리스트 가져오기
        List<MemberTags> diaryTags = diaryTagRepository.findMemberTagsByDiaryId(diary.getId());
        List<TagList> tags = diaryTags.stream()
                .map(MemberTags::getTagList)
                .toList();

        return new DiaryDTO(
                diary.getId(),
                diary.getContent(),
                tags,
                diary.getConditionLevel().getTransparency(),
                diary.getIsPrivate(),
                diary.getDiaryLikes().size(),
                diary.getCreatedAt().format(DateTimeFormatter.ofPattern("yy년 MM월 dd일")),
                diary.getCreatedAt().format(DateTimeFormatter.ofPattern("HH:mm")));
    }

    /**
     * 유저의 총 잔디 개수(count) 유저의 이번 달 잔디 정보(grassInfoDTO<grassList>, colorRGB>)
     */
    @Transactional(readOnly = true)
    public CountAndMonthGrassDTO countAllAndMonthGrass(Long memberId) {
        List<Diary> allByMemberId = diaryRepository.findAllByMemberId(memberId);

        LocalDate startOfMonth = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1);
        LocalDate today = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(),
                LocalDate.now().getDayOfMonth());
        LocalDateTime startOfDay = startOfMonth.atStartOfDay();
        LocalDateTime endOfToday = today.atTime(LocalTime.MAX);

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 멤버 입니다. (id: " + memberId + ")"));

        List<Diary> thisMonthHistory = diaryRepository.findByMemberIdAndCreatedAtBetween(memberId, startOfDay,
                endOfToday);
        ColorCode colorCode = member.getCurrentColorCode();

        return new CountAndMonthGrassDTO(
                allByMemberId.size(),
                new GrassInfoDTO(thisMonthHistory, colorCode.getRgb()),
                thisMonthHistory.size()
        );
    }

    @Transactional
    public Long addLike(Long diaryId, Long memberId) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new DiaryNotFoundException("해당 일기가 존재하지 않습니다. diaryId = " + diaryId));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("해당 멤버가 존재하지 않습니다. memberId = " + memberId));

        diaryLikeRepository.findByDiaryIdAndMemberId(diaryId, memberId)
                .ifPresent(diaryLike -> {
                    throw new AlreadyLikedException("좋아요를 이미 눌렀습니다.");
                });

        DiaryLike diaryLike = DiaryLike.builder()
                .diary(diary)
                .member(member)
                .build();

        diaryLikeRepository.save(diaryLike);

        // 추후 DTO로 return값 변경
        return diaryId;
    }

    @Transactional
    public Long deleteLike(Long diaryId, Long memberId) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new DiaryNotFoundException("해당 일기가 존재하지 않습니다. diaryId = " + diaryId));

        DiaryLike diaryLike = diaryLikeRepository.findByDiaryIdAndMemberId(diaryId, memberId)
                .orElseThrow(() -> new NotLikedException("해당 게시글에 좋아요를 누르지 않았습니다."));

        diaryLikeRepository.delete(diaryLike);

        // 추후 DTO로 return값 변경
        return diaryId;
    }
}
