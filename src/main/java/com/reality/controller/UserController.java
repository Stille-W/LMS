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
import com.reality.form.UserSigninForm;
import com.reality.repository.UserRepository;

import jakarta.validation.Valid;

@Controller
public class UserController {

	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/adduser")
	public String addUser(@ModelAttribute UserSigninForm form) {
		return "addUser";
	}
	
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
}
