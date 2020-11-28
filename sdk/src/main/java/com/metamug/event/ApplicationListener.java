package com.metamug.event;

import com.metamug.entity.Application;

/**
 * The listener interface for receiving action events. The class that is interested in processing an action event implements this interface, and the object created with that class is registered with a
 * component, using the component's ApplicationListener method. When the action event occurs, that object's uploadPerformed method is invoked
 *
 * @author Kaisteel
 */
public interface ApplicationListener {

    /**
     * Call this method during the application startup
     *
     * @param application Application information
     * @return 
     * @throws java.lang.Exception
     */
    public void init(Application application) throws Exception;

    /**
    * Call this method during termination of the application
    */
    public void destroy(Application application) throws Exception;
}
