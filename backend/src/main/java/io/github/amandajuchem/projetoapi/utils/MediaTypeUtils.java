package io.github.amandajuchem.projetoapi.utils;

import jakarta.servlet.ServletContext;
import org.springframework.http.MediaType;

public class MediaTypeUtils {

    public static MediaType getMediaTypeForFileName(ServletContext servletContext, String fileName) {

        final var mineType = servletContext.getMimeType(fileName);

        try {
            return MediaType.parseMediaType(mineType);
        } catch (Exception e) {
            return MediaType.APPLICATION_OCTET_STREAM;
        }
    }
}