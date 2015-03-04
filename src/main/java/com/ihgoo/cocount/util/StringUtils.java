package com.ihgoo.cocount.util;

import android.text.Html;
import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class StringUtils {

    /**
     * 通过{n},格式化.
     *
     * @param src
     * @param objects
     * @return
     */
    public static String format(String src, Object... objects) {
        int k = 0;
        for (Object obj : objects) {
            src = src.replace("{" + k + "}", obj.toString());
            k++;
        }
        return src;
    }


    /**
     * 判断字符串是否为null或""
     *
     * @param string
     * @return 为空或null返回false，否则返回true
     */
    public static boolean isNull(String string) {
        if(null == string && "".equals(string)){
            return true;
        }
        return false;
    }

    public static String join(String[] array, String sep) {
        if (array == null) {
            return null;
        }

        int arraySize = array.length;
        int sepSize = 0;
        if (sep != null && !sep.equals("")) {
            sepSize = sep.length();
        }

        int bufSize = (arraySize == 0 ? 0 : ((array[0] == null ? 16 : array[0]
                .length()) + sepSize) * arraySize);
        StringBuilder buf = new StringBuilder(bufSize);

        for (int i = 0; i < arraySize; i++) {
            if (i > 0) {
                buf.append(sep);
            }
            if (array[i] != null) {
                buf.append(array[i]);
            }
        }
        return buf.toString();
    }

    // no null in array
    public static String jsonJoin(String[] array) {
        int arraySize = array.length;
        int bufSize = arraySize * (array[0].length() + 3);
        StringBuilder buf = new StringBuilder(bufSize);
        for (int i = 0; i < arraySize; i++) {
            if (i > 0) {
                buf.append(',');
            }

            buf.append('"');
            buf.append(array[i]);
            buf.append('"');
        }
        return buf.toString();
    }

    /**
     * 将长字符从截取剩下的用...代替
     *
     * @param input
     * @param count
     * @return
     */
    public static String cutString(String input, int count) {
        return cutString(input, count, null);
    }

    /**
     * 将长字符从截取剩下的用more代替,more为空则用省略号代替
     *
     * @param input
     * @param count
     * @param more
     * @return
     */
    public static String cutString(String input, int count, String more) {
        String resultString = "";
        if (input != null) {
            if (more == null) {
                more = "...";
            }
            if (input.length() > count) {
                resultString = input.substring(0, count) + more;
            } else {
                resultString = input;
            }
        }
        return resultString;
    }

    /**
     * 获得指定中文长度对应的字符串长度，用于截取显示文字，一个中文等于两个英文
     *
     * @param chineseLengthForDisplay
     * @param content
     * @return
     */
    public static int chineseWidth2StringLenth(int chineseLengthForDisplay, String content) {
        int result = 0;
        int displayWidth = chineseLengthForDisplay * 2;
        if (content != null) {
            for (char chr : content.toCharArray()) {
                // 中文
                if (chr >= 0x4e00 && chr <= 0x9fbb) {
                    displayWidth -= 2;
                } else {
                    // 英文
                    displayWidth -= 1;
                }
                if (displayWidth <= 0) {
                    break;
                }
                result++;
            }
        }
        return result;
    }

    /**
     * 将长时间格式字符串转换为字符串,默认为yyyy-MM-dd HH:mm:ss
     *
     * @param time       long型时间,支持毫秒和秒
     * @param dataFormat 需要返回的时间格式，例如： yyyy-MM-dd， yyyy-MM-dd HH:mm:ss
     * @return dataFormat格式的时间结果字符串
     */
    public static String dateFormat(long milliseconds, String dataFormat) {
        long tempTimestamp = milliseconds > 9999999999L ? milliseconds : milliseconds * 1000;
        if (TextUtils.isEmpty(dataFormat)) {
            dataFormat = "yyyy-MM-dd HH:mm:ss";
        }
        Date date = new Date(tempTimestamp * 1l);
        SimpleDateFormat formatter = new SimpleDateFormat(dataFormat);
        return formatter.format(date);
    }


    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
        String telRegex = "[1][34587]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles))
            return false;
        else
            return mobiles.matches(telRegex);
    }


    /**
     * 过滤emoji 或者 其他非文字类型的字符
     *
     * @param source
     * @return
     */
    public static String filterEmoji(String source) {

        if (!containsEmoji(source)) {
            return source;//如果不包含，直接返回
        }
        //到这里铁定包含
        StringBuilder buf = null;

        int len = source.length();

        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);

            if (!isEmojiCharacter(codePoint)) {
                if (buf == null) {
                    buf = new StringBuilder(source.length());
                }

                buf.append(codePoint);
            } else {
            }
        }

        if (buf == null) {
            return "";
        } else {
            if (buf.length() == len) {//这里的意义在于尽可能少的toString，因为会重新生成字符串
                buf = null;
                return source;
            } else {
                return buf.toString();
            }
        }

    }

    /**
     * 是否包含Emoji表情
     *
     * @param content
     * @return
     */
    private static boolean containsEmoji(String content) {
        char[] chars = content.toCharArray();
        boolean hasEmoji = false;
        for (char charItem : chars) {
            hasEmoji = isEmojiCharacter(charItem);
            if (hasEmoji) {
                break;
            }
        }
        return hasEmoji;
    }

    private static boolean isEmojiCharacter(char codePoint) {
        return !((codePoint == 0x0) ||
                (codePoint == 0x9) ||
                (codePoint == 0xA) ||
                (codePoint == 0xD) ||
                ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
                ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF)));
    }

    /**
     * 给字符串加上下划线
     *
     * @param content
     * @return
     */
    private static android.text.Spanned addUnderline(String content) {
        return Html.fromHtml("<u>" + content + "</u>");
    }

    /**
     * 给字符串字体加粗
     *
     * @param content
     * @return
     */
    private static android.text.Spanned addBoldSize(String content) {
        return Html.fromHtml("<b>" + content + "<b>");
    }

    /**
     * 让字符串字体倾斜
     *
     * @param content
     * @return
     */
    private static android.text.Spanned addTilt(String content) {
        return Html.fromHtml("<i>" + content + "</i>");
    }


}
