package com.metamug.mason.entity.request;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author richard937
 */
public class XmlBodyStrategy implements RequestBodyStrategy {

    @Override
    public Object getBodyObject(InputStream stream, Class clazz) {
        try {
            JAXBContext jc = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            return unmarshaller.unmarshal(new StreamSource(stream), clazz).getValue();
        } catch (JAXBException ex) {
            Logger.getLogger(XmlBodyStrategy.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
