package com.reality.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.reality.form.DailyReportForm;
import com.reality.util.Form2Excel;

import jakarta.servlet.http.HttpSession;

@Controller
public class DailyReportController {

	@GetMapping("/dailyReport")
	public String dailyReport(@ModelAttribute DailyReportForm dailyReportForm) {
		return "dailyReport";
	}
	
	@PostMapping("/doDailyReport")
	public String doDailyReport(@ModelAttribute DailyReportForm dailyReportForm, Model model) {
		Form2Excel excel = new Form2Excel();
		try {
			excel.runForm2Excel(dailyReportForm);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			model.addAttribute("stat", "excelUsed");
			return "error";
		} catch (Exception e) {
			// TODO: handle exception
			model.addAttribute("stat", "error");
			e.printStackTrace();
			return "error";
		}
		model.addAttribute("stat", "dailyDone");
		return "loading";
	}
}
