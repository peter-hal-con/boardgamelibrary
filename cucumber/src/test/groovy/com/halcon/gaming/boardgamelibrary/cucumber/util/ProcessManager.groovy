package com.halcon.gaming.boardgamelibrary.cucumber.util

import java.util.concurrent.TimeUnit

import static org.awaitility.Awaitility.await

class ProcessManager {
    final String cmdline
    final List envp
    final File directory
    final Closure isRunning

    Process process
    StringBuilder sout
    StringBuilder serr

    ProcessManager(String cmdline, List envp, File directory, Closure isRunning) {
        this.cmdline = cmdline
        this.envp = envp
        this.directory = directory
        this.isRunning = isRunning
    }

    boolean start() {
        sout = new StringBuilder()
        serr = new StringBuilder()
        process = cmdline.execute(envp, directory)
        process.consumeProcessOutput(sout, serr)

        await().atMost(5, TimeUnit.MINUTES).until {
            if (!process.alive) {
                println('--- COMMAND FAILED ---')
                println(cmdline)
                if (process.isAlive()) {
                    println("Process was RUNNING")
                    process.waitForOrKill(10000)
                } else {
                    println("Process was TERMINATED")
                }
                println()
                println('Standard Output:')
                println(sout)
                println('Standard Error:')
                println(serr)
                println('----------------------')
            }
            return isRunning()
        }

        return true
    }

    private static terminate(ProcessHandle handle) {
        ProcessHandle[] children = handle.children().toArray()
        while (children.length > 0) {
            children.each { terminate(it) }
            children = handle.children().toArray()
        }
        if (handle.isAlive()) {
            handle.destroy()
        }
        try {
            await().atMost(5, TimeUnit.SECONDS).until { !handle.alive }
        } catch (Exception e) {
        }
        if (handle.isAlive()) {
            handle.destroyForcibly()
        }
        try {
            await().atMost(5, TimeUnit.SECONDS).until { !handle.alive }
        } catch (Exception e) {
        }
    }

    boolean stop() {
        terminate(process.toHandle())
    }
}