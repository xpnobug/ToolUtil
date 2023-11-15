package com.reai.toolutil.githubtool.util;

import com.reai.toolutil.githubtool.service.impl.StartServiceImpl;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.b3log.latke.ioc.BeanManager;
import org.b3log.latke.servlet.annotation.RequestProcessor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author 86136
 */
@RequestProcessor
public class UtilTest {

    /**
     * Bean manager.
     */
    private static BeanManager beanManager;

    @Autowired
    static
    StartServiceImpl startServiceImpl;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        JSONArray gitHubRepos = GitHubs.getGitHubRepos("xpnobug");
//        System.out.println("项目列表");
//        for (int i = 0; i < gitHubRepos.length(); i++) {
//            JSONObject compatibleObject = new JSONObject();
//            JSONObject resultObject = gitHubRepos.optJSONObject(i);
//            compatibleObject.put("name",resultObject.optString("githubrepoFullName"));
//            compatibleObject.put("url",resultObject.optString("githubrepoHTMLURL"));
//            System.out.println(compatibleObject.get("name") +"-"+compatibleObject.get("url"));
//        }

        List<JSONArray> gitHubRepoInfo = Collections.singletonList(
            GitHubs.getGitHubFileContent("xpnobug/blog",
                "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", "source/_posts"));

    }
}
