package org.oauth2.resource.server.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.oauth2.resource.server.ResourceServerApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceServerApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class ResourceServerIntegrationTest {

    @Test
    public void whenLoadApplication_thenSuccess() {

    }
}
