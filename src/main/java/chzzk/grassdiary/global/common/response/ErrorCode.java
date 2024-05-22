package chzzk.grassdiary.global.common.response;

public enum ErrorCode implements CodeModel {
    REQUEST_PARAM_NOT_MATCH(404, "C001", "Request Parameter가 유효한지 확인해 주세요.");

    private final int statusCode;
    private final String systemErrorCode;
    private final String message;

    ErrorCode(int statusCode,  String systemErrorCode, String message) {
        this.statusCode = statusCode;
        this.systemErrorCode = systemErrorCode;
        this.message = message;
    }

    @Override
    public int getStatusCode() {
        return this.statusCode;
    }

    @Override
    public String getSystemErrorCode() {
        return this.systemErrorCode;
    }


    @Override
    public String getErrorMessage() {
        return this.message;
    }
}
