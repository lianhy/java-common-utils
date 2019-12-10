package cn.lianhy.demo.servlet;

import cn.lianhy.demo.constant.DateConstant;
import cn.lianhy.demo.utils.DateExtendUtils;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Slf4j
@WebServlet(urlPatterns = "/UPLOAD")
public class FileUploadServlet extends HttpServlet {

    @Value("${server.cdn.path}")
    private String cdnPath;

    @Value("${server.cdn.tmp.path}")
    private String cdnPathTmp;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject jsonObject=new JSONObject();
        try {
            request.setCharacterEncoding("utf-8");
            if(!ServletFileUpload.isMultipartContent(request)){
                jsonObject.put("result","0");//文件为空
                response.getWriter().write(jsonObject.toJSONString());
                return;
            }

            DiskFileItemFactory factory=new DiskFileItemFactory();
            //大于10Kb 临时文件存储到临时文件中
            factory.setSizeThreshold(1024*10);
            //设置临时文件目录
            File cdnDirTmp=new File(cdnPathTmp);
            if(!cdnDirTmp.exists()){
                cdnDirTmp.mkdir();
            }
            File cdnDir=new File(cdnPath);
            if(!cdnDir.exists()){
                cdnDir.mkdir();
            }
            factory.setRepository(cdnDirTmp);

            ServletFileUpload servletFileUpload=new ServletFileUpload(factory);
            servletFileUpload.setFileSizeMax(1024*1024*2);//单文件大小
            servletFileUpload.setSizeMax(1024*1024*2*5);//总的大小限制

            Iterator<FileItem> iterator=servletFileUpload.parseRequest(request).iterator();

            List<String> listUrl=new ArrayList<>();
            while (iterator.hasNext()){
                FileItem item=iterator.next();
                if(!item.isFormField()){
                    String fileName=item.getName();
                    Random rand = new Random();
                    String ranName = DateExtendUtils.getInstance().getString(new Date(), DateConstant.PATTERN_NOSPACE_YMDHMS) + "_" + rand.nextInt(1000)+fileName.substring(fileName.indexOf("."));
                    File file=new File(cdnPath+ranName);
                    item.write(file);

                    listUrl.add(cdnPath+ranName);
                }
            }
            jsonObject.put("result","1");
            jsonObject.put("listUrl",listUrl);
            response.getWriter().write(jsonObject.toJSONString());

        } catch (Exception e) {
            log.error("upload fail error"+e);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public void init() throws ServletException {
        super.init();
    }
}
