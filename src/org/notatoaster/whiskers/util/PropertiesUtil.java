package org.notatoaster.whiskers.util;


import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class PropertiesUtil {

    public static Properties loadPropertiesFile(Path propertiesFileName) {
        Properties props = new Properties();
        loadPropertiesFile(propertiesFileName, props);
        return props;
    }

    public static void loadPropertiesFile(Path propertiesFileName, Properties props) {
        if(Files.exists(propertiesFileName)) {
            BufferedReader reader = null;
            try {
                reader = Files.newBufferedReader(propertiesFileName, Charset.forName("UTF-8"));
                props.load(reader);
            } catch (Exception ex) {
                if(reader!=null)
                {
                    try {
                        reader.close();
                    } catch (IOException ignored) {

                    }
                }
            }
        }
    }
}
