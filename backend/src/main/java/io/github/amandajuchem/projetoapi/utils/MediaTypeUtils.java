package io.github.amandajuchem.projetoapi.utils;

import jakarta.servlet.ServletContext;
import org.springframework.http.MediaType;

/**
 * The type Media type utils.
 */
public class MediaTypeUtils {

    /**
     * Gets media type for file name.
     *
     * @param servletContext the servlet context
     * @param fileName       the file name
     * @return the media type for file name
     */
    public static MediaType getMediaTypeForFileName(ServletContext servletContext, String fileName) {

        var mineType = servletContext.getMimeType(fileName);

        try {
            return MediaType.parseMediaType(mineType);
        } catch (Exception e) {
            return MediaType.APPLICATION_OCTET_STREAM;
        }
    }
}