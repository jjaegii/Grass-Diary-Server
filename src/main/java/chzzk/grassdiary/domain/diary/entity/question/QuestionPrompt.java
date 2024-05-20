package chzzk.grassdiary.domain.diary.entity.question;

import java.util.Random;

public enum QuestionPrompt {
    TODAY_SUMMARY("오늘 하루를 한 문장 혹은 한 단어로 요약해 보세요."),
    FEELINGS("오늘 기분은 어땠나요? 가장 기분 좋았던 순간은 언제였나요?"),
    MEMORABLE_EVENT("오늘 하루 동안 가장 기억에 남는 일이나, 특별했던 일은 무엇인가요?"),
    DAILY_GOAL("오늘의 목표는 무엇이었고, 그것을 이룰 수 있었나요?"),
    LEARNING("오늘 배운 것이나 새롭게 알게 된 사실이 있다면 무엇인가요?"),
    CHALLENGE("오늘 가장 큰 도전은 무엇이었고, 어떻게 극복하였나요?"),
    GRATITUDE("오늘 누군가에게 감사했던 일이나, 감사했던 순간은 무엇이었나요?"),
    LAUGHTER("오늘 당신을 웃게 한 일은 무엇이었나요? 가장 웃었던 순간은 언제인가요?"),
    ACHIEVEMENT("오늘의 성취는 무엇이었나요? 자랑할만한 순간이나, 성취감을 느낀 일은 무엇이었나요?"),
    HAPPINESS("오늘 가장 행복했던 순간은 언제인가요?"),
    STRONGEST_EMOTION("가장 큰 감정을 느낀 순간은 언제였나요?"),
    LESSON_LEARNED("오늘의 가장 큰 교훈은 무엇이었나요?"),
    AMAZING_DISCOVERY("오늘의 놀라운 발견은 무엇이었나요?"),
    IMPORTANT_MOMENT("오늘 하루에서 가장 중요한 순간은 무엇이었나요?"),
    TOMORROW_EXPECTATION("내일에 대한 가장 큰 기대는 무엇인가요?"),
    BEST_FOOD("오늘 가장 맛있게 먹은 음식은 무엇인가요?"),
    USEFUL_APP("오늘 사용한 가장 유용한 앱은 무엇인가요?"),
    PROJECT_PROGRESS("현재 진행 중인 프로젝트나 일에 대한 진척 상황은 어떤가요?"),
    SMALL_JOY("오늘의 일상에서 느낀 작은 행복이나 만족감은 무엇이었나요?"),
    RECENT_BOOK("어떤 책을 최근에 읽었나요?"),
    WEATHER("오늘의 날씨는 어땠나요?"),
    DREAM("꿈 속에서 이루고 싶은 것은 무엇인가요?"),
    MBTI("당신의 엠비티아이에 대해 불만이 무엇인가요? 좋은 점은 무엇이라고 생각하나요?"),
    LAST_TIME_LAUGHED("마지막으로 미친 듯이 웃은 적이 언제인가요?"),
    IF_ONE_DAY("만약 오늘 하루만 살 수 있다면, 어떻게 보내고 싶나요?"),
    COMFORT_MUSIC("어떤 음악이 당신을 힘들 때 위로가 되나요?"),
    STRONGEST_SKILL("자신의 가장 자신 있는 능력은 무엇인가요?"),
    LETTER_TO_SELF("만약 현재의 자신에게 편지를 쓴다면, 어떤 내용인가요?"),
    SUPERPOWER("만약 한 가지 초능력을 가질 수 있다면, 무엇을 선택하고 싶나요?"),
    EARLIEST_MEMORY("가장 어렸을 때의 기억은 무엇인가요?");

    private final String question;
    private static final Random random = new Random();

    QuestionPrompt(String question) {
        this.question = question;
    }

    public static QuestionPrompt getRandomQuestion() {
        random.setSeed(System.currentTimeMillis());
        QuestionPrompt[] prompt = values();

        return prompt[random.nextInt(prompt.length)];
    }

    public String getQuestion() {
        return question;
    }
}
