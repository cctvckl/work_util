package com.ckl.controller;

import com.ckl.filter.CacheRequestFilter;
import com.ckl.utils.BaseWebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * desc:
 *
 * @author : caokunliang
 * creat_date: 2018/11/13 0013
 * creat_time: 13:46
 **/
@RestController
public class TestController {
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);


    @RequestMapping("/testGet.do")
    public String responseBody() {
        return "testGet";
    }


    @RequestMapping(method = RequestMethod.POST, value = "testPost.do")
    public String testPost(HttpServletRequest request, HttpServletResponse response){
        logger.info("request:{}",request.getClass().getName());
        response.setContentType("application/json");
        String body1 = BaseWebUtils.getParameters(request);
        logger.info("the first time read post body:{}",body1);

        String body2 = BaseWebUtils.getParameters(request);
        logger.info("the second time read post body:{}",body2);

        return body2;
    }
}
