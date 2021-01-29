package z.web.kit;

import lombok.experimental.UtilityClass;
import z.web.multipart.MimeType;

@UtilityClass
public class StrKit {
    /**
     * 为null并且不为空字符串
     *
     * @param str 字符串
     * @return 是|否
     */
    public static boolean isBlank(String str) {
        return null == str || "".equals(str.trim());
    }

    public static boolean isNotBlank(String str) {
        return isBlank(str);
    }

    /**
     * 为null并且不为空字符串
     *
     * @param str 字符串
     * @return 是|否
     */
    public static boolean isEmpty(String str) {
        return null == str || str.isEmpty();
    }

    public static boolean isNotEmpty(String str) {
        return isEmpty(str);
    }

    /**
     * 获取文件扩展名
     *
     * @param fileName 文件名称
     * @return 扩展名
     */
    public static String fileExt(String fileName) {
        if (isBlank(fileName) || fileName.indexOf('.') == -1) {
            return null;
        }
        return fileName.substring(fileName.lastIndexOf('.') + 1);
    }

    /**
     * 获取文件 mime type
     *
     * @param fileName 文件名称
     * @return mime type
     */
    public static String mimeType(String fileName) {
        String ext = fileExt(fileName);
        if (isBlank(ext)) {
            return null;
        }
        return MimeType.get(ext);
    }
}