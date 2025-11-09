package wtf.magma.audiosync.util;



import lombok.experimental.UtilityClass;

import java.io.InputStream;

@UtilityClass
public class ResourceUtil{

    public static InputStream getResourceAsStream(String fileName) {
        try {
            String location = "/assets/audiosync/fonts/"+ fileName;
            return ResourceUtil.class.getResourceAsStream(location);
        } catch (Exception e) {
            return null;
        }
    }
}
