package io.github.frapples.javapinyin.utils;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 18-9-16
 */
public class FileUtils {
    public static List<URL> getResources(String path) throws IOException {
        Enumeration<URL> resources = FileUtils.class.getClassLoader().getResources(path);
        List<URL> urls = new ArrayList<URL>();
        while (resources.hasMoreElements()) {
            URL url = resources.nextElement();
            urls.add(url);
        }
        return urls;
    }

    public static URL getResource(String path) {
        return FileUtils.class.getClassLoader().getResource(path);
    }

    public static String getResourceContext(String path) throws IOException {
        return getResourceContext(path, "UTF-8");
    }

    public static String getResourceContext(String path, String charset) throws IOException {
        URL url = FileUtils.class.getClassLoader().getResource(path);
        if (url == null) {
            throw new FileNotFoundException("classpath:" + path);
        }
        return readAll(url.openStream(), charset);
    }

    public static String readAll(InputStream inputStream, String charset) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        return result.toString(charset);
    }


}
