//package chzzk.grassdiary;
//
//import chzzk.grassdiary.domain.member.entity.color.ColorCode;
//import chzzk.grassdiary.domain.member.entity.color.ConditionLevel;
//import chzzk.grassdiary.domain.diary.entity.Diary;
//import chzzk.grassdiary.domain.diary.entity.DiaryLike;
//import chzzk.grassdiary.domain.member.entity.Member;
//import chzzk.grassdiary.domain.member.entity.MemberPurchasedColor;
//import jakarta.persistence.EntityManager;
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Commit;
//
//@SpringBootTest
//@Transactional
//@Commit
//public class DBTest {
//
//    @Autowired
//    private EntityManager em;
//
//    @Test
//    public void test1() {
//
//        Member member = new Member();
//        member.setEmail("xxx@gmail.com");
//        member.setGrassCount(10L);
//        em.persist(member);
//
//        Diary diary = new Diary();
//        diary.setContent("오늘 바쁘다");
//        diary.setHasTag(true);
//        diary.setMember(member);
//        em.persist(diary);
//
//        DiaryLike diaryLike = new DiaryLike();
//        diaryLike.setMember(member);
//        diaryLike.setDiary(diary);
//        em.persist(diaryLike);
//    }
//
//    @DisplayName("사용자가 소유한 잔디 색상 조회")
//    @Test
//    public void test2() {
//        ColorCode colorCode = new ColorCode();
//        colorCode.setColorName("파랑");
//        colorCode.setRgb("001");
//        em.persist(colorCode);
//
//        MemberPurchasedColor memberPurchasedColor = new MemberPurchasedColor();
//        memberPurchasedColor.setColorCode(colorCode);
//        em.persist(memberPurchasedColor);
//
//        Member member = new Member();
//        member.setNickname("zzz");
//        member.setCurrentColorCode(colorCode);
//        em.persist(member);
//    }
//
//    @DisplayName("ConditionLevel을 enum으로 사용할 경우")
//    @Test
//    public void test3() {
//        Diary diary = new Diary();
//        diary.setConditionLevel(ConditionLevel.LEVEL_1);
//        em.persist(diary);
//
//        em.clear();
//
//        Diary foundDiary = em.find(Diary.class, diary.getId());
//        Float transparency = foundDiary.getConditionLevel().getTransparency();
//        System.out.println("transparency = " + transparency);
//    }
//}
