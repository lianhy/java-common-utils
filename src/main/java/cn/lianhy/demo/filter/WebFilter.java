package cn.lianhy.demo.filter;

import cn.lianhy.demo.constant.OpenConstant;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;

@javax.servlet.annotation.WebFilter(filterName="webFilter",urlPatterns="/demo/*",dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD})
public class WebFilter extends HttpServlet implements Filter, OpenConstant {

    private static final long serialVersionUID = -8152284218069997373L;

    private ServletContext servletContext;

    @Override
    public void destroy() {
        // TODO Auto-generated method stub
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)res;

        String targetURL = request.getRequestURI().replace("//","/").substring(
                request.getContextPath().length());
        String decodeUrl = URLDecoder.decode(targetURL,"UTF-8");
        filterChain.doFilter(req, res);
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        servletContext=arg0.getServletContext();
        WebApplicationContext ctx= WebApplicationContextUtils.getWebApplicationContext(servletContext);
    }

}
