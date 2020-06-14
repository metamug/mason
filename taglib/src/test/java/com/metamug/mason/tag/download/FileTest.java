/*
 * Copyright 2020 pc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.metamug.mason.tag.download;

import com.metamug.entity.Attachment;
import com.metamug.entity.Request;
import static com.metamug.mason.Router.MASON_REQUEST;
import com.metamug.mason.tag.ExecuteTagHandler;
import static com.metamug.mason.tag.RestTag.MASON_OUTPUT;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author pc
 */
@RunWith(MockitoJUnitRunner.class)
public class FileTest {

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;

    @Mock
    private Request masonRequest;
    
    @Mock
    private PageContext context;
    
    

    @InjectMocks
    ExecuteTagHandler executeTag = new ExecuteTagHandler();

    private LinkedHashMap<String, Object> masonResponse;
    private Enumeration<String> headerList;
    
    @Before
    public void setup() throws IOException {
        
        
        when(context.getRequest()).thenReturn(request);
        when(context.getResponse()).thenReturn(response);
         when(request.getAttribute(MASON_REQUEST)).thenReturn(masonRequest);
        when(request.getAttribute(MASON_REQUEST)).thenReturn(masonRequest);
        
        //needed to add output
        masonResponse = new LinkedHashMap<>();
        when(context.getAttribute(MASON_OUTPUT, PageContext.PAGE_SCOPE)).thenReturn(masonResponse);
        
        headerList = Collections.enumeration(new ArrayList<String>());
        when(request.getHeaderNames()).thenReturn(headerList);
        
        

    }

    @Test
    public void fileDownloadTest() throws JspException {
        when(masonRequest.getParameter("file")).thenReturn("pom.xml");
       
        executeTag.setVar("fileDownload");
        executeTag.setOutput(true);
        executeTag.setClassName("com.metamug.mason.tag.download.FileDownloadPlugin");
        assertEquals(Tag.EVAL_BODY_INCLUDE, executeTag.doStartTag());
        assertEquals(Tag.EVAL_PAGE, executeTag.doEndTag());
        
        Attachment attachment = (Attachment) masonResponse.get("fileDownload");
        String output = slurp(attachment.getStream(), 2048);
        assertEquals(9, output.length());
   
    }
    
     private static String slurp(final InputStream is, final int bufferSize) {
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        try (Reader in = new InputStreamReader(is, "UTF-8")) {
            for (;;) {
                int rsz = in.read(buffer, 0, buffer.length);
                if (rsz < 0) {
                    break;
                }
                out.append(buffer, 0, rsz);
            }
        } catch (UnsupportedEncodingException ex) {
            /* ... */
        } catch (IOException ex) {
            Logger.getLogger(FileTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return out.toString();
    }
}
