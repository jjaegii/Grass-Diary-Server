package chzzk.grassdiary.domain.diary.service;

import chzzk.grassdiary.domain.color.entity.ColorCode;
import chzzk.grassdiary.domain.color.entity.ColorCodeDAO;
import chzzk.grassdiary.domain.color.ConditionLevel;
import chzzk.grassdiary.domain.diary.dto.browse.DiaryPreviewDTO;
import chzzk.grassdiary.domain.diary.entity.Diary;
import chzzk.grassdiary.domain.diary.entity.DiaryDAO;
import chzzk.grassdiary.domain.diary.entity.DiaryLike;
import chzzk.grassdiary.domain.diary.entity.DiaryLikeDAO;
import chzzk.grassdiary.domain.member.entity.Member;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.BuilderArbitraryIntrospector;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import net.jqwik.api.Arbitraries;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
@Transactional
class BrowseDiaryServiceTest {

    @InjectMocks
    private BrowseDiaryService browseDiaryService;

    @Mock
    private DiaryDAO diaryDAO;

    @Mock
    private DiaryLikeDAO diaryLikeDAO;

    @Mock
    private DiaryService diaryService;

    @Mock
    private ColorCodeDAO colorCodeDAO;

    private FixtureMonkey fixtureMonkey;

    List<Diary> diaries;
    Member member;

    @BeforeEach
    void setUp() {
        fixtureMonkey = FixtureMonkey.builder()
                .objectIntrospector(BuilderArbitraryIntrospector.INSTANCE)
                .build();

        member = fixtureMonkey.giveMeBuilder(Member.class)
                .set("currentColorCode", new ColorCode())
                .sample();

        // 다이어리 여러 개 생성(좋아요가 여러 개 달린)
        diaries = fixtureMonkey.giveMeBuilder(Diary.class)
                .set("member", member)
                .set("conditionLevel", fixtureMonkey.giveMeOne(ConditionLevel.class))
                .sampleList(20);

        // 각 Diary 객체에 대해 랜덤한 DiaryLike 리스트를 설정
        diaries.forEach(diary -> {
            diary.setDiaryLikes(
                    fixtureMonkey.giveMeBuilder(DiaryLike.class)
                            .set("member", member)
                            .sampleList(Arbitraries.integers().between(1, 20).sample())
            );
            log.info(diary.getContent());
        });
    }

    @Test
    @DisplayName("이번주 Top10 일기들을 볼 수 있다. (좋아요, 최신 순 정렬)")
    void findTop10DiariesThisWeek() {
        int expectedSize = 10;
        // given
        // 좋아요 수와 생성 날짜로 정렬
        List<Diary> sortedDiaries = diaries.stream()
                .sorted(Comparator.comparing((Diary d) -> d.getDiaryLikes().size()).reversed()
                        .thenComparing(Diary::getCreatedAt))
                .limit(10)
                .collect(Collectors.toList());

        // Mock 설정
        when(diaryDAO.findTop10DiariesThisWeek(any(), any(), any()))
                .thenReturn(new PageImpl<>(sortedDiaries, PageRequest.of(0, 10), sortedDiaries.size()));

        when(diaryService.getImagesByDiary(any())).thenReturn(Collections.emptyList());

        // when
        List<DiaryPreviewDTO> result = browseDiaryService.findTop10DiariesThisWeek();

        // then
        assertNotNull(result);
        assertEquals(expectedSize, result.size());

        // 좋아요 개수 순서 확인
        for (int i = 0; i < expectedSize - 1; i++) {
            DiaryPreviewDTO current = result.get(i);
            DiaryPreviewDTO next = result.get(i + 1);
            assertTrue(current.diaryLikeCount() >= next.diaryLikeCount());
        }

        verify(diaryDAO).findTop10DiariesThisWeek(any(LocalDateTime.class), any(LocalDateTime.class), any(PageRequest.class));
        verify(diaryService, times(expectedSize)).getImagesByDiary(any());
    }

}