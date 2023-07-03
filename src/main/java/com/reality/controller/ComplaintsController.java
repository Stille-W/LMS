package com.reality.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import com.reality.entity.Attendance;
import com.reality.entity.User;
import com.reality.repository.AttendanceRepository;
import com.reality.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
@Controller
public class ComplaintsController {
	@Autowired
	AttendanceRepository attendanceRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@PostMapping("/complaints")
	public String complaints(Model model, HttpSession session, String startTime, String endTime, String place, String remarks) {
		Attendance attendance = new Attendance();
		User user = userRepository.getReferenceById(Integer.parseInt(session.getAttribute("userId").toString()));
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = sdf.format(date);
		
		List<Attendance> attdArr = attendanceRepository.findAll();
		for (int i = 0; i < attdArr.size(); i++) {
			if (sdf.format(attdArr.get(i).getDate()).equals(dateStr) && attdArr.get(i).getUser().getId()==user.getId()
							&& attdArr.get(i).getProject().contains("愚痴相談")) {
				model.addAttribute("stat", "attendanceError");
				return "error";
			}
		}
		
		attendance.setDate(date);
		attendance.setStartTime(removeFirstChar(startTime));
		attendance.setEndTime(removeFirstChar(endTime));
		attendance.setWorkHours("0:30");
		attendance.setPlace(place);
		attendance.setProject("新人教育（愚痴相談）");
		attendance.setRemarks(remarks);
		attendance.setUser(user);
		attendanceRepository.save(attendance);
		model.addAttribute("stat", "complaints");
		return "loading";
	}
	
	//　時刻の入力形式変更 ex) 09:00 >> 9:00
	private String removeFirstChar (String str) {
		if(str.startsWith("0")) {
			str = str.substring(1);
		}		
		return str;
	}
}
