package com.halcon.gaming.boardgamelibrary.cucumber

import com.halcon.gaming.boardgamelibrary.cucumber.util.CachingProxyHttpServer
import com.halcon.gaming.boardgamelibrary.cucumber.util.ProcessManager
import io.cucumber.java.AfterAll
import io.cucumber.java.Before
import io.cucumber.java.BeforeAll

import static org.junit.jupiter.api.Assertions.assertEquals

class ApplicationSetupTeardown {
    private static int responseCode(String path) {
        return new URL("http://localhost:8080${path}").openConnection().responseCode
    }

    private static ProcessManager processManager = new ProcessManager("java -Dgrails.env=test -jar build/server-0.1.jar", ['BGG_BASE_URL=http://localhost:8081'], new File(".."), {
        try {
            int rc = responseCode("/")
            return rc == 200
        } catch (IOException e) {
            return false
        }
    })

    private static CachingProxyHttpServer bggCachingProxy = new CachingProxyHttpServer("https://boardgamegeek.com", 8081)

    @BeforeAll
    static void startServer() {
        processManager.start()
    }

    @AfterAll
    static void stopServer() {
        processManager.stop()
        bggCachingProxy.close()
    }

    @Before
    void resetServer() {
        assertEquals(200, responseCode("/testOnly/reset"))
    }
}
