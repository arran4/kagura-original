package com.base2.example.war.rest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.base2.kagura.core.authentication.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AuthRestImplTest {

    @Mock
    private KaguraBean kaguraBean;

    @InjectMocks
    private AuthRestImpl authRest;

    @Test
    public void testAuthToken_whenUserIsNull_returnsNotOk() {
        when(kaguraBean.getUser()).thenReturn(null);

        String result = authRest.testAuthToken("dummyToken");

        assertEquals("Not OK", result);
    }

    @Test
    public void testAuthToken_whenUserIsNotNull_returnsOk() {
        User user = new User();
        when(kaguraBean.getUser()).thenReturn(user);

        String result = authRest.testAuthToken("dummyToken");

        assertEquals("Ok", result);
    }
}
