package com.reai.toolutil.githubtool.controller;

import com.reai.toolutil.githubtool.service.impl.ArticleMgmtServiceImpl;
import com.reai.toolutil.githubtool.service.impl.StartServiceImpl;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 86136
 */
@RestController
@RequestMapping("/tool")
public class TestController {

    @Autowired
    private ArticleMgmtServiceImpl articleMgmtService;

    @Autowired
    private StartServiceImpl startService;
    @GetMapping("/startGithubBf")
    public JSONObject updateGithub(String githubId,String githubPAT){
        startService.start();
        return new JSONObject();
    }
}
