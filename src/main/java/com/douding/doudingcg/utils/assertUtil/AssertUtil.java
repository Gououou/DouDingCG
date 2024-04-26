package com.douding.doudingcg.utils.assertUtil;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.douding.doudingcg.base.enums.BaseEnum;
import com.douding.doudingcg.constant.enums.ResponseCodeEnum;
import com.douding.doudingcg.exception.ServiceException;

import java.util.Map;
import java.util.function.Supplier;

/**
 * @author Guo
 * @create 2024-04-26 15:47
 */
public class AssertUtil {
    private static final String TEMPLATE_VALUE_MUST_BE_BETWEEN_AND = "The value must be between {} and {}.";

    public AssertUtil () {
    }

    public static <X extends Throwable> void isTrue(boolean expression, Supplier<? extends X> errorSupplier) throws X {
        if (!expression) {
            throw errorSupplier.get();
        }
    }

    public static void isTrue(boolean expression, BaseEnum baseEnum) throws ServiceException {
        isTrue(expression, () -> {
            return new ServiceException(baseEnum);
        });
    }

    public static <X extends Throwable> void isFalse(boolean expression, Supplier<X> errorSupplier) throws X {
        if (expression) {
            throw errorSupplier.get();
        }
    }

    public static void isFalse(boolean expression, BaseEnum baseEnum) throws ServiceException {
        isFalse(expression, () -> {
            return new ServiceException(baseEnum);
        });
    }

    public static <X extends Throwable> void isNull(Object object, Supplier<X> errorSupplier) throws X {
        if (null != object) {
            throw errorSupplier.get();
        }
    }

    public static void isNull(Object object, BaseEnum baseEnum) throws ServiceException {
        isNull(object, () -> {
            return new ServiceException(baseEnum);
        });
    }

    public static <T, X extends Throwable> T notNull(T object, Supplier<X> errorSupplier) throws X {
        if (null == object) {
            throw errorSupplier.get();
        } else {
            return object;
        }
    }

    public static <T> T notNull(T object, BaseEnum baseEnum) throws ServiceException {
        return notNull(object, () -> {
            return new ServiceException(baseEnum);
        });
    }

    public static <T extends CharSequence, X extends Throwable> T notEmpty(T text, Supplier<X> errorSupplier) throws X {
        if (StrUtil.isEmpty(text)) {
            throw errorSupplier.get();
        } else {
            return text;
        }
    }

    public static <T extends CharSequence> T notEmpty(T text, BaseEnum baseEnum) throws ServiceException {
        return notEmpty(text, () -> {
            return new ServiceException(baseEnum);
        });
    }

    public static <T extends CharSequence, X extends Throwable> T notBlank(T text, Supplier<X> errorMsgSupplier) throws X {
        if (StrUtil.isBlank(text)) {
            throw errorMsgSupplier.get();
        } else {
            return text;
        }
    }

    public static <T extends CharSequence> T notBlank(T text, BaseEnum baseEnum) throws ServiceException {
        return notBlank(text, () -> {
            return new ServiceException(baseEnum);
        });
    }

    public static <T extends CharSequence, X extends Throwable> T notContain(CharSequence textToSearch, T substring, Supplier<X> errorSupplier) throws X {
        if (StrUtil.contains(textToSearch, substring)) {
            throw errorSupplier.get();
        } else {
            return substring;
        }
    }

    public static String notContain(String textToSearch, String substring, BaseEnum baseEnum) throws ServiceException {
        return (String)notContain((CharSequence)textToSearch, (CharSequence)substring, (Supplier)(() -> {
            return new ServiceException(baseEnum);
        }));
    }

    public static <T, X extends Throwable> T[] notEmpty(T[] array, Supplier<X> errorSupplier) throws X {
        if (ArrayUtil.isEmpty(array)) {
            throw errorSupplier.get();
        } else {
            return array;
        }
    }

    public static <T> T[] notEmpty(T[] array, BaseEnum baseEnum) throws ServiceException {
        return notEmpty(array, () -> {
            return new ServiceException(baseEnum);
        });
    }

    public static <T, X extends Throwable> T[] noNullElements(T[] array, Supplier<X> errorSupplier) throws X {
        if (ArrayUtil.hasNull(array)) {
            throw errorSupplier.get();
        } else {
            return array;
        }
    }

    public static <T> T[] noNullElements(T[] array, BaseEnum baseEnum) throws ServiceException {
        return noNullElements(array, () -> {
            return new ServiceException(baseEnum);
        });
    }

    public static <E, T extends Iterable<E>, X extends Throwable> T notEmpty(T collection, Supplier<X> errorSupplier) throws X {
        if (CollUtil.isEmpty(collection)) {
            throw errorSupplier.get();
        } else {
            return collection;
        }
    }

    public static <E, T extends Iterable<E>> T notEmpty(T collection, BaseEnum baseEnum) throws ServiceException {
        return notEmpty(collection, () -> {
            return new ServiceException(baseEnum);
        });
    }

    public static <K, V, T extends Map<K, V>, X extends Throwable> T notEmpty(T map, Supplier<X> errorSupplier) throws X {
        if (MapUtil.isEmpty(map)) {
            throw errorSupplier.get();
        } else {
            return map;
        }
    }

