package com.techasians.doctor.common.utils;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.util.Collection;

public class CommonUtils {

    private static int count(String str, char c) {
        if (isNullOrEmpty(str)) {
            return 0;
        }

        int dem = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == c) {
                dem++;
            }
        }

        return dem;
    }

    public static String toLikeString(String s) {
        if (s != null) {
            s = "%" + s.trim() + "%";
            s = s.toLowerCase();
        }
        return s;
    }

    public static boolean isNull(Object object) {
        return object == null;
    }

    public static boolean isNullOrEmpty(Collection<?> list) {
        return list == null || list.size() == 0;
    }

    /**
     * Check min length of String
     *
     * @param s         : String
     * @param maxLength
     * @return Created by NQManh - 18/6/2019
     */
    public static boolean maxLength(String s, int maxLength) {
        return s.length() <= maxLength;
    }

    /**
     * Check min length of String
     */
    public static boolean minLength(String s, int minLength) {
        return s.length() >= minLength;
    }

    /**
     * Check String only contain number
     */
    public static boolean isNumber(String s) {
        return s.matches("[0-9]+");
    }

    public static boolean isDigitWithDot(String cell) {

        try {
            Double.parseDouble(cell);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String toPlainString(Double value) {
        if (value == null)
            return "";
        MathContext mathContext = MathContext.DECIMAL64;
        BigDecimal big = new BigDecimal(value, mathContext);

        return big.toPlainString();
    }

    public static String toPlainString(Long value) {
        if (value == null)
            return "";
        BigDecimal big = new BigDecimal(value);

        return big.toPlainString();

    }

    public static String subLengthWithConfig(String data, Long length) {
        if (data == null) {
            return "";
        }
        if (data.length() < length) {
            return data;
        }

        return data.substring(0, length.intValue()); //cat = do dai database cho phep
    }


    public static String convertCellDigit(String cell) {
        if (cell == null || "".equals(cell)) {
            return "";
        }
        try {
            BigDecimal d = new BigDecimal(cell);
            DecimalFormat f = new DecimalFormat("###,###.##");
            cell = f.format(d);
        } catch (Exception e) {
            e.getStackTrace();
        }
        return cell;
    }

    public static Object copy(Object fromBean) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        XMLEncoder out = new XMLEncoder(bos);
        out.writeObject(fromBean);
        out.close();
        ByteArrayInputStream bis = new
                ByteArrayInputStream(bos.toByteArray());
        XMLDecoder in = new XMLDecoder(bis);
        Object toBean = in.readObject();
        in.close();
        return toBean;
    }


    /**
     * Check String only contain alphanumeric character
     */
    public static boolean isAlphanumeric(String s) {
        return s.matches("[a-zA-Z]+");
    }

    /**
     * Check String only contain number, alphanumeric, underscore
     */
    public static boolean isNumberOrAlphanumeric(String s) {
        return s.matches("[a-zA-Z0-9_]+");
    }

    public static Long parseLongExcel(String data) {
        if (data == null || "".equals(data)) {
            return 0l;
        }
        try {
            return Long.parseLong(data);
        } catch (Exception e) {
            return 0l;
        }
    }

    public static Double parseDoubleExcel(String data) {
        if (data == null || "".equals(data)) {
            return 0d;
        }
        try {
            return Double.parseDouble(data);
        } catch (Exception e) {
            return 0d;
        }
    }

    public static boolean isDouble(String... datas) {
        for (String data : datas) {
            if (data == null || "".equals(data)) {
                return false;
            }
            try {
                Double.parseDouble(data);
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    public static boolean isOneOfDouble(String... datas) {
        for (String data : datas) {
            try {
                Double.parseDouble(data);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static double parseDoubleExcelDemo(String data) {
        if (data == null || "".equals(data)) {
            return 0;
        }
        try {
            return Double.parseDouble(data);
        } catch (Exception e) {
            return 0;
        }
    }

    public static BigDecimal parseBigDecimalExcel(String data) {
        if (data == null || "".equals(data)) {
            return new BigDecimal("0");
        }
        try {
            return new BigDecimal(data, MathContext.DECIMAL64);
        } catch (Exception e) {
            return new BigDecimal("0");
        }
    }

    public static Integer parseIntegerExcel(String data) {
        if (data == null || "".equals(data)) {
            return 0;
        }
        try {
            return Integer.parseInt(data);
        } catch (Exception e) {
            return 0;
        }
    }

    public static boolean isNullObject(Object obj) {
        return obj == null;
    }

    public static boolean isNullOrEmpty(String name) {
        return name == null || "".equals(name.trim());
    }

    public static boolean isEmptyObject(Object value) {
        if (value == null)
            return true;
        if (value instanceof String)
            return CommonUtils.isNullOrEmpty((String) value);
        return false;
    }

    public static String lowerFirstChar(String simpleName) {
        String first = simpleName.substring(0, 1);
        return first.toLowerCase() + simpleName.substring(1);
    }

    public static String safeToString(Object oldValue) {
        if (oldValue == null) {
            return "";
        }
        if (oldValue instanceof String) {
            String data = (String) oldValue;
            return data.trim();
        }
        return String.valueOf(oldValue);
    }

    public static String getTableName(Class<?> c) {
        String className = c.getSimpleName();
        return toTableName(className);
    }

    public static Long safeToLong(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Long) {
            Long data = (Long) object;
            return data.longValue();
        }
        if (object instanceof BigInteger) {
            return ((BigInteger) object).longValue();
        }

        if (object instanceof Integer) {
            return ((Integer) object).longValue();
        }

        if (object instanceof BigDecimal) {
            return ((BigDecimal) object).longValue();
        }

        if (object instanceof Boolean) return (!((Boolean) object)) ? 0L : 1L;

        return null;

    }

    private static void convertAndAppendTo(StringBuilder modifiedString, char currentChar,
                                           String separator) {
        if (Character.isUpperCase(currentChar)) {
            modifiedString.append(separator);
        }
        currentChar = Character.toUpperCase(currentChar);
        modifiedString.append(currentChar);
    }

    public static String toTableName(String s) {
        StringBuilder modifiedString = new StringBuilder();
        char[] sToArray = s.toCharArray();
        convertAndAppendTo(modifiedString, sToArray[0], "");

        for (int i = 1; i < sToArray.length; i++) {
            convertAndAppendTo(modifiedString, sToArray[i], "_");
        }
        return modifiedString.toString();
    }

    /**
     * Kiểm tra độ dài String theo byte
     *
     * @param s   : String cần kiểm tra
     * @param min : Độ dài nhỏ nhất
     * @param max : Độ dài lớn nhất
     * @return
     * @see :CreatedBy NQManh - 26/6/2019
     */
    public static boolean checkLengthUTF8(String s, int min, int max) {
        return (s.getBytes().length >= min && s.getBytes().length <= max);
    }

    public static boolean compareString(String a, String b) {
        a = a == null ? "" : a.trim();
        b = b == null ? "" : b.trim();
        return a.equals(b);
    }


    public static String convertLangMessage(String lang) {
        String[] temp = lang.split("_");
        if (temp.length > 0) {
            return temp[0];
        }
        return "vi";
    }
}
