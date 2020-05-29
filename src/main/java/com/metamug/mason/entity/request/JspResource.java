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
package com.metamug.mason.entity.request;

import com.metamug.mason.Router;
import java.io.File;
import javax.servlet.http.HttpServletRequest;

/**
 * Version of JSP
 * @author pc
 */
public class JspResource {
    
    private HttpServletRequest request;

    public JspResource(HttpServletRequest request) {
        this.request = request;
    }
    
    protected  boolean resourceExists(String resourcePath, float version) {
		String jspPath = Router.RESOURCES_FOLDER + "v" + version + resourcePath + Router.JSP_EXTN;
                return new File(request.getServletContext().getRealPath(jspPath)).exists();
    }
}
