package com.genzverse.thread;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ThreadExecutorProgram {

    @Scheduled(fixedRate = 60000)
    public void execute() {
        System.out.println("Executing every minute...");
    }
}
