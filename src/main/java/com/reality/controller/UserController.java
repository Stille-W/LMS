package com.reality.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.reality.entity.User;
import com.reality.form.UserSigninForm;
import com.reality.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class UserController {

	@Autowired
	UserRepository userRepository;
	
	/**
	 * ユーザーの新規登録画面表示
	 */
	@GetMapping("/adduser")
	public String addUser(@ModelAttribute UserSigninForm form) {
		return "addUser";
	}
	
	/**
	 * ユーザーの新規登録実行
	 */
	@PostMapping("/adduser")
	public String doAddUser(@Valid @ModelAttribute UserSigninForm form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "addUser";
		}
		if (userRepository.findAll().stream().anyMatch(u->u.getUserName().equals(form.getUserName()))) {
			model.addAttribute("dup", "UserName は重複しました。");
			return "addUser";
		}
		User user = new User();
		user.setUserName(form.getUserName());
		user.setFullName(form.getFullName());
		user.setPassword(form.getPassword());
		userRepository.save(user);
		return "user";
	}

	/**
	 * ユーザー情報編集画面表示
	 */
	@GetMapping("/editUser")
	public String editUser(@ModelAttribute UserSigninForm form, Model model, HttpSession session) {
		User user = userRepository.getReferenceById(Integer.parseInt(session.getAttribute("userId").toString()));

		form.setUserName(user.getUserName());
		form.setFullName(user.getFullName());
		form.setPassword(user.getPassword());
		return "editUser";
	}

	/**
	 * ユーザー情報を更新
	 */
	@PostMapping("/editUser")
	public String doEditUser(@Valid @ModelAttribute UserSigninForm form, BindingResult result, Model model, HttpSession session) {
		User user = userRepository.getReferenceById(Integer.parseInt(session.getAttribute("userId").toString()));

		if (result.hasErrors()) {
			form.setUserName(user.getUserName());
			form.setFullName(user.getFullName());
			form.setPassword(user.getPassword());
			System.out.println(result.toString());
			return "editUser";
		}

		user.setFullName(form.getFullName());
		user.setPassword(form.getPassword());
		userRepository.save(user);
		session.setAttribute("fullName",user.getFullName());
		model.addAttribute("stat", "editUserSuccess");
		return "loading";
	}
}
