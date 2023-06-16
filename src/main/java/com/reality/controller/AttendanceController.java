package com.reality.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import com.reality.util.Form2ExcelMM;

import jakarta.servlet.http.HttpSession;

@Controller
public class AttendanceController {
	
	@Autowired
	AttendanceRepository attendanceRepository;
	
	@Autowired
	UserRepository userRepository;

	@GetMapping("/attendanceSystem")
	public String attendanceSystem() {
		return "attendanceSystem";
	}
	
	@PostMapping("/attendanceRegister")
	public String attendanceRegister(Model model, HttpSession session) {
		Attendance attendance = new Attendance();
		User user = userRepository.getReferenceById(Integer.parseInt(session.getAttribute("userId").toString()));
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = sdf.format(date);
		
		List<Attendance> attdArr = attendanceRepository.findAll();
		for(int i=0; i<attdArr.size(); i++) {
			if(sdf.format(attdArr.get(i).getDate()).equals(dateStr) && attdArr.get(i).getUser().getId()==user.getId()) {
				model.addAttribute("stat", "attendanceError");
				return "error";
			}
		}
		attendance.setDate(date);
		attendance.setStartTime("9:00");
		attendance.setEndTime("18:00");
		attendance.setWorkHours("8:00");
		attendance.setPlace("高田馬場事務所");
		attendance.setProject("新入社員研修");
		attendance.setRemarks("やっとむ屋");
		attendance.setUser(user);
		attendanceRepository.save(attendance);
		model.addAttribute("attendance", attendance);
		model.addAttribute("stat", "attendance");
		return "loading";
	}
	

	
	@GetMapping("/findAllAttendance")
	public String findAllAttendance(Model model, HttpSession session) {
		User user = userRepository.getReferenceById(Integer.parseInt(session.getAttribute("userId").toString()));
//		model.addAttribute("attendance", attendanceRepository.findByUser(user));
		model.addAttribute("attendance", attendanceRepository.findByUserOrderByDateAsc(user));
		return "findAllAttendance";
	}
	
	
	@GetMapping("/findByMonth")
	public String findByMonth(String month, Model model, HttpSession session) throws ParseException {		
		// 2023-06
		String[] yearMonth = month.split("-");
		Integer numMonth = Integer.parseInt(yearMonth[1]);
		model.addAttribute("attendance", attendanceRepository.findByMMAndUserIdOrderByDateAsc(numMonth, (Integer)session.getAttribute("userId")));
		return "findAllAttendance";
	}
	
	@PostMapping("/genReport")
	public String genReport(Integer month, HttpSession session, Model model) throws Exception {
		List<Attendance> attendances = attendanceRepository.findByMMAndUserIdOrderByDateAsc(month, (Integer)session.getAttribute("userId"));
		String genDate = Calendar.getInstance().get(Calendar.YEAR)+"/"+month; 
		Form2ExcelMM excelMM = new Form2ExcelMM();
		excelMM.runForm2Excel(attendances, genDate, session);
		model.addAttribute("stat", "dailyDone");
		return "loading";
	}
}
