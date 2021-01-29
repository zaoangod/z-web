/**
 * Copyright (c) 2017, biezhi 王爵 (biezhi.me@gmail.com)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package z.web;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Config {

    private final Map<String, String> data = new ConcurrentHashMap<>();

    public static Config empty() {
        return new Config();
    }

    public static Config of(@NonNull Map<String, String> map) {
        Config config = new Config();
        map.forEach(config.data::put);
        return config;
    }

    public Config set(@NonNull String key, @NonNull String value) {
        data.put(key, value);
        return this;
    }

    public Config set(@NonNull Map<String, String> map) {
        map.forEach(this.data::put);
        return this;
    }

    public Config set(@NonNull Config config) {
        this.data.putAll(config.data);
        return this;
    }

    //-------------------------------------------------------------
    public Optional<String> get(String key) {
        return null == key ? Optional.empty() : Optional.of(data.get(key));
    }

    public String get(String key, String defaultValue) {
        return get(key).orElse(defaultValue);
    }

    public String getOrNull(String key) {
        return get(key).orElse(null);
    }
    //-------------------------------------------------------------

    public Optional<Integer> getInt(String key) {
        return get(key).map(Integer::parseInt);
    }

    public Integer getIntOrNull(String key) {
        return getInt(key).orElse(null);
    }

    public Integer getInt(String key, int defaultValue) {
        return getInt(key).orElse(defaultValue);
    }

    //-------------------------------------------------------------
    public Optional<Long> getLong(String key) {
        return get(key).map(Long::parseLong);
    }

    public Long getLongOrNull(String key) {
        return getLong(key).orElse(null);
    }

    public Long getLong(String key, long defaultValue) {
        return getLong(key).orElse(defaultValue);
    }

    //-------------------------------------------------------------
    public Optional<Boolean> getBoolean(String key) {
        return get(key).map(Boolean::parseBoolean);
    }

    public Boolean getBooleanOrNull(String key) {
        return getBoolean(key).orElse(null);
    }

    public Boolean getBoolean(String key, boolean defaultValue) {
        return getBoolean(key).orElse(defaultValue);
    }

    //-------------------------------------------------------------
    public Optional<Double> getDouble(String key) {
        return get(key).map(Double::parseDouble);
    }

    public Double getDoubleOrNull(String key) {
        return getDouble(key).orElse(null);
    }

    public Double getDouble(String key, double defaultValue) {
        return getDouble(key).orElse(defaultValue);
    }

    //-------------------------------------------------------------
    public Map<String, String> getPrefix(String key) {
        Map<String, String> map = new HashMap<>();
        if (null == key) {
            return map;
        }
        data.forEach((k, v) -> {
            if (k.startsWith(key)) {
                map.put(k, v);
            }
        });
        return map;
    }

    public boolean hasKey(String key) {
        if (null == key) {
            return false;
        }
        return data.containsKey(key);
    }

    public boolean hasValue(String value) {
        if (null == value) {
            return false;
        }
        return data.containsValue(value);
    }

    public Map<String, String> data() {
        return data;
    }

    public int size() {
        return data.size();
    }

    public boolean isEmpty() {
        return data.isEmpty();
    }

}