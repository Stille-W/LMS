package com.reality.controller;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.reality.entity.Attendance;
import com.reality.entity.User;
import com.reality.repository.AttendanceRepository;
import com.reality.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class ManualnputController {
	@Autowired
	AttendanceRepository attendanceRepository;
	
	@Autowired
	UserRepository userRepository;
	
	
	@GetMapping("/manualInput")
	public String manualInput() {
		return "manualInput";
	}
	
	@PostMapping("/doManualInput")
	public String doManualInput(String date, String startTime, String endTime, String workHours,
			String division, String project, String place, String remarks, Model model, HttpSession session) throws ParseException {
			Attendance attendance = new Attendance();
			User user = userRepository.getReferenceById(Integer.parseInt(session.getAttribute("userId").toString()));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date dateTemp = new SimpleDateFormat("yyyy-MM-dd").parse(date);
			  
			List<Attendance> attdArr = attendanceRepository.findByUser(user);
			
			for (int i = 0; i < attdArr.size(); i++) {
				if (sdf.format(attdArr.get(i).getDate()).equals(date) && attdArr.get(i).getProject().equals("新入社員研修")) {
//					if(removeFirstChar(startTime).equals("9:00") || removeFirstChar(endTime).equals("18:00")) {
					if(project.equals(attdArr.get(i).getProject())) {
						model.addAttribute("stat", "attendanceError");
						return "error";
					}	
				}
			}
			
			attendance.setDate(dateTemp);
			attendance.setStartTime(removeFirstChar(startTime));
			attendance.setEndTime(removeFirstChar(endTime));
			attendance.setWorkHours(removeFirstChar(workHours));
			attendance.setDivision(division);
			attendance.setPlace(place);
			attendance.setProject(project);
			attendance.setRemarks(remarks);
			attendance.setUser(user);
			attendanceRepository.save(attendance);
			model.addAttribute("attendance");
			return "redirect:/findAllAttendance";
		}
	
	//　時刻の入力形式変更 ex) 09:00 >> 9:00
	private String removeFirstChar (String str) {
		if(str.startsWith("0")) {
			str = str.substring(1);
		}		
		return str;
	}
	
}
