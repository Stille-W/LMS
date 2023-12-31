package com.reality.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.reality.entity.User;
import com.reality.form.LoginForm;
import com.reality.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class SessionController {
	@Autowired
	UserRepository userRepository;
	
	/**
	 * ログイン画面表示
	 */
	@GetMapping("/index")
	public String index(@ModelAttribute LoginForm form) {
		return "index";
	}
	
	/**
	 * ログイン実行
	 */
	@PostMapping("/login")
	public String doLogin(@Valid @ModelAttribute LoginForm form, BindingResult result, HttpSession session, Model model) {
		if (result.hasErrors()) {
			return "index";
		}
		
		String userName = form.getUserName();
		String password = form.getPassword();
		User user = userRepository.findByUserNameAndPassword(userName, password);
		
		if (user != null) {
			session.setAttribute("fullName", user.getFullName());
			session.setAttribute("userId",user.getId());
			return "redirect:/select";
		} else {
			model.addAttribute("errMessage", "ユーザー名、またはパスワードが間違っています。");
			return "index";
		}
	}
	
	/**
	 * ローディング画面表示
	 */
	@GetMapping("/loading")
	public String loading() {
		return "loading";
	}
	
	/**
	 * ログアウト実行
	 */
	@GetMapping("/logout")
	public String logout(@ModelAttribute LoginForm form, HttpSession session) {
		session.invalidate();
		return "index";
	}

	/**
	 * ログインをせずに内部にアクセスしたときにエラーメッセージを表示する
	 */
	@GetMapping("/login")
	public String login() {
        return "login";
    }
	
}
