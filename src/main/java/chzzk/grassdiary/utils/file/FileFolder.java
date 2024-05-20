package chzzk.grassdiary.utils.file;

import lombok.Getter;

@Getter
public enum FileFolder {
    // TODO: 폴더 구조 비공개 돌릴 수 있는 방법
    USER_PROFILE("user-profile/"),
    PERSONAL_DIARY("personal-diary/"),
    GROUP_DIARY("group-diary/");

    private final String directoryPath;

    FileFolder(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    public static FileFolder of(String request) {
        return FileFolder.valueOf(request);
    }

}
