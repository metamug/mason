package com.metamug.mason.entity.request;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author richard937
 */
public class XmlBodyStrategy implements RequestBodyStrategy {

    @Override
    public Object getBodyObject(InputStream stream, Class clazz){
        XmlMapper mapper = new XmlMapper();
        try {
              return mapper.readValue(stream, clazz);
//            return jaxbUnmarshaller.unmarshal(new StreamSource(stream));
        } catch (IOException ex) {
            Logger.getLogger(XmlBodyStrategy.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
