package com.metamug.event;

import com.metamug.entity.Response;
import javax.sql.DataSource;

/**
 * The listener interface for receiving action events. The class that is interested in processing an action event implements this interface, and the object created with that class is registered with a
 * component, using the component's UploadListener method. When the action event occurs, that object's uploadPerformed method is invoked
 *
 * @author Kaisteel
 */
public interface UploadListener {

    /**
     * Callback method for File upload event. File is uploaded with POST request on backend.
     *
     * @param event
     * @param dataSource DataSource object to connect with Database
     * @return Returned object will be converted to JSON/XML depending on accept header supplied in the multipart(upload) request
     * @throws java.lang.Exception
     */
    public Response uploadPerformed(UploadEvent event, DataSource dataSource) throws Exception;
}
