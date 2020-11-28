package com.metamug.exec;

import com.metamug.entity.Response;
import com.metamug.entity.Result;

/**
 *
 * @author Kainix
 */
public interface ResultProcessable {

    /**
     * Performs operation on data received from SELECT statement
     *
     * @param queryResult
     * @return Response data
     * @throws java.lang.Exception
     */
    public Response process(Result queryResult) throws Exception;
}
