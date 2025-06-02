package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
   private static final Properties properties = new Properties();
   public static final String env = System.getProperty("env", "stg");

   static {
      try {
         FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
         properties.load(fis);
      } catch (IOException e) {
         throw new RuntimeException("Failed to load config.properties", e);
      }
   }

   public static String get(String key) {
      String fullKey = env + "." + key;
      String value = properties.getProperty(fullKey);
      if (value == null || value.isEmpty()) {
         throw new RuntimeException("Missing configuration for key: " + fullKey);
      }
      return value;
   }
}
