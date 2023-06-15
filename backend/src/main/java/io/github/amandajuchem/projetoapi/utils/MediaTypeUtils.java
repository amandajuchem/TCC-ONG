package io.github.amandajuchem.projetoapi.utils;

import jakarta.servlet.ServletContext;
import org.springframework.http.MediaType;

/**
 * Utility class for working with media types.
 */
public class MediaTypeUtils {

    /**
     * Retrieves the media type (MIME type) for a given filename.
     *
     * @param servletContext the ServletContext object
     * @param fileName       the filename for which to determine the media type
     * @return the MediaType object representing the media type of the file
     */
    public static MediaType getMediaTypeForFileName(ServletContext servletContext, String fileName) {

        final var mineType = servletContext.getMimeType(fileName);

        try {
            return MediaType.parseMediaType(mineType);
        } catch (Exception e) {
            return MediaType.APPLICATION_OCTET_STREAM;
        }
    }
}