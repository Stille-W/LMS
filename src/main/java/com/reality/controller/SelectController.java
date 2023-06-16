package com.reality.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.reality.form.DailyReportForm;
import com.reality.form.LoginForm;
import com.reality.repository.AttendanceRepository;
import com.reality.util.Form2Excel;

import jakarta.servlet.http.HttpSession;

@Controller
public class SelectController {
	
	@GetMapping("/select")
	public String login(HttpSession session) {
		return "select";
	}
	
	@PostMapping("/select")
		public String doLogin(LoginForm form, HttpSession session) {
			session.setAttribute("userName", form.getUserName());
			Date date = new Date();
			SimpleDateFormat sdf= new SimpleDateFormat("yyyy/MM/dd");
			session.setAttribute("date", sdf.format(date));
			return "select";
		}
	
}
