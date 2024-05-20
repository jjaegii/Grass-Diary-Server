package chzzk.grassdiary.domain.color;

import lombok.Getter;

@Getter
public enum ConditionLevel {
    LEVEL_1("Very Bad", 0.1f),
    LEVEL_2("Bad", 0.2f),
    LEVEL_3("Not Good", 0.3f),
    LEVEL_4("Average", 0.4f),
    LEVEL_5("Good", 0.5f),
    LEVEL_6("Very Good", 0.6f),
    LEVEL_7("Excellent", 0.7f),
    LEVEL_8("Fantastic", 0.8f),
    LEVEL_9("Amazing", 0.9f),
    LEVEL_10("Perfect", 1.0f);
    
    private final String label;
    private final Float transparency;

    ConditionLevel(String label, Float transparency) {
        this.label = label;
        this.transparency = transparency;
    }
}