package com.reality.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MailController {
	@GetMapping("/mail")
	public String mail() {
		return "mail";
	}
	
	@GetMapping("/mailTemplate")
	public String mailTemp(String recipient, String contents, Model model) {
		model.addAttribute("recipient", recipient);
		model.addAttribute("contents", contents);
		return "mailTemplate";
	}

}
