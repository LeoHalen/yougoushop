package com.yougou.search.controller;

import com.yougou.common.pojo.SearchResult;
import com.yougou.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;

/**
 * 搜索逻辑层接口
 *
 * @ProjectName: yougoushop
 * @Package: com.yougou.search.controller
 * @ClassName: ${TYPE_NAME}
 * @Description: java类作用描述
 * @Author: HALEN(lizhigang)
 * @CreateDate: 2018/4/15 10:29
 * <p>Copyright: Copyright (c) 2018</p>
 */
@Controller
public class SearchController {

	@Autowired
	private SearchService searchService;

	@Value("${SEARCH_RESULT_ROWS}")
	private Integer SEARCH_RESULT_ROWS;

	@RequestMapping("search")
	public String search(@RequestParam("q")String queryString,
						 @RequestParam(defaultValue = "1")Integer page,
						 Model model) throws Exception{
		queryString = new String(queryString.getBytes("ISO8859-1"), "UTF-8");
		//调用服务执行查询
		SearchResult searchResult = searchService.search(queryString, page, SEARCH_RESULT_ROWS);
		//将结果传给页面
		model.addAttribute("query", queryString);
		model.addAttribute("page", page);
		if(searchResult != null){
			model.addAttribute("totalPages", searchResult.getTotalPages());
			model.addAttribute("itemList", searchResult.getItemList());
		}
		//返回逻辑视图
		return "search";
	}
}
