package chzzk.grassdiary.global.util.file;

import chzzk.grassdiary.global.common.error.exception.SystemException;
import chzzk.grassdiary.global.common.response.ClientErrorCode;

import java.util.UUID;

public class FileNameUtil {
    public static void checkImageMimeType(String mimeType) {
        if (!(mimeType.equals("image/jpg") || mimeType.equals("image/jpeg")
                || mimeType.equals("image/png") || mimeType.equals("image/gif"))) {
            throw new SystemException(ClientErrorCode.INVALID_IMAGE_FORMAT);
        }
    }

    public static String fileNameConvert(String fileName) {
        StringBuilder builder = new StringBuilder();
        UUID uuid = UUID.randomUUID();
        String extension = getExtension(fileName);

        builder.append(uuid).append(".").append(extension);

        return builder.toString();
    }

    private static String getExtension(String fileName) {
        int pos = fileName.lastIndexOf(".");

        return fileName.substring(pos + 1);
    }
}
