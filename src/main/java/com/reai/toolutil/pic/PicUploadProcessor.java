package com.reai.toolutil.pic;

import com.reai.toolutil.githubtool.model.Option;
import com.reai.toolutil.pic.util.UploadUtil;
import com.reai.toolutil.githubtool.service.impl.OptionRepository;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.b3log.latke.ioc.Inject;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.servlet.HttpMethod;
import org.b3log.latke.servlet.RequestContext;
import org.b3log.latke.servlet.annotation.RequestProcessing;
import org.b3log.latke.servlet.annotation.RequestProcessor;

/**
 * 图床上传
 *
 * @author 86136*/
@RequestProcessor
public class PicUploadProcessor {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(PicUploadProcessor.class);

    /**
     * Option repository.
     */
    @Inject
    private OptionRepository optionRepository;

    /**
     * 自定义图床上传目录
     *
     * @param context RT
     */
    @RequestProcessing(value = "/pic/upload", method = {HttpMethod.POST})
    public void uploadPicture(final RequestContext context) {
        synchronized (this) {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setRepository(new File("temp/"));
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setHeaderEncoding("UTF-8");
            Map okPic = new HashMap();
            List<String> errFiles = new ArrayList<>();
            try {
                List<FileItem> itemList = upload.parseRequest(context.getRequest());
                for (FileItem item : itemList) {
                    String name = item.getName();
                    String config;
                    try {
                        config = optionRepository.get(Option.ID_C_TUCHUANG_CONFIG)
                            .optString(Option.OPTION_VALUE);
                    } catch (Exception e) {
                        config = "hacpai";
                    }
//                    final ServletContext servletContext = SoloServletListener.getServletContext();
                    final String assets = "/";
                    String path = "servletContext.getResource(assets).getPath()";
                    path = URLDecoder.decode(path);
                    LOGGER.info("Uploading image [temp=" + path + name + "]");
                    File file = new File(path + name);
                    item.write(file);
                    item.delete();
                    try {
                        String url = UploadUtil.upload(config, file);
                        if (url.isEmpty()) {
                            url = "接口调用错误，请检查偏好设置-自定义图床配置，清除浏览器缓存并重启服务端。";
                        }
                        okPic.put(name, url);
                    } catch (Exception e) {
                        errFiles.add(name);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Map map = new HashMap();
            map.put("succMap", okPic);
            map.put("errFiles", errFiles);
            context.renderJSON().renderData(map);
            context.renderCode(0);
            context.renderMsg("");
        }
    }

    /**
     * 检查本地图床是否符合规则
     *
     * @param context RT
     */
    @RequestProcessing(value = "/pic/local/check", method = {HttpMethod.GET})
    public void checkLocalImageBedAvailable(final RequestContext context) {
        String path = context.param("path");
        if (path == null || "undefined".equals(path)) {
            context.renderJSON().renderCode(500);
            context.renderJSON().renderMsg("测试失败！目录格式错误。");

            return;
        }
        File pathFile = new File(path);
        // 检查目录是否存在，如果不存在则创建
        if (!pathFile.exists()) {
            if (!pathFile.mkdirs()) {
                context.renderJSON().renderCode(500);
                context.renderJSON().renderMsg("创建目录失败！目录格式错误或没有权限。");

                return;
            }
        }
        // 判断目录是否可写
        try {
            File tmpFile = new File(pathFile.getAbsolutePath() + "/" + "temp_bolo_solo.tmp");
            tmpFile.createNewFile();
            tmpFile.delete();

            context.renderJSON().renderCode(200);
            context.renderJSON().renderMsg("测试成功 :)");

            return;
        } catch (Exception e) {
            context.renderJSON().renderCode(500);
            context.renderJSON().renderMsg("测试失败！目录没有写入权限。");

            return;
        }
    }

    /**
     * 获取本地图床存储位置的图片，并返回
     *
     * @param context RT
     */
    @RequestProcessing(value = "/image/{imageFilename}", method = HttpMethod.GET)
    public void getLocalImage(final RequestContext context) {
        try {
            final HttpServletResponse response = context.getResponse();
            String config = optionRepository.get(Option.ID_C_TUCHUANG_CONFIG)
                .optString(Option.OPTION_VALUE);
            String type = config.split("<<>>")[0];
            if ("local".equals(type)) {
                String userInputFilename = context.pathVar("imageFilename");
                userInputFilename = URLEncoder.encode(userInputFilename, "UTF-8");
                userInputFilename = userInputFilename.replaceAll("%", "");
                String filePath = config.split("<<>>")[1] + "/" + userInputFilename;
                FileInputStream fileInputStream = new FileInputStream(filePath);
                int fileSize = fileInputStream.available();
                byte[] data = new byte[fileSize];
                fileInputStream.read(data);
                fileInputStream.close();
                String contentType = Files.probeContentType(Paths.get(filePath));
                response.setContentType(contentType);
                OutputStream outputStream = response.getOutputStream();
                outputStream.write(data);
                outputStream.close();
            }
        } catch (Exception e) {
            context.sendError(HttpServletResponse.SC_NOT_FOUND);

            return;
        }
    }
}
