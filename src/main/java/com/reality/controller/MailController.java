package com.reality.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MailController {
	/**
	 * メールテンプレートのメニュー画面を表示
	 */
	@GetMapping("/mail")
	public String mail() {
		return "mail";
	}
	
	/**
	 * メールテンプレートの表示
	 * 
	 * @param recipient /mailで入力した送信相手の名前
	 * @param contents /mailで選択したメールの内容
	 */
	@GetMapping("/mailTemplate")
	public String mailTemp(String recipient, String contents, Model model) {
		model.addAttribute("recipient", recipient);
		model.addAttribute("contents", contents);
		return "mailTemplate";
	}

}
