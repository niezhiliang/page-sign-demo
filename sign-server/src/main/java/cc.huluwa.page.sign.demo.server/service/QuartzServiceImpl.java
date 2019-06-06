package cc.huluwa.page.sign.demo.server.service;

import cc.huluwa.page.sign.demo.server.domain.PcSignDTO;
import org.quartz.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;


@Component
public class QuartzServiceImpl implements QuartzService {

    @Resource
    private Scheduler scheduler;

    public void addJob(Class clazz, String jobName, String groupId,String pcSignDTO,String data, Date startAt) throws SchedulerException {
        // 启动调度器
        scheduler.start();

        //构建job信息
        JobDetail jobDetail = JobBuilder.newJob(clazz)
                .withIdentity(jobName,groupId)
                .usingJobData("data",data)
                .usingJobData("pcSignDTO",pcSignDTO)
                .build();

        //创建触发器 只执行一次
        SimpleTrigger simpleTrigger =  (SimpleTrigger) TriggerBuilder.newTrigger()
                .withIdentity(jobName, groupId)
                .startAt(startAt)
                .forJob(jobName, groupId)
                .build();
        scheduler.scheduleJob(jobDetail, simpleTrigger);
    }

    public void deleteJob(String jobName,String groupId) throws SchedulerException {
        scheduler.pauseTrigger(TriggerKey.triggerKey(jobName, groupId));
        scheduler.unscheduleJob(TriggerKey.triggerKey(jobName, groupId));
        scheduler.deleteJob(JobKey.jobKey(jobName, groupId));

    }

    public void stopJob(String jobName, String groupId) throws SchedulerException {
        scheduler.pauseJob(JobKey.jobKey(jobName, groupId));

    }

    public void resumeJob(String jobName, String groupId) throws SchedulerException {
        scheduler.resumeJob(JobKey.jobKey(jobName, groupId));
    }
}
