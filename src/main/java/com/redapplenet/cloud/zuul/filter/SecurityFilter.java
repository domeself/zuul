package com.redapplenet.cloud.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import javax.servlet.http.HttpServletRequest;

/**
 * @AUTHOR liuqn
 * @DATE 2018/12/5 11:01
 * @VERSION v1.0
 * @DESCRIPTION TODO
 */

public class SecurityFilter  extends ZuulFilter {
    Logger logger = LoggerFactory.getLogger(SecurityFilter.class);
    /**
     * filterType：过滤器类型，他决定过滤器在请求的哪个生命周期执行。
     * - pre：可以在请求被路由之前调用；
     * - routing：在路由请求是调用；
     * - post：在routing和error过滤器之后被调用；
     * - error：处理请求是发生错误是被调用
     *
     */
    @Override
    public String filterType() {

        return "pre";
    }

    /**
     * 过滤器的执行顺序，数值越小优先级越高
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 判断过滤器是否需要执行
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        logger.info("send{} request to {}",request.getMethod(),request.getRequestURI().toCharArray());
        String accessToken = request.getParameter("accessToken");
        if(accessToken==null||accessToken.length()==0){
            logger.warn("accessToken is empty");
            currentContext.setSendZuulResponse(false);
            currentContext.setResponseStatusCode(401);
            return null;
        }
        logger.info("accessToken {}" ,accessToken);
        return null;
    }
}
