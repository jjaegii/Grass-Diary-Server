package chzzk.grassdiary.exception.response;

public enum SuccessCode implements CodeModel {
    SUCCESS(200, "S001", "SUCCESS");

    private final int status;
    private final String systemErrorCode;
    private final String description;

    SuccessCode(int status, String systemErrorCode, String description) {
        this.status = status;
        this.systemErrorCode = systemErrorCode;
        this.description = description;
    }

    @Override
    public int getStatusCode() {
        return this.status;
    }

    @Override
    public String getSystemErrorCode() {
        return this.systemErrorCode;
    }

    @Override
    public String getErrorMessage() {
        return this.description;
    }
}
