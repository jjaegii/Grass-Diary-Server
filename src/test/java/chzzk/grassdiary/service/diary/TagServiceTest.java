//package chzzk.grassdiary.service.diary;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import chzzk.grassdiary.domain.member.entity.color.ConditionLevel;
//import chzzk.grassdiary.domain.diary.entity.Diary;
//import chzzk.grassdiary.domain.diary.entity.dao.DiaryRepository;
//import chzzk.grassdiary.domain.diary.tag.MemberTags;
//import chzzk.grassdiary.domain.diary.tag.MemberTagsRepository;
//import chzzk.grassdiary.domain.diary.tag.TagList;
//import chzzk.grassdiary.domain.diary.tag.TagListRepository;
//import chzzk.grassdiary.domain.member.entity.Member;
//import chzzk.grassdiary.domain.member.entity.MemberRepository;
//import chzzk.grassdiary.domain.diary.dto.TagDTO;
//import java.util.ArrayList;
//import java.util.List;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
//class TagServiceTest {
//
//    protected List<Member> members;
//    protected List<Diary> diaries;
//    protected List<MemberTags> memberTags;
//    protected List<TagList> tagLists;
//    @Autowired
//    MemberRepository memberRepository;
//    @Autowired
//    DiaryRepository diaryRepository;
//    @Autowired
//    MemberTagsRepository memberTagsRepository;
//    @Autowired
//    TagListRepository tagListRepository;
//    @Autowired
//    TagService tagService;
//
//    @BeforeEach
//    void beforeEach() {
//        // 멤버 3명 만들기
//        members = new ArrayList<>();
//        for (int i = 0; i < 3; i++) {
//            members.add(Member.builder()
//                    .nickname("testMember" + i)
//                    .email("member" + i + "@test.com")
//                    .build());
//        }
//        members = memberRepository.saveAll(members);
//
//        // 15개 일기
//        diaries = new ArrayList<>();
//        for (int i = 0; i < 15; i++) {
//            diaries.add(Diary.builder()
//                    .member(members.get(i % 3))
//                    .content("testContent" + i)
//                    .hasTag(true)
//                    .conditionLevel(ConditionLevel.LEVEL_5)
//                    .build());
//        }
//        diaries = diaryRepository.saveAll(diaries);
//
//        // 태그 리스트 생성
//        tagLists = new ArrayList<>();
//        tagLists.add(TagList.builder().tag("tag1").build());
//        tagLists.add(TagList.builder().tag("tag2").build());
//        tagLists.add(TagList.builder().tag("tag3").build());
//        tagLists = tagListRepository.saveAll(tagLists);
//
//        // 멤버 태그 생성
//        memberTags = new ArrayList<>();
//        for (int i = 0; i < 3; i++) {
//            memberTags.add(new MemberTags(members.get(i), tagLists.get(0)));
//            memberTags.add(new MemberTags(members.get(i), tagLists.get(1)));
//        }
//        memberTags.add(new MemberTags(members.get(2), tagLists.get(2)));
//        memberTags = memberTagsRepository.saveAll(memberTags);
//    }
//
//    @Test
//    @DisplayName("멤버별 해시태그 검색")
//    void searchHashTags() {
//        // Given
//        Member member = members.get(2); // 1번 멤버 선택
//        Long memberId = member.getId();
//
//        // When
//        List<TagDTO> dto = tagService.getMemberTags(memberId);
//
//        // Then
//        System.out.println("SIZE: " + dto.size());
//        for (int i = 0; i < dto.size(); i++) {
//            System.out.println("tagId: " + dto.get(i).tagId());
//            System.out.println("tag: " + dto.get(i).tag());
//        }
//        assertThat(dto.size()).isEqualTo(3);
//    }
//
//}