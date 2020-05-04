package com.springcloud.web.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 过滤器
 *
 * @author: 许集思
 * @date: 2020/5/2 10:50
 */
@Component
public class WebFilter extends ZuulFilter {
    /**
     * 在之前执行（pre)  后执行（post)
     *
     * @param
     * @return java.lang.String
     * @author: 许集思
     * @date: 2020/5/2 10:50
     **/
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 多个过滤器的执行顺序，数字越小，表示越先执行
     *
     * @param
     * @return int
     * @author: 许集思
     * @date: 2020/5/2 10:50
     **/
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 当前过滤器是否开启true表示开启
     *
     * @param
     * @return boolean
     * @author: 许集思
     * @date: 2020/5/2 10:51
     **/
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 过滤器内执行的操作  return 任何object的值表示继续执行
     * setsendzullRespponse(false)表示不再继续执行
     *
     * @param
     * @return java.lang.Object
     * @author: 许集思
     * @date: 2020/5/2 10:51
     **/
    @Override
    public Object run() throws ZuulException {

        System.out.println("经过web过滤器了");
        //得到request上下文
        RequestContext currentContext = RequestContext.getCurrentContext();
        //得到request域
        HttpServletRequest request = currentContext.getRequest();
        //得到头信息
        String header = request.getHeader("Authorization");

        //判断是否有头信息
        if (StringUtils.isNotEmpty(header)) {
            //头信息继续传下去，本来过网关是不传的
            currentContext.addZuulResponseHeader("Authorization", header);
        }
        return null;
    }
}
