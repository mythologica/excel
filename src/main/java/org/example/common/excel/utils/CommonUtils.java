package org.example.common.excel.utils;

import java.io.FileReader;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonUtils {

    public static String getYYYYMMDDHH24MISSMS() {
        StringBuffer sb = new StringBuffer();

        Calendar cldr = Calendar.getInstance();

        sb.append(cldr.get(Calendar.YEAR))
                .append(StringUtils.leftPad(String.valueOf(cldr.get(Calendar.MONTH) + 1), 2, '0'))
                .append(StringUtils.leftPad(String.valueOf(cldr.get(Calendar.DATE)), 2, '0'))
                .append(StringUtils.leftPad(String.valueOf(cldr.get(Calendar.HOUR_OF_DAY)), 2, '0'))
                .append(StringUtils.leftPad(String.valueOf(cldr.get(Calendar.MINUTE)), 2, '0'))
                .append(StringUtils.leftPad(String.valueOf(cldr.get(Calendar.SECOND)), 2, '0'))
                .append(StringUtils.leftPad(String.valueOf(cldr.get(Calendar.MILLISECOND)), 4, '0'))
        ;
        return sb.toString();
    }

    public static String getCreateDateFormat() {
        StringBuffer sb = new StringBuffer();

        Calendar cldr = Calendar.getInstance();

        sb.append(cldr.get(Calendar.YEAR))
                .append(StringUtils.leftPad("-" + (cldr.get(Calendar.MONTH) + 1), 2, '0'))
                .append(StringUtils.leftPad("-" + (cldr.get(Calendar.DATE)), 2, '0'))
                .append(StringUtils.leftPad(" " + (cldr.get(Calendar.HOUR_OF_DAY)), 2, '0'))
                .append(StringUtils.leftPad(":" + (cldr.get(Calendar.MINUTE)), 2, '0'))
                .append(StringUtils.leftPad(":" + (cldr.get(Calendar.SECOND)), 2, '0'))
        ;
        return sb.toString();
    }

    /**
     * 문자열 byte를 구하는 것
     *
     * @param text
     * @return
     */
    public static int getBytesUtf8(String text) {
        return CommonUtils.getBytes(text, ExcelCode.UTF_8_ENC);
    }

    /**
     * 문자열 byte를 구하는 것 (charset기준)
     *
     * @param text
     * @param charsetName
     * @return
     */
    public static int getBytes(String text, String charsetName) {
        try {
            if (charsetName == null) {
                return text.getBytes().length;
            }
            return text.getBytes(charsetName).length;
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * 반각문자로 변경한다
     *
     * @param src 변경할값
     * @return String 변경된값
     */
    public static String toHalfChar(String src) {
        StringBuffer strBuf = new StringBuffer();
        char c = 0;
        int nSrcLength = src.length();
        for (int i = 0; i < nSrcLength; i++) {
            c = src.charAt(i);
            // 영문이거나 특수 문자 일경우.
            if (c >= '！' && c <= '～') {
                c -= 0xfee0;
            } else if (c == '　') {
                c = 0x20;
            }
            // 문자열 버퍼에 변환된 문자를 쌓는다
            strBuf.append(c);
        }
        return strBuf.toString();
    }

    /**
     * 전각문자로 변경한다.
     *
     * @param src 변경할값
     * @return String 변경된값
     */
    public static String toFullChar(String src) {
        // 입력된 스트링이 null 이면 null 을 리턴
        if (src == null)
            return null;
        // 변환된 문자들을 쌓아놓을 StringBuffer 를 마련한다
        StringBuffer strBuf = new StringBuffer();
        char c = 0;
        int nSrcLength = src.length();
        for (int i = 0; i < nSrcLength; i++) {
            c = src.charAt(i);
            // 영문이거나 특수 문자 일경우.
            if (c >= 0x21 && c <= 0x7e) {
                c += 0xfee0;
            }
            // 공백일경우
            else if (c == 0x20) {
                c = 0x3000;
            }
            // 문자열 버퍼에 변환된 문자를 쌓는다
            strBuf.append(c);
        }
        return strBuf.toString();
    }

    /**
     * List<String>를 String[]배열로 변환한다.
     *
     * @param list
     * @return
     */
    public static String[] listToStringArray(List<String> list) {
        if (list != null) {
            return list.toArray(new String[list.size()]);
        }
        return new String[0];
    }

    /**
     * String[]를 List<String>으로 변환한다.
     *
     * @param array
     * @return
     */
    public static List<String> stringArrayToList(String[] array) {
        List<String> list = new ArrayList<String>();
        if (array != null && array.length > 0) {
            int cnt = array.length;
            list.addAll(Arrays.asList(array).subList(0, cnt));
        }
        return list;
    }

    /**
     * 단어의 길이만큼 문자열을 만든다. 문자의 왼쪽 남은길이 만큼 "0"으로 채웁니다.
     *
     * @param number
     * @param len
     * @return
     */
    public static String zlpad(int number, int len) {
        return zlpad(String.valueOf(number), len);
    }

    /**
     * 단어의 길이만큼 문자열을 만든다. 문자의 왼쪽 남은길이 만큼 "0"으로 채웁니다.
     *
     * @param text
     * @param len
     * @return
     */
    public static String zlpad(String text, int len) {
        if (text != null) {
            return lpad(text, len, '0');
        }
        return lpad("", len, '0');
    }

    /**
     * 단어의 길이만큼 문자열을 만든다. 문자의 오른쪽 남은길이 만큼 " "으로 채웁니다.
     *
     * @param text
     * @param len
     * @return
     */
    public static String brpad(String text, int len) {
        return rpad(text, len, ' ');
    }

    /**
     * 단어의 길이만큼 문자열을 만든다. 문자의 왼쪽 남은길이 만큼 원하는 char로 채웁니다.
     *
     * @param text
     * @param len
     * @param strChar
     * @return
     */
    public static String lpad(String text, int len, char strChar) {
        // today.append(String.format("%04d", cal.get(cal.YEAR)));
        StringBuilder sbAddChar = new StringBuilder();
        int strlen = text.getBytes().length;
        for (int i = strlen; i < len; i++) {
            sbAddChar.append(strChar);
        }
        // LPAD이므로, 채울문자열 + 원래문자열로 Concate한다.
        return sbAddChar + text;
    }

    /**
     * 단어의 길이만큼 문자열을 만든다. 문자의 오른쪽 남은길이 만큼 원하는 char로 채웁니다.
     *
     * @param text
     * @param len
     * @param strChar
     * @return
     */
    public static String rpad(String text, int len, char strChar) {
        StringBuilder sbAddChar = new StringBuilder();
        int strlen = text.getBytes().length;
        for (int i = strlen; i < len; i++) {
            sbAddChar.append(strChar);
        }
        // LPAD이므로, 채울문자열 + 원래문자열로 Concate한다.
        return text + sbAddChar;
    }

    /**
     * 전,후의 whitespace를 제거합니다.
     *
     * @param text
     * @return
     */
    public static String trim(String text) {
        return text != null ? text.trim() : "";
    }

    /**
     * 문자열이 참을 의미하는 것인지
     *
     * @param text
     * @return
     */
    public static boolean parseBoolean(String text) {
        return parseBoolean(text, false);
    }

    public static boolean parseBoolean(String text, boolean defaultValue) {
        if (text != null) {
            String val = text.trim().toLowerCase();
            return "y".equals(val) || "true".equals(val) || "1".equals(val);
        }
        return defaultValue;
    }

    /**
     * 문자열을 숫자(int형)로 반환한다.
     *
     * @param text
     * @return
     */
    public static int parseInt(String text) {
        return parseInt(text, 0);
    }

    public static int parseInt(String text, int defaultValue) {
        try {
            return Integer.parseInt(trim(text));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 문자열을 숫자(long형)로 반환한다.
     *
     * @param text
     * @return
     */
    public static long parseLong(String text) {
        return parseLong(trim(text), 0L);
    }

    public static long parseLong(String text, long defaultValue) {
        try {
            return Long.parseLong(trim(text));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 문자열을 숫자(float형)로 반환한다.
     *
     * @param text
     * @return
     */
    public static float parseFloat(String text) {
        return parseFloat(trim(text), 0.0f);
    }

    public static float parseFloat(String text, float defaultValue) {
        try {
            return Float.parseFloat(trim(text));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 문자열을 숫자(double형)로 반환한다.
     *
     * @param text
     * @return
     */
    public static double parseDouble(String text) {
        return parseDouble(trim(text), 0.0);
    }

    public static double parseDouble(String text, double defaultValue) {
        try {
            return Double.parseDouble(trim(text));
        } catch (Exception e) {
            return 0.0;
        }
    }

    /**
     * 문자열을 숫자(BigDecimal형)로 반환한다.
     *
     * @param text
     * @return
     */
    public static BigDecimal parseBigDecimal(String text) {
        return parseBigDecimal(trim(text),new BigDecimal(0));
    }

    public static BigDecimal parseBigDecimal(String text, BigDecimal defaultValue) {
        try {
            return new BigDecimal(trim(text));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * underscore문자열을 camel형으로 변환
     *
     * @param underscoreText
     * @param isFirstCharUpper
     * @return
     */
    public static String toCamel(String underscoreText, boolean isFirstCharUpper) {
        StringBuffer sb = new StringBuffer();

        for (String tmp : underscoreText.split("_")) {
            if (sb.length() == 0) {
                if (isFirstCharUpper) {
                    sb.append(tmp.substring(0, 1).toUpperCase());
                } else {
                    sb.append(tmp.substring(0, 1).toLowerCase());
                }
            } else {
                sb.append(tmp.substring(0, 1).toUpperCase());
            }
            sb.append(tmp.substring(1).toLowerCase());
        }
        return sb.toString();
    }

    /**
     * camel문자열을 underscore문자열로 변환
     *
     * @param camelText
     * @return
     */
    public static String toUnderscore(String camelText) {
        String regex = "([a-z])([A-Z]+)";
        String replacement = "$1_$2";
        return camelText.replaceAll(regex, replacement).toUpperCase();
    }

    /**
     * 분리문자로 문자열을 불리하여 배열로 반환
     *
     * @param text
     * @param splitWord
     * @param hasEmpty
     * @return
     */
    public static String[] splitText(String text, String splitWord, boolean hasEmpty) {
        String[] arr = {};
        if (text != null && text.length() > 0) {
            if (hasEmpty) {
                arr = text.split(splitWord, -1);
            } else {
                arr = text.split(splitWord);
            }
        }
        return arr;
    }

    /**
     * 랜덤 문자열
     *
     * @param wordLength
     * @return
     */
    public static String randomWordLowerCase(int wordLength) {
        Random r = new Random();
        StringBuffer sb = new StringBuffer();

        char LCa = 'a', LCz = 'z';

        for (int i = 0; i < wordLength; i++) {
            char a = (char) (LCa + r.nextInt(LCz - LCa));
            sb.append(a);
        }

        return sb.toString();
    }

    public static String randomWordUpperCase(int wordLength) {
        Random r = new Random();
        StringBuffer sb = new StringBuffer();

        char UCA = 'A', UCZ = 'Z';

        for (int i = 0; i < wordLength; i++) {
            char a = (char) (UCA + r.nextInt(UCZ - UCA));
            sb.append(a);
        }

        return sb.toString();
    }

    public static String randomWordMixCase(int wordLength) {
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        char LCa = 'a', LCz = 'z', UCA = 'A', UCZ = 'Z';
        for (int i = 0; i < wordLength; i++) {
            boolean isUpper = r.nextInt(wordLength) % 2 == 0;
            char a = 'a';
            if (isUpper) {
                a = (char) (UCA + r.nextInt(UCZ - UCA));
            } else {
                a = (char) (LCa + r.nextInt(LCz - LCa));
            }
            sb.append(a);
        }
        return sb.toString();
    }

    /**
     * map의 내용을 log로 출력
     *
     * @param log
     * @param map
     */
    public static void printMap(Logger log, Map<String, String> map) {
        StringBuffer sb = new StringBuffer();
        for (String key : map.keySet()) {
            String value = map.get(key);
            sb.append("key:" + key + ",value:" + value);
        }
        log.debug(sb.toString());
    }

    /**
     * 실행시간을 msec단위로 지연시키는 것
     *
     * @param msec
     */
    public static void delay(long msec) {
        try {
            Thread.sleep(msec);
        } catch (Exception e) {
        }
    }

}
