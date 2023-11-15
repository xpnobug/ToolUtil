package com.reai.toolutil.githubtool.service.impl;

import com.reai.toolutil.Reais;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.util.Stopwatchs;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 86136
 */
@Service
public class StartServiceImpl {

    @Autowired
    private ArticleMgmtServiceImpl articleMgmtService;
    @Autowired
    private ExportService exportService;

    /**
     * Cron线程池。
     */
    private static ScheduledExecutorService SCHEDULED_EXECUTOR_SERVICE = Executors.newScheduledThreadPool(
        1);
    /**
     * 日志.
     */
    private static final Logger LOGGER = Logger.getLogger(StartServiceImpl.class);

    /**
     * 开始处理。 该方法会在启动时延迟2秒后开始执行，然后每隔24小时执行一次。在执行过程中，会判断是否开启自动刷新GitHub，并且GitHub ID不为空， 如果满足条件，则调用
     * articleMgmtService.refreshGitHub 方法刷新GitHub。最后，无论任务是否成功执行，都会释放 Stopwatchs 资源。
     */
    public void start() {
        long delay = 2000;
        // 初始化计数器变量
        AtomicInteger executionCount = new AtomicInteger();
        // 使用ScheduledExecutorService.scheduleAtFixedRate方法，创建一个定时任务
        SCHEDULED_EXECUTOR_SERVICE.scheduleAtFixedRate(() -> {
            try {
                // 每次执行时计数器加1
                executionCount.getAndIncrement();
                // 输出执行次数
                System.out.println("第 " + executionCount + " 次执行");

                boolean enableAutoFlushGitHub;
                String myGitHubID;
                try {
                    enableAutoFlushGitHub = true;
                    myGitHubID = Reais.userId;
                } catch (NullPointerException | JSONException e) {
                    enableAutoFlushGitHub = false;
                    myGitHubID = "";
                }
                // 如果开启自动刷新GitHub，并且GitHub ID不为空，则调用articleMgmtService.refreshGitHub方法刷新GitHub
                if (enableAutoFlushGitHub) {
                    if (!myGitHubID.isEmpty()) {
                        articleMgmtService.refreshGitHub(myGitHubID);
                    }
                }
                exportService.exportGitHub(Reais.token);
            } catch (final Exception e) {
                LOGGER.log(Level.ERROR, "执行定时任务失败", e);
            } finally {
                Stopwatchs.release();
            }
        }, delay, 1000 * 60 * 60 * 24, TimeUnit.MILLISECONDS);
        delay += 2000;
    }

    /**
     * 结束
     */
    public void stop() {
        SCHEDULED_EXECUTOR_SERVICE.shutdown();
    }

    /**
     * 重启
     */
    public void restart() {
        stop();
        SCHEDULED_EXECUTOR_SERVICE = Executors.newScheduledThreadPool(1);
        start();
    }
}
