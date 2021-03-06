package com.shawn.jooo.framework.core.validate;

import org.springframework.lang.Nullable;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

public class Preconditions {

    public static void checkArgument(boolean expression) {
        if (!expression) {
            throw new IllegalArgumentException();
        }
    }

    public static void checkArgument(boolean expression, @Nullable String errorMessage) {
        if (!expression) {
            throw new IllegalArgumentException(String.valueOf(errorMessage));
        }
    }

    public static void checkArgument(boolean expression, RuntimeException ex) {
        if (!expression) {
            throw ex;
        }
    }

    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        } else {
            return reference;
        }
    }

    public static <T> T checkNotNull(T reference, @Nullable String errorMessage) {
        if (reference == null) {
            throw new NullPointerException(String.valueOf(errorMessage));
        } else {
            return reference;
        }
    }

    public static <T> T checkNotNull(T reference, RuntimeException ex) {
        if (reference == null) {
            throw ex;
        } else {
            return reference;
        }
    }


    public static <T> T checkNotEmpty(T reference) {
        if (isEmpty(reference)) {
            throw new IllegalArgumentException();
        } else {
            return reference;
        }
    }

    public static <T> T checkNotEmpty(T reference, @Nullable String errorMessage) {
        if (isEmpty(reference)) {
            throw new IllegalArgumentException(String.valueOf(errorMessage));
        } else {
            return reference;
        }
    }

    public static <T> T checkNotEmpty(T reference, RuntimeException ex) {
        if (isEmpty(reference)) {
            throw ex;
        } else {
            return reference;
        }
    }

    public static <T> void validate(T reference) {
        Validations.validate(reference);
    }

    public static <T> void validate(T reference, @Nullable String errorMessage) {
        Validations.validate(reference, errorMessage);
    }

    public static <T> void validate(T reference, RuntimeException ex) {
        Validations.validate(reference);
    }

    private static boolean isEmpty(Object object) {
        if (object == null) {
            return true;
        } else if (object instanceof CharSequence) {
            return ((CharSequence) object).length() == 0;
        } else if (object.getClass().isArray()) {
            return Array.getLength(object) == 0;
        } else if (object instanceof Collection) {
            return ((Collection) object).isEmpty();
        } else {
            return object instanceof Map ? ((Map) object).isEmpty() : false;
        }
    }
}
