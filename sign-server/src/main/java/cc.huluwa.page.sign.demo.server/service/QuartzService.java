package cc.huluwa.page.sign.demo.server.service;

import org.quartz.SchedulerException;

import java.util.Date;

public interface QuartzService {

    /**
     * 添加调度任务
     * @param clazz
     * @param jobName
     * @param groupId
     * @param data
     * @throws SchedulerException
     */
    void addJob(Class clazz, String jobName, String groupId,String pcSignDTO, String data, Date startAt) throws SchedulerException;

    /**
     * 删除调度任务
     * @param jobName
     * @param groupId
     * @throws SchedulerException
     */
    void deleteJob(String jobName, String groupId)  throws SchedulerException;

    /**
     * 暂停调度任务
     * @param jobName
     * @param groupId
     */
    void stopJob(String jobName, String groupId) throws SchedulerException;

    /**
     * 恢复调度任务
     * @param jobName
     * @param groupId
     */
    void resumeJob(String jobName, String groupId) throws SchedulerException;
}
