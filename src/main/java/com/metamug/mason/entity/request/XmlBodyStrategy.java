package com.metamug.mason.entity.request;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author richard937
 */
public class XmlBodyStrategy implements RequestBodyStrategy {

    Unmarshaller jaxbUnmarshaller;

    public XmlBodyStrategy(Class clazz) throws JAXBException {

        JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
        jaxbUnmarshaller = jaxbContext.createUnmarshaller();

    }

    @Override
    public Object getBodyObject(InputStream stream){

        try {
            return jaxbUnmarshaller.unmarshal(new StreamSource(stream));
        } catch (JAXBException ex) {
            Logger.getLogger(XmlBodyStrategy.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

}
