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
package com.metamug.mason.tag;

import com.metamug.entity.Result;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import static javax.servlet.jsp.tagext.Tag.EVAL_PAGE;
import org.apache.taglibs.standard.tag.common.sql.ResultImpl;

/**
 * Execute tag arguments
 * @author pc
 */
public class ArgTagHandler extends NameValueTagHandler {

    @Override
    public int doEndTag() throws JspException {

        parent = (ExecuteTagHandler) getParent();
        if (parent == null) {
            throw new JspTagException("The arg tag must be bounded by execute tag");
        }

        if (value instanceof ResultImpl) {
            value = convert((ResultImpl) value);
        }

        parent.addParameter(name, this.value);

        return EVAL_PAGE;
    }

    /**
     * Convert JSP Result to Mason SQL Result Object
     *
     * @param ri
     * @return Result Object
     */
    private Result convert(ResultImpl ri) {
        return new Result(ri.getRows(), ri.getColumnNames(), ri.getRowCount());
    }

}
