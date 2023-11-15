package com.reai.toolutil.githubtool.service.impl;

import com.reai.toolutil.githubtool.model.Option;
import org.b3log.latke.ioc.Inject;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.springframework.stereotype.Service;

/**
 * 初始化服务类。
 *
 * @author 86136
 */
@Service
public class InitService {

    /**
     * 日志记录器。
     */
    private static final Logger LOGGER = Logger.getLogger(InitService.class);

    /**
     * Option仓库。
     */
    @Inject
    private OptionRepository optionRepository;

    /**
     * 初始化状态标志。
     */
    private static boolean inited;

    /**
     * 打印的初始化提示信息标志。
     */
    private static boolean printedInitMsg;

    /**
     * 确定是否已初始化。
     *
     * @return 如果已初始化则返回true，否则返回false
     */
    public boolean isInited() {
        if (inited) {
            return true;
        }
        try {
            inited = null != optionRepository.get(Option.ID_C_VERSION);
            if (!inited && !printedInitMsg) {
                LOGGER.log(Level.WARN, "尚未初始化，请在浏览器中打开以初始化");
                printedInitMsg = true;
            }
            return inited;
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "检查初始化失败", e);
            System.exit(-1);
            return false;
        }
    }
}