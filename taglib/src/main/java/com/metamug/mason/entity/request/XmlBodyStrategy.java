package com.metamug.mason.entity.request;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author richard937
 */
public class XmlBodyStrategy implements RequestBodyStrategy {

    @Override
    public Object getBodyObject(InputStream stream, Class clazz) {
//        XmlMapper mapper = new XmlMapper();
        try {
//              return mapper.readValue(stream, clazz);
            Map<String, Object> properties = new HashMap<String, Object>(1);
            JAXBContext jc = JAXBContext.newInstance(new Class[]{clazz}, properties);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            return unmarshaller.unmarshal(new StreamSource(stream));
        } catch (JAXBException ex) {
            Logger.getLogger(XmlBodyStrategy.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
