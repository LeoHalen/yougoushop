package com.yougou.search.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 全局异常处理器
 *
 * @ProjectName: yougoushop
 * @Package: com.yougou.search.controller
 * @Description: java类作用描述
 * @Author: HALEN(lizhigang)
 * @CreateDate: 2018/4/16 16:12
 * <p>Copyright: Copyright (c) 2018</p>
 */
public class GlobalExceptionResolver implements HandlerExceptionResolver {

	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionResolver.class);


	public ModelAndView resolveException(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			Object handler, Exception e) {
		LOGGER.info("进入全局异常处理器...................");
		LOGGER.debug("测试handler的类型："+handler.getClass());
		//控制台打印异常
		e.printStackTrace();
		//向日志文件写入异常
		LOGGER.error("系统异常！",e);
		//发邮件使用jmail
		//发短信
		//展示错误页面
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("message","呜呜呜......你访问的页面丢失了！");
		modelAndView.setViewName("error/exception");
		return modelAndView;
	}
}
