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
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;
import javax.sql.DataSource;

/**
 *
 * @author pc
 */
public class FileDownloadPlugin implements RequestProcessable {

    @Override
    public Response process(Request request, DataSource ds, Map<String, Object> args) throws Exception {

        InputStream is = new ByteArrayInputStream("test data".getBytes());
        Attachment attachment = new Attachment(is);
        attachment.setName(request.getParameter("file"));
        Response response = new Response(attachment);
        return response;

    }

}
