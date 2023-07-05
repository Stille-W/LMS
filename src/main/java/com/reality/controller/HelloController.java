package com.reality.controller;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

//@RestController
public class HelloController {

	public static void main(String[] args) {
		LocalDate now = LocalDate.now();
		LocalDate first = now.with(TemporalAdjusters.firstDayOfMonth());
		LocalDate last = now.with(TemporalAdjusters.lastDayOfMonth());
		System.out.println(now+","+first+","+last);
		Date firstD = Date.from(first.atStartOfDay(ZoneId.systemDefault()).toInstant());
		System.out.println(firstD);
	}

	@RequestMapping("/")
	public String hello(ServletRequest request, ServletResponse response) {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		System.out.println("URI:"+req.getRequestURI());
		System.out.println("PATH:"+req.getContextPath());
		return "hello World!";
	}

	@RequestMapping("/gm")
	public String gm(ServletRequest request, ServletResponse response) {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		System.out.println("URI:"+req.getRequestURI());
		System.out.println("PATH:"+req.getContextPath());
		return "gm World!";
	}
}
