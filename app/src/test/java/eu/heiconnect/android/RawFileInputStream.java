package eu.heiconnect.android;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by louis on 01/03/15.
 */
public class RawFileInputStream {

    public InputStream get(String file) throws FileNotFoundException {
        String classesPath = getClass().getProtectionDomain().getCodeSource().getLocation().getFile();
        String[] pathElements = classesPath.split("/");
        String resPath = classesPath + "/../../../res/" + pathElements[pathElements.length - 1] + "/raw/";

        return new FileInputStream(resPath + file);
    }
}
