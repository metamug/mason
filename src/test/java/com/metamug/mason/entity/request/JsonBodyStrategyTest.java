package com.metamug.mason.entity.request;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JsonBodyStrategyTest {

    @Mock
    private HttpServletRequest request;

    @Before
    public void setUp() {
        request = mock(HttpServletRequest.class);
        ServletContext context = mock(ServletContext.class);
        when(request.getServletContext()).thenReturn(context);
        when(context.getContextPath()).thenReturn("backend");
    }

    @Test
    public void jsonBodyUnmarshal() throws IOException {

            JsonBodyStrategy masonRequest = new JsonBodyStrategy(request);
            Object object = masonRequest.getBodyObject();

            Assert.assertNotNull(object);
    }

}
