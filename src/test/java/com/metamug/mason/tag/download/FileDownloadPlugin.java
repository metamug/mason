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
import com.metamug.entity.Response;
import com.metamug.exec.RequestProcessable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 *
 * @author pc
 */
public class FileDownloadPlugin implements RequestProcessable {

    @Override
    public Response process(Request request, DataSource ds, Map<String, Object> args) throws Exception {

        try (SFTP sftp = new SFTP((String) args.get("host"),
                (String) args.get("user"), (String) args.get("password"), (String) args.get("dir"))) {
            InputStream is = sftp.download(request.getParameter("file"));
            Attachment attachment = new Attachment(is);
            attachment.setName(request.getParameter("file"));
            Response response = new Response(attachment);
            return response;
        }
    }
    
    

}