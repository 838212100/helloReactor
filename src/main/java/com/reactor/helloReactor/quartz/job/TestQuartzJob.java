package com.reactor.helloReactor.quartz.job;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

//@EnableScheduling
@Component
public class TestQuartzJob {
	
	@Scheduled(cron="0/5 * * * * ?")
    public void job(){
        System.out.println("每五秒执行一次");
	}

}
