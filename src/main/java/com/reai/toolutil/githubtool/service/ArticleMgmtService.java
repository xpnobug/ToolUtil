package com.reai.toolutil.githubtool.service;

/**
 * @author 86136
 */
public interface ArticleMgmtService {

    /**
     * 同步拉取 GitHub 仓库
     *
     * @param githubId
     */
    void refreshGitHub(String githubId);
}
