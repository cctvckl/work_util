/**
 *
 */
package com.ckl.filter;

import com.ckl.utils.BaseWebUtils;
import com.ckl.utils.MultiReadHttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Web流多次读写过滤器
 *
 * 拦截所有请求，主要是针对第三方提交过来的请求.
 * 为什么要做成可多次读写的流，因为可以在aop层打印日志。
 * 但是不影响controller层继续读取该流
 *
 * 该filter的原理：https://stackoverflow.com/questions/10210645/http-servlet-request-lose-params-from-post-body-after-read-it-once/17129256#17129256
 * @author ckl
 */
@Order(1)
@WebFilter(filterName = "cacheRequestFilter", urlPatterns = "*.do")
public class CacheRequestFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(CacheRequestFilter.class);


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // TODO Auto-generated method stub

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        logger.info("request uri:{}",httpServletRequest.getRequestURI());

        if (BaseWebUtils.isFormPost(httpServletRequest)){
            httpServletRequest = new MultiReadHttpServletRequest(httpServletRequest);

            String parameters = BaseWebUtils.getParameters(httpServletRequest);
            logger.info("CacheRequestFilter receive post req. body is {}", parameters);
        }else if (isPost(httpServletRequest)){
            //文件上传请求，没必要缓存请求
            if (request.getContentType().contains(MediaType.MULTIPART_FORM_DATA_VALUE)){

            }else {
                httpServletRequest = new MultiReadHttpServletRequest(httpServletRequest);

                String parameters = BaseWebUtils.getParameters(httpServletRequest);
                logger.info("CacheRequestFilter receive post req. body is {}", parameters);
            }
        }


        chain.doFilter(httpServletRequest, response);
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

    public static boolean isPost(HttpServletRequest request) {
        return  HttpMethod.POST.matches(request.getMethod());
    }
}