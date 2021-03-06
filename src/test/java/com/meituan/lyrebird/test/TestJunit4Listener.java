package com.meituan.lyrebird.test;

import com.google.gson.Gson;
import com.meituan.lyrebird.client.MockData;
import com.meituan.lyrebird.client.api.*;
import com.meituan.lyrebird.client.listeners.*;
import com.meituan.lyrebird.client.exceptions.LyrebirdClientException;
import okhttp3.mockwebserver.*;
import org.junit.*;
import org.junit.runner.RunWith;

import java.io.IOException;

@RunWith(Junit4Runner.class)
public class TestJunit4Listener {
    private static MockWebServer mockServer;
    private static Gson gson;

    @BeforeClass
    public static void setup() throws IOException {
        gson = new Gson();
        mockServer = new MockWebServer();
        mockServer.start(9090);
        makeSuccessResponse();
    }

    @AfterClass
    public static void teardown() throws IOException {
        if (mockServer!=null) {
            mockServer.close();
            mockServer = null;
        }
    }

    private static void makeSuccessResponse(){
        BaseResponse resp = new BaseResponse();
        resp.setCode(1000);
        resp.setMessage("success");
        mockServer.enqueue(new MockResponse().setBody(gson.toJson(resp)));
    }

    @Test
    @MockData(groupID = "89e0426c-9cf9-454a-bbe0-94246fc23b04", groupName = "首页")
    public void testActivate() throws InterruptedException, LyrebirdClientException {
        RecordedRequest req = mockServer.takeRequest();
        Assert.assertEquals("request path not match", "/api/mock/89e0426c-9cf9-454a-bbe0-94246fc23b04/activate", req.getPath());
    }
}