package chzzk.grassdiary.global.common.response;

public enum SuccessErrorCode implements ErrorCodeModel {
    SUCCESS(200, "S001", "SUCCESS");

    private final int status;
    private final String systemErrorCode;
    private final String description;

    SuccessErrorCode(int status, String systemErrorCode, String description) {
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
