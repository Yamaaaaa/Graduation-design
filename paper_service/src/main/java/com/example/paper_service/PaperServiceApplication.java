package com.example.paper_service;

import com.example.paper_service.Service.PaperService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class PaperServiceApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(PaperServiceApplication.class, args);
        PaperService paperService = context.getBean(PaperService.class);

        ScheduledExecutorService updateHotPaperService = Executors.newSingleThreadScheduledExecutor();
        updateHotPaperService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                paperService.updatePaperRecentBrowseNum();
            }
        }, 0, 1, TimeUnit.DAYS);
    }

}
