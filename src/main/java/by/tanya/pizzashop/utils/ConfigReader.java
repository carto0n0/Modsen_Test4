package by.tanya.pizzashop.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static final Properties props = new Properties();
    private static boolean isLoaded = false;

    private ConfigReader() {}

    public static synchronized void load() {
        if (!isLoaded) {
            try (InputStream input = ConfigReader
                    .class
                    .getClassLoader()
                    .getResourceAsStream(
                            "config.properties"
                    )) {
                if (input == null) {
                    throw new RuntimeException("Файл config.properties не найден в resources!");
                }
                props.load(input);
                isLoaded = true;
            } catch (IOException e) {
                throw new RuntimeException("Ошибка при чтении config.properties", e);
            }
        }
    }

    public static String get(String key) {
        if (!isLoaded) load();
        return props.getProperty(key);
    }
}
