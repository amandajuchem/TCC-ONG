package io.github.amandajuchem.projetoapi.utils;

import io.github.amandajuchem.projetoapi.exceptions.OperationFailureException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for file operations.
 */
public class FileUtils {

    public static final Map<String, MultipartFile> FILES = new HashMap<>();

    public static final String DOCUMENTS_DIRECTORY = File.separator + "data" + File.separator + "files" + File.separator + "documents";

    public static final String IMAGES_DIRECTORY = File.separator + "data" + File.separator + "files" + File.separator + "images";

    /**
     * Finds a file by its filename and path.
     *
     * @param filename the name of the file to find.
     * @param path     the path where the file is located.
     * @return the File object representing the found file.
     * @throws FileNotFoundException if the file is not found.
     */
    public static File find(String filename, String path) throws FileNotFoundException {

        final var file = new File(System.getProperty("user.dir") + path + File.separator + filename);

        if (!file.exists()) {
            throw new FileNotFoundException(MessageUtils.FILE_NOT_FOUND);
        }

        return file;
    }

    /**
     * Saves a MultipartFile to the specified path.
     *
     * @param file the MultipartFile to save.
     * @param path the path to save the file to.
     * @return the saved File object.
     * @throws IOException if an I/O error occurs.
     */
    public static File save(MultipartFile file, String path) throws IOException {

        if (checkPathDestination(path)) {

            final var strFileName = file.getOriginalFilename().replace(".", " ").split(" ");
            final var extension = strFileName[strFileName.length - 1];
            final var filename = System.currentTimeMillis() + "." + extension;
            final var filePath = Paths.get(System.getProperty("user.dir") + path, filename);

            Files.write(filePath, file.getBytes());

            return find(filename, path);
        }

        throw new OperationFailureException("Diretório não encontrado!");
    }

    /**
     * Saves a MultipartFile with a specified filename to the specified path.
     *
     * @param filename the desired filename for the saved file.
     * @param file     the MultipartFile to save.
     * @param path     the path to save the file to.
     * @return the saved File object.
     * @throws IOException if an I/O error occurs.
     */
    public static File save(String filename, MultipartFile file, String path) throws IOException {

        if (checkPathDestination(path)) {

            final var filePath = Paths.get(System.getProperty("user.dir") + path, filename);
            Files.write(filePath, file.getBytes());

            return find(filename, path);
        }

        throw new OperationFailureException("Diretório não encontrado!");
    }

    /**
     * Deletes a file at the specified path.
     *
     * @param filename the name of the file to delete.
     * @param path     the path where the file is located.
     * @return true if the file is deleted successfully, false otherwise.
     */
    public static boolean delete(String filename, String path) {

        final var file = new File(System.getProperty("user.dir") + path + "/" + filename);

        if (file.exists() && file.isFile()) {
            return file.delete();
        }

        return true;
    }

    /**
     * Gets the file extension of the provided object.
     *
     * @param object the object (File or MultipartFile) to get the extension from.
     * @return the file extension.
     * @throws FileNotFoundException     if the file is not found.
     * @throws OperationFailureException if the object is not an instance of File or MultipartFile.
     */
    public static String getExtension(Object object) throws FileNotFoundException, OperationFailureException {

        if (object instanceof File) {

            final var file = ((File) object);

            if (!file.exists()) {
                throw new FileNotFoundException("Arquivo não encontrado!");
            }

            return file.getName().replace(".", " ").split(" ")[1];
        }

        if (object instanceof MultipartFile) {
            final var file = ((MultipartFile) object);
            final var fileName = file.getOriginalFilename().replace(".", "").split(" ");
            return fileName[fileName.length - 1];
        }

        throw new OperationFailureException(MessageUtils.OPERATION_FAILURE);
    }

    /**
     * Checks if the destination path exists and creates it if it doesn't.
     *
     * @param path the path to check and create.
     * @return true if the path exists or is created successfully, false otherwise.
     */
    public static boolean checkPathDestination(String path) {

        final var directory = new File(System.getProperty("user.dir") + path);

        if (!directory.exists()) {
            return directory.mkdir();
        }

        return true;
    }
}