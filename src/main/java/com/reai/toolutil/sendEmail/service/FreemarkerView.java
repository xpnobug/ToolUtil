package com.reai.toolutil.sendEmail.service;

import com.reai.toolutil.sendEmail.config.Itoo;
import freemarker.template.Configuration;
import freemarker.template.Template;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.HashMap;
import java.util.Map;


import freemarker.template.TemplateException;
import org.springframework.stereotype.Service;

/**
 * @author 86136
 */
@Service
public class FreemarkerView {
    public String createHtml() throws Exception {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String time = format.format(date);
        Map<String, Object> root = new HashMap<>();
        Itoo itooList = new Itoo(
            "自动备份通知",
            time,
            "欢迎使用",
            "",
            "https://blog.reaicc.com",
            "https://blog.reaicc.com/img/Cat.svg",
            "",
            " ",
            "",
            "2877406366@qq.com"
        );
        root.put("itooList", itooList);
        this.fprint("index.ftl", root,"Ares.html");
        return this.processTemplateToString("index.ftl", root);
    }


    /**
     * 获取模板信息
     * @param name 模板名
     * @return
     */
    public Template getTemplate(String name){
        //通过freemarkerd COnfiguration读取相应的ftl
        Configuration cfg = new Configuration();
        //设定去哪里读取相应的ftl模板文件，指定模板路径
        cfg.setClassForTemplateLoading(this.getClass(), "/email/ftl");
        try {
            //在模板文件目录中找到名称为name的文件
            Template template =  cfg.getTemplate(name);
            return template;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String processTemplateToString(String name, Map<String, Object> root) throws TemplateException, IOException {
        Template template = this.getTemplate(name);
        StringWriter stringWriter = new StringWriter();
        template.process(root, stringWriter);
        return stringWriter.toString();
    }



    /**
     * 输出到控制台
     * @param name
     * @param root
     * @throws TemplateException
     * @throws IOException
     */
    public void print(String name,Map<String,Object> root) throws TemplateException, IOException {
        //通过Template可以将模板文件输出到相应的流
        Template template = this.getTemplate(name);
        template.process(root, new PrintWriter(System.out));
    }
    /**
     * 输出到文件中
     * @param name
     * @param root
     * @throws TemplateException
     * @throws IOException
     */
    public void fprint(String name,Map<String,Object> root,String outFile)  {
        FileWriter out=null;
        try {
            out = new FileWriter(new File("E:/"+outFile));
            //获取模板
            Template template = this.getTemplate(name);
            //设置模板编码
            template.setEncoding("utf-8");
            try {
                //输出
                template.process(root, out);
            } catch (TemplateException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();

        }finally{
            try {
                if (out!=null) {
                    out.close();
                }
            } catch (Exception e2) {
            }
        }
    }
}

