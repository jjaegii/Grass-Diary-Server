package chzzk.grassdiary.global.common.response;

public interface ErrorCodeModel {
    int getStatusCode();
    String getSystemErrorCode();
    String getErrorMessage();
}
