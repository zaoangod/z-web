package z.service.route;

import java.util.HashMap;
import java.util.Map;

/**
 * 处理类型
 */
public enum Method {

    get, post, put, patch, delete, head, trace, connect, options, before, after, invalid;

    private static final Map<String, Method> _method = new HashMap<>();

    static {
        for (Method item : values()) {
            _method.put(item.name(), item);
        }
    }

    public static Method get(String method) {
        Method value = _method.get(method);
        return value != null ? value : invalid;
    }
}