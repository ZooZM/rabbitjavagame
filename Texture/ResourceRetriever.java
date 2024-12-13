//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package Texture;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ResourceRetriever {
    public ResourceRetriever() {
    }

    public static URL getResource(String filename) throws IOException {
        URL url = ClassLoader.getSystemResource(filename);
        return url == null ? new URL("file", "localhost", filename) : url;
    }

    public static InputStream getResourceAsStream(String filename) throws IOException {
        InputStream stream = ClassLoader.getSystemResourceAsStream(filename);
        return (InputStream)(stream == null ? new FileInputStream(filename) : stream);
    }
}
