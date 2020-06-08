package com.yst.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller                  
@RequestMapping("/index")
public class IndexController {
	
	@RequestMapping("test.do")
	@ResponseBody
	public String test(HttpServletRequest request, HttpServletResponse response) {
		return "{\"test\":\"aaa\"}";
	}
}
