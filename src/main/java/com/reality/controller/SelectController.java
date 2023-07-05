package com.reality.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class SelectController {
	/**
	 * メニュー画面表示
	 */
	@GetMapping("/select")
	public String login(HttpSession session) {
		return "select";
	}
	
}
