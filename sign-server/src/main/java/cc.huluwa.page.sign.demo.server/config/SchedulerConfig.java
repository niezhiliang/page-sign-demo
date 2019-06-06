package cc.huluwa.page.sign.demo.server.config;

import org.quartz.Scheduler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class SchedulerConfig {


    @Bean(name="SchedulerFactory")
    public SchedulerFactoryBean schedulerFactoryBean(){
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        return factory;
    }


    /**
     *
     *通过SchedulerFactoryBean获取Scheduler的实例
     */
    @Bean(name="Scheduler")
    @Primary
    public Scheduler scheduler() {
        return schedulerFactoryBean().getScheduler();
    }
}
