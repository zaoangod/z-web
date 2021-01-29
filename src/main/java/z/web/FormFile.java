package z.web;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * HTTP multipart/form-data Request
 */
public class FormFile {

    /**
     * Upload file field, e.g: "file", "img"
     */
    private String name;

    /**
     * Upload file name, e.g: "hello.png"
     */
    private String fileName;

    /**
     * File temp path
     */
    private String path;

    /**
     * File Content Type
     */
    private String contentType;

    /**
     * File size, unit: byte
     */
    private long length;
    /**
     * File
     */
    private File file;

    public String name() {
        return name;
    }

    public FormFile name(String name) {
        this.name = name;
        return this;
    }

    public String fileName() {
        return fileName;
    }

    public FormFile fileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public String path() {
        return path;
    }

    public FormFile path(String path) {
        this.path = path;
        return this;
    }

    public String contentType() {
        return contentType;
    }

    public FormFile contentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public Long length() {
        return length;
    }

    public FormFile length(long length) {
        this.length = length;
        return this;
    }

    public File file() {
        return file;
    }

    public FormFile file(File file) {
        this.file = file;
        return this;
    }

    //----------------------------------------------------------------
    public String extName() {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    public void moveTo(File newFile) throws IOException {
        this.moveTo(Paths.get(newFile.getPath()));
    }

    public void moveTo(Path newFile) throws IOException {
        Files.move(Paths.get(file.getPath()), newFile, StandardCopyOption.REPLACE_EXISTING);
    }

    public byte[] data() {
        byte[] content;
        try {
            content = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return content;
    }
}