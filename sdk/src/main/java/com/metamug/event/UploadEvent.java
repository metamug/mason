package com.metamug.event;

import com.metamug.entity.Request;
import java.io.File;

/**
 * Event for multipart/form-data file upload. The event is passed to UploadListener object 
 * that subscribes to receive such events using the UploadListener method.
 *
 * @author Kaisteel
 */
public class UploadEvent {

    private final File uploadedFile;
    private final String fileName;
    private final Request request;

    /**
     *
     * @param uploadedFile uploaded <code>File</code> object
     * @param fileName Name of uploaded <code>File</code>
     * @param request Request object
     */
    public UploadEvent(File uploadedFile, String fileName, Request request) {
        this.uploadedFile = uploadedFile;
        this.fileName = fileName;
        this.request = request;
    }

    /**
     *
     * @return Instance of uploaded File.
     */
    public File getUploadedFile() {
        return uploadedFile;
    }

    /**
     *
     * @return Name of uploaded File.
     */
    public String getFileName() {
        return fileName;
    }

    /**
     *
     * @return Get the target request for the upload event.
     */
    public Request getTarget() {
        return this.request;
    }

}
