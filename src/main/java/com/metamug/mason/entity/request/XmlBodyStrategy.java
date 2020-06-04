package com.metamug.mason.entity.request;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author richard937
 */
public class XmlBodyStrategy extends RequestBodyStrategy {

    private HttpServletRequest request;

    /**
     *
     * @param request
     */
    public XmlBodyStrategy(HttpServletRequest request) {
        super(request);
        this.request = request;
    }

    @Override
    public Object getBodyObject() throws IOException {

        JAXBContext jaxbContext;
        Object object = null;
        try{

            jaxbContext = JAXBContext.newInstance(clazz);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            object = jaxbUnmarshaller.unmarshal(new InputStreamReader(request.getInputStream()));

        }catch (JAXBException ex) {
            Logger.getLogger(JsonBodyStrategy.class.getName()).log(Level.SEVERE, "Json Body Strategy :{0}", ex.getMessage());
        }

        return object;
    }
}
