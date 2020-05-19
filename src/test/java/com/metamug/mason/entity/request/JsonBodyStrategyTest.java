package com.metamug.mason.entity.request;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import javax.servlet.http.HttpServletRequest;
import java.io.*;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class JsonBodyStrategyTest {

    @Mock
    private HttpServletRequest request;

    @Before
    public void setUp() throws IOException {
        request = mock(HttpServletRequest.class);
        String json = "{\n" +
                "    \"customer\": {\n" +
                "      \"contact\": {\n" +
                "        \"phone\": \"+1 943 322 4292\",\n" +
                "        \"email\": \"john.doe@gmail.com\"\n" +
                "      },\n" +
                "      \"name\": \"John Doeyy.\",\n" +
                "      \"roll\": 555,\n" +
                "      \"id\": 8\n" +
                "    }\n" +
                "  }";

        Reader inputString = new StringReader(json);
        BufferedReader reader = new BufferedReader(inputString);

        when(request.getReader()).thenReturn(reader);

    }

    @Test
    public void jsonBodyUnmarshal() throws IOException {

            JsonBodyStrategy masonRequest = new JsonBodyStrategy(request);
            masonRequest.setClazz(Customer.class);
            Object object = masonRequest.getBodyObject();

            Customer customer = (Customer) object;

            String name = customer.getName();
            System.out.println(customer);
            Assert.assertEquals("John Doeyy.", name);
    }

}
