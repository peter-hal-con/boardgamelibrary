package com.halcon.gaming.boardgamelibrary.cucumber

import com.halcon.gaming.boardgamelibrary.cucumber.util.ProcessManager
import io.cucumber.java.AfterAll
import io.cucumber.java.BeforeAll

public class ApplicationSetupTeardown {
    private static int responseCode(String path) {
        return new URL("http://localhost:8080${path}").openConnection().responseCode
    }

    private static ProcessManager processManager = new ProcessManager("java -Dgrails.env=test -jar build/server-0.1.jar", new File(".."), {
        try {
            int rc = responseCode("/")
            return rc == 200
        } catch (IOException e) {
            return false
        }
    })

    @BeforeAll
    static void startServer() {
        processManager.start()
    }

    @AfterAll
    static void stopServer() {
        processManager.stop()
    }
}