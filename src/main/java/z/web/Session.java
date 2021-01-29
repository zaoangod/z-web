package z.web;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

public class Session {

    private final Map<String, Object> data = new HashMap<>();

    @Getter
    @Setter
    private String id = null;

    @Getter
    @Setter
    private String ip = null;

    @Getter
    @Setter
    private long created = -1;

    @Getter
    @Setter
    private long expired = -1;

    public <T> T data(String name) {
        Object object = this.data.get(name);
        return null == object ? null : (T) object;
    }

    public Map<String, Object> data() {
        return data;
    }

    public Session data(String name, Object value) {
        this.data.put(name, value);
        return this;
    }

    public Session removeData(String name) {
        this.data.remove(name);
        return this;
    }

    public Session remove(String name) {
        this.removeData(name);
        return this;
    }
}