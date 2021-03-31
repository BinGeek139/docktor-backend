package com.techasians.doctor.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.CharMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.*;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DataUtils {

    private static final Logger logger = LoggerFactory.getLogger(DataUtils.class);

    public static boolean isNullOrEmpty(final Object[] collection) {
        return collection == null || collection.length == 0;
    }

    public static boolean nonEmpty(String text) {
        return !nullOrEmpty(text);
    }

    public static boolean nonEmpty(Collection<?> collection) {
        return !nullOrEmpty(collection);
    }

    public static boolean nullOrEmpty(Collection<?> objects) {
        return objects == null || objects.isEmpty();
    }

    public static boolean nullOrEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    public static boolean notNull(Object object) {
        return !nullObject(object);
    }

    public static boolean nullObject(Object object) {
        return object == null;
    }

    public static boolean nullOrZero(Long value) {
        return (value == null || value.equals(0L));
    }

    public static boolean nullOrZero(String value) {
        return value == null || "0".equals(value);
    }

    public static boolean nullOrZero(Integer value) {
        return (value == null || value.equals(0));
    }

    public static boolean equalsObj(Object obj1, Object obj2) {
        if (obj1 == null || obj2 == null) return false;
        return obj1.equals(obj2);
    }

    public static Integer parseToInt(String value, Integer defaultVal) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return defaultVal;
        }
    }

    public static Integer parseToInt(String value) {
        return parseToInt(value, null);
    }

    public static Integer parseToInt(Object value) {
        return parseToInt(parseToString(value), null);
    }

    public static boolean isNullOrZero(Long value) {
        return (value == null || value.equals(0L));
    }

    public static Long parseToLong(Object value, Long defaultVal) {
        try {
            String str = parseToString(value);
            if (nullOrEmpty(str)) {
                return null;
            }
            return Long.parseLong(str);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return defaultVal;
        }
    }

    public static Long parseToLong(Object value) {
        return parseToLong(value, null);
    }

    public static String parseToString(Object value, String defaultVal) {
        try {
            return String.valueOf(value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return defaultVal;
        }
    }

    public static String parseToString(Object value) {
        return parseToString(value, "");
    }

    public static boolean matchByPattern(String value, String regex) {
        if (nullOrEmpty(regex) || nullOrEmpty(value)) {
            return false;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

    public static void throwIfFailed(boolean test, String message) throws Exception {
        if (!test) throw new Exception(message);
    }

    public static <X extends Throwable> void throwIfFailed(boolean test, Supplier<? extends X> exceptionSupplier) throws X {
        if (!test) throw exceptionSupplier.get();
    }

    public static void throwIf(boolean test, String message) throws Exception {
        if (test) throw new Exception(message);
    }

    public static void assertTrue(boolean test, String message) throws Exception {
        if (!test) throw new IllegalArgumentException(message);
    }


    public static <X extends Throwable> void throwIf(boolean test, Supplier<? extends X> exceptionSupplier) throws X {
        if (test) throw exceptionSupplier.get();
    }

    public static boolean nullOrEmpty(CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNullOrEmpty(CharSequence cs) {
        return nullOrEmpty(cs);
    }

    public static boolean isNullOrEmpty(final Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static <T> T deepCloneObject(T source) {
        try {
            if (source == null) {
                return null;
            }
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(source);
            out.flush();
            out.close();

            ObjectInputStream in = new ObjectInputStream(
                    new ByteArrayInputStream(bos.toByteArray()));
            T dto = (T) in.readObject();
            in.close();
            return dto;
        } catch (IOException | ClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static String objectToJson(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        if (object == null) {
            return null;
        }
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsString(object);
    }

    public static <T> T jsonToObject(String json, Class<T> classOutput) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        if (json == null || json.isEmpty()) {
            return null;
        }
        return mapper.readValue(json, classOutput);
    }

    private static boolean safeEqualString(String str1, String str2, boolean ignoreCase, boolean trimspace) {
        if (str1 == null || str2 == null) {
            return false;
        }

        if (trimspace) {
            str1 = str1.trim();
            str2 = str2.trim();
        }

        if (ignoreCase) {
            return str1.equalsIgnoreCase(str2);
        } else {
            return str1.equals(str2);
        }
    }

    public static boolean safeEqualIgnoreCaseString(String str1, String str2) {
        return safeEqualString(str1, str2, true, true);
    }

    public static boolean safeEqualString(String str1, String str2) {
        return safeEqualString(str1, str2, false, true);
    }

    public static boolean safeEqualIgnoreCaseWithoutTrimSpaceString(String str1, String str2) {
        return safeEqualString(str1, str2, true, false);
    }

    public static boolean safeEqualWithoutTrimSpaceString(String str1, String str2) {
        return safeEqualString(str1, str2, false, false);
    }


    public static List<String> split(String separate, String object) {
        return Optional.ofNullable(object)
                .map(x -> x.split(separate))
                .map(Arrays::asList)
                .orElseGet(ArrayList::new);
    }

    public static String firstNonEmpty(String... strings) {
        for (String string : strings) {
            if (!isNullOrEmpty(string)) {
                return string;
            }
        }
        return "";
    }

    public static <T> T defaultIfNull(final T object, final T defaultValue) {
        return object != null ? object : defaultValue;
    }

    /**
     * Tra ve doi tuong default neu object la null, neu khong thi tra object
     *
     * @param object
     * @param defaultValueSupplier
     * @param <T>
     * @return
     */
    public static <T> T defaultIfNull(final T object, final Supplier<T> defaultValueSupplier) {
        return object != null ? object : defaultValueSupplier.get();
    }

    public static boolean safeEqual(Object obj1, Object obj2) {
        return ((obj1 != null) && (obj2 != null) && obj2.toString().equals(obj1.toString()));
    }

    public static Character safeToCharacter(Object value) {
        return safeToCharacter(value, '0');
    }

    public static Character safeToCharacter(Object value, Character defaulValue) {
        if (value == null) return defaulValue;
        return String.valueOf(value).charAt(0);
    }

    public static String removeStartingZeroes(String number) {
        if (DataUtils.nullOrEmpty(number)) {
            return "";
        }
        return CharMatcher.anyOf("0").trimLeadingFrom(number);
    }

    public static boolean isNullObject(Object obj1) {
        if (obj1 == null) {
            return true;
        }
        if (obj1 instanceof String) {
            return isNullOrEmpty(obj1.toString());
        }
        return false;
    }

    public static Long safeToLong(Object obj1) {
        return safeToLong(obj1, 0L);
    }

    public static String safeToString(Object obj1) {
        return safeToString(obj1, "");
    }

    public static Long safeToLong(Object obj1, Long defaultValue) {
        Long result = defaultValue;
        if (obj1 != null) {
            if (obj1 instanceof BigDecimal) {
                return ((BigDecimal) obj1).longValue();
            }
            if (obj1 instanceof BigInteger) {
                return ((BigInteger) obj1).longValue();
            }
            try {
                result = Long.parseLong(obj1.toString());
            } catch (Exception ignored) {
                throw ignored;
            }
        }

        return result;
    }

    public static String safeToString(Object obj1, String defaultValue) {
        if (obj1 == null) {
            return defaultValue;
        }

        return obj1.toString();
    }

    public static String toUpper(String input) {
        if (isNullOrEmpty(input)) {
            return input;
        }
        return input.toUpperCase();
    }

    public static Short safeToShort(Object obj1) {
        return safeToShort(obj1, (short) 0);
    }

    public static Short safeToShort(Object obj1, Short defaultValue) {
        Short result = defaultValue;
        if (obj1 != null) {
            try {
                result = Short.parseShort(obj1.toString());
            } catch (Exception ignored) {
                throw ignored;
            }
        }

        return result;
    }

    public static boolean isNullOrEmpty(final Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    public static String upperFirstChar(String input) {
        if (DataUtils.isNullOrEmpty(input)) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }

    /**
     * Copy du lieu tu bean sang bean moi
     * Luu y chi copy duoc cac doi tuong o ngoai cung, list se duoc copy theo tham chieu
     * <p>
     * Chi dung duoc cho cac bean java, khong dung duoc voi cac doi tuong dang nhu String, Integer, Long...
     *
     * @param source
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T cloneBean(T source) {
        try {
            if (source == null) {
                return null;
            }
            T dto = (T) source.getClass().getConstructor().newInstance();
            BeanUtils.copyProperties(source, dto);
            return dto;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    //thangtv22 them ham fomat tien hien thi
    public static String format(Long input) {
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        String result = formatter.format(input);
        return result;
    }

    public static Double safeToDouble(Object obj1) {
        return safeToDouble(obj1, 0.0);
    }

    public static Double safeToDouble(Object obj1, Double defaultValue) {
        Double result = defaultValue;
        if (obj1 != null) {
            try {
                result = Double.parseDouble(obj1.toString());
            } catch (Exception ignored) {
                logger.error(ignored.getMessage(), ignored);
            }
        }

        return result;
    }


    public static Long convertStringToLong(String input) throws Exception {
        try {
            Long result = Long.parseLong(input);
            return result;
        } catch (NumberFormatException ex) {
            throw new Exception("Format number");
        }
    }

    public static String readFromInputStream(InputStream inputStream)
            throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br
                     = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }

}
