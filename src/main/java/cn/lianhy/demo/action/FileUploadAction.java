package cn.lianhy.demo.action;

import cn.lianhy.demo.constant.DateConstant;
import cn.lianhy.demo.utils.DateExtendUtils;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Slf4j
@RequestMapping(value = "file")
@Controller
public class FileUploadAction {

    @Value("${server.cdn.path}")
    private String cdnPath;

    @Value("${server.cdn.tmp.path}")
    private String cdnPathTmp;

    @PostMapping(value = "/upload.do")
    @ResponseBody
    public void upload(HttpServletRequest request, HttpServletResponse response
            ,@RequestParam(value = "upload", required = false) MultipartFile file)throws ServletException, IOException {
        JSONObject jsonObject=new JSONObject();
        String compressFlag=request.getParameter("compressFlag");
        try{
            request.setCharacterEncoding("utf-8");
            if(file.isEmpty()){
                jsonObject.put("result","0");//文件为空
                response.getWriter().write(jsonObject.toJSONString());
                return;
            }

            if(file.getSize() > 1024*1024*10){
                jsonObject.put("result","2");//文件过大
                response.getWriter().write(jsonObject.toJSONString());
                return;
            }

            String fileName=file.getOriginalFilename();
            Random rand = new Random();
            String ranName = DateExtendUtils.getInstance().getString(new Date(), DateConstant.PATTERN_NOSPACE_YMDHMS) + "_" + rand.nextInt(1000)+fileName.substring(fileName.indexOf("."));
            if(StringUtils.equals("1",compressFlag)){
                Thumbnails.of(file.getInputStream()).scale(1f).outputQuality(0.5f).toFile(cdnPath+ranName);
            }else{
                file.transferTo(new File(cdnPath+ranName));
            }
            jsonObject.put("result","1");
            jsonObject.put("url",cdnPath+ranName);
            response.getWriter().write(jsonObject.toJSONString());
        }catch (Exception e){
            log.error("upload fail error"+e);
        }

    }
}
