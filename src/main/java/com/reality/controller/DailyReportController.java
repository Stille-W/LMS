package com.reality.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.reality.form.DailyReportForm;

@Controller
public class DailyReportController {

	/**
	 * 日報作成画面の表示
	 */
	@GetMapping("/dailyReport")
	public String dailyReport(@ModelAttribute DailyReportForm dailyReportForm) {
		return "dailyReport";
	}
}
