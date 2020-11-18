package tss.sumdu.test.integration;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import tss.sumdu.UrlTesterApp;
import tss.sumdu.test.utils.TestHelper;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("rawtypes")
public class ServiceControllerWebServerTest {

    private static final  UrlTesterApp app = new UrlTesterApp();

    @BeforeAll
    static void initServer(){
        app.start(UrlTesterApp.PORT);
    }

    @Test
    public void testCreateValidService() {
        String body = TestHelper.jsonGenerator("/test/test", "200", "test");
        HttpResponse response = Unirest.post(UrlTesterApp.URL+"/")
                .header("Content-Type", "application/json")
                .body(body)
                .asEmpty();
        assertEquals(302, response.getStatus());
    }

    @Test
    public void testCreateMalformedJson() {
        String body = TestHelper.jsonGenerator("/test/test", "400", "test").replace('{','[');
        HttpResponse response = Unirest.post(UrlTesterApp.URL+"/")
                .header("Content-Type", "application/json")
                .body(body).asEmpty();
        assertEquals(400, response.getStatus());
    }

    @AfterAll
    static void  stopServer(){
        app.stop();
    }
}