    public static <K, V, T extends Map<K, V>> T notEmpty(T map, BaseEnum baseEnum) throws ServiceException {
        return notEmpty(map, () -> {
            return new ServiceException(baseEnum);
        });
    }

    public static <T> T isInstanceOf(Class<?> type, T obj, BaseEnum baseEnum) throws ServiceException {
        notNull(type, (BaseEnum) ResponseCodeEnum.SYS_ASSERT_PARAM_EMPTY);
        if (!type.isInstance(obj)) {
            throw new ServiceException(baseEnum);
        } else {
            return obj;
        }
    }

    public static void isAssignable(Class<?> superType, Class<?> subType, BaseEnum baseEnum) throws ServiceException {
        notNull(superType, (BaseEnum)ResponseCodeEnum.SYS_ASSERT_PARAM_EMPTY);
        if (subType == null || !superType.isAssignableFrom(subType)) {
            throw new ServiceException(baseEnum);
        }
    }

    public static void state(boolean expression, Supplier<BaseEnum> errorMsgSupplier) throws ServiceException {
        if (!expression) {
            throw new ServiceException((BaseEnum)errorMsgSupplier.get());
        }
    }

    public static void state(boolean expression, BaseEnum baseEnum) throws ServiceException {
        if (!expression) {
            throw new ServiceException(baseEnum);
        }
    }

    public static int checkIndex(int index, int size, BaseEnum baseEnum) throws ServiceException, IndexOutOfBoundsException {
        if (index >= 0 && index < size) {
            return index;
        } else {
            throw new ServiceException(baseEnum);
        }
    }

    public static <X extends Throwable> int checkBetween(int value, int min, int max, Supplier<? extends X> errorSupplier) throws X {
        if (value >= min && value <= max) {
            return value;
        } else {
            throw errorSupplier.get();
        }
    }

    public static int checkBetween(int value, int min, int max, BaseEnum baseEnum) {
        return checkBetween(value, min, max, () -> {
            return new ServiceException(baseEnum);
        });
    }

    public static int checkBetween(int value, int min, int max) {
        return checkBetween(value, min, max, (BaseEnum)ResponseCodeEnum.SYS_ASSERT_PARAM_NOT_PASS);
    }

    public static <X extends Throwable> long checkBetween(long value, long min, long max, Supplier<? extends X> errorSupplier) throws X {
        if (value >= min && value <= max) {
            return value;
        } else {
            throw errorSupplier.get();
        }
    }

    public static long checkBetween(long value, long min, long max, BaseEnum baseEnum) {
        return checkBetween(value, min, max, () -> {
            return new ServiceException(baseEnum);
        });
    }

    public static long checkBetween(long value, long min, long max) {
        return checkBetween(value, min, max, (BaseEnum)ResponseCodeEnum.SYS_ASSERT_PARAM_NOT_PASS);
    }

    public static <X extends Throwable> double checkBetween(double value, double min, double max, Supplier<? extends X> errorSupplier) throws X {
        if (!(value < min) && !(value > max)) {
            return value;
        } else {
            throw errorSupplier.get();
        }
    }

    public static double checkBetween(double value, double min, double max, BaseEnum baseEnum) {
        return checkBetween(value, min, max, () -> {
            return new ServiceException(baseEnum);
        });
    }

    public static double checkBetween(double value, double min, double max) {
        return checkBetween(value, min, max, (BaseEnum)ResponseCodeEnum.SYS_ASSERT_PARAM_NOT_PASS);
    }

    public static Number checkBetween(Number value, Number min, Number max) {
        notNull(value, (BaseEnum)ResponseCodeEnum.SYS_ASSERT_PARAM_EMPTY);
        notNull(min, (BaseEnum)ResponseCodeEnum.SYS_ASSERT_PARAM_EMPTY);
        notNull(max, (BaseEnum)ResponseCodeEnum.SYS_ASSERT_PARAM_EMPTY);
        double valueDouble = value.doubleValue();
        double minDouble = min.doubleValue();
        double maxDouble = max.doubleValue();
        if (!(valueDouble < minDouble) && !(valueDouble > maxDouble)) {
            return value;
        } else {
            throw new ServiceException(ResponseCodeEnum.SYS_ASSERT_PARAM_NOT_PASS);
        }
    }

    public static void notEquals(Object obj1, Object obj2, BaseEnum baseEnum) throws ServiceException {
        notEquals(obj1, obj2, () -> {
            return new ServiceException(baseEnum);
        });
    }

    public static <X extends Throwable> void notEquals(Object obj1, Object obj2, Supplier<X> errorSupplier) throws X {
        if (ObjectUtil.equals(obj1, obj2)) {
            throw errorSupplier.get();
        }
    }

    public static void equals(Object obj1, Object obj2, BaseEnum baseEnum) throws ServiceException {
        equals(obj1, obj2, () -> {
            return new ServiceException(baseEnum);
        });
    }

    public static <X extends Throwable> void equals(Object obj1, Object obj2, Supplier<X> errorSupplier) throws X {
        if (ObjectUtil.notEqual(obj1, obj2)) {
            throw errorSupplier.get();
        }
    }
}
