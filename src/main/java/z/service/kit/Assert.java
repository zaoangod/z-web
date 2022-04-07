package z.service.kit;

import java.util.function.Supplier;

public class Assert {

    private Assert() {
    }

    public static <X extends Throwable> void notBlank(String value, Supplier<X> error) throws X {
        if (null == value || 0 == value.trim().length()) {
            throw error.get();
        }
    }

    public static <T, X extends Throwable> void notNull(T value, Supplier<X> error) throws X {
        if (null == value) {
            throw error.get();
        }
    }

}
