package cd.project.client.ui;

import java.io.File;
import java.net.MalformedURLException;

public class Styles {
    private static String path = getStylesPath();

    private static String getStylesPath() {
        String projectDir = System.getProperty("user.dir");
        String cssFilePath = projectDir + "/client/src/main/resources/cd/project/client/css/styles.css";
        File cssFile = new File(cssFilePath);
        try {
            return cssFile.toURI().toURL().toString();
        } catch (MalformedURLException e) {
            return null;
        }
    }

    public static String getPath() {
        return path;
    }
}
