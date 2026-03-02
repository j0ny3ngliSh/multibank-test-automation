package com.multibank.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;

/**
 * Central configuration utility.
 *
 * Resolution order (highest to lowest priority):
 * 1. JVM system property (-Dkey=value)
 * 2. Environment variable (KEY)
 * 3. config.properties on the classpath
 */
public final class ConfigReader {

    private static final String DEFAULT_CONFIG_FILE = "config.properties";
    private static final Properties PROPERTIES = new Properties();

    static {
        loadFromClasspath(DEFAULT_CONFIG_FILE);
    }

    private ConfigReader() {
    }

    private static void loadFromClasspath(String fileName) {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        if (cl == null) {
            cl = ConfigReader.class.getClassLoader();
        }

        try (InputStream is = cl.getResourceAsStream(fileName)) {
            if (is != null) {
                PROPERTIES.load(is);
            }
        } catch (IOException e) {
            // Swallow here; callers can still rely on system/env values.
        }
    }

    /**
     * Get a configuration value by key.
     *
     * @param key configuration key
     * @return value if present, otherwise {@code null}
     */
    public static String get(String key) {
        return get(key, null);
    }

    /**
     * Get a configuration value by key with a default.
     *
     * @param key          configuration key
     * @param defaultValue default value if key is not found
     * @return resolved value or default
     */
    public static String get(String key, String defaultValue) {
        Objects.requireNonNull(key, "key must not be null");

        return Optional.ofNullable(System.getProperty(key))
                .or(() -> Optional.ofNullable(System.getenv(toEnvKey(key))))
                .or(() -> Optional.ofNullable(PROPERTIES.getProperty(key)))
                .orElse(defaultValue);
    }

    /**
     * Convenience method to get a boolean flag.
     */
    public static boolean getBoolean(String key, boolean defaultValue) {
        String value = get(key);
        if (value == null) {
            return defaultValue;
        }
        return "true".equalsIgnoreCase(value.trim()) || "1".equals(value.trim());
    }

    /**
     * Convenience method to get an integer value.
     */
    public static int getInt(String key, int defaultValue) {
        String value = get(key);
        if (value == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private static String toEnvKey(String key) {
        return key.toUpperCase().replace('.', '_');
    }
}

