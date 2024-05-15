package chzzk.grassdiary.exception.response;

public interface CodeModel {
    int getStatusCode();
    String getSystemErrorCode();
    String getErrorMessage();
}
