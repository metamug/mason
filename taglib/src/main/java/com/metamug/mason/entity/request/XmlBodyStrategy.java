package com.metamug.mason.entity.request;

import org.eclipse.persistence.jaxb.JAXBContextFactory;
import org.eclipse.persistence.jaxb.JAXBContextProperties;

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
        try {
            Map<String, Object> properties = new HashMap<>();
            final JAXBContext jaxbContext =
                    JAXBContextFactory.createContext(new Class<?>[]{clazz}, properties);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            return unmarshaller.unmarshal(new StreamSource(stream), clazz).getValue();
        } catch (JAXBException ex) {
            Logger.getLogger(XmlBodyStrategy.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
