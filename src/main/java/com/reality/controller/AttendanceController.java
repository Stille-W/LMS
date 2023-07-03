package com.reality.controller;

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
public class AttendanceController {
	// にゃー

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

        List<Attendance> attList = attendanceRepository.findByUser(user);
        
        for (Attendance att : attList) {
			if (sdf.format(att.getDate()).equals(sdf.format(date)) && att.getProject().equals("新入社員研修")) {
				model.addAttribute("stat", "attendanceError");
	            return "error";
			}
		}

        attendance.setDate(date);
        attendance.setStartTime("9:00");
        attendance.setEndTime("18:00");
        attendance.setDivision("");
        attendance.setWorkHours("8:00");
        attendance.setProject("新入社員研修");
        attendance.setPlace("高田馬場事務所");
        attendance.setRemarks("Agile-PBL開発");
        attendance.setUser(user);

        attendanceRepository.save(attendance);

        model.addAttribute("stat", "attendance");
        return "loading";
    }

    @GetMapping("/findAllAttendance")
    public String findAllAttendance(Model model, HttpSession session) {
        User user = userRepository.getReferenceById(Integer.parseInt(session.getAttribute("userId").toString()));
        model.addAttribute("attendance", attendanceRepository.findByUserOrderByDateAsc(user));
        Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		String dateStr = sdf.format(date);
		session.setAttribute("date", dateStr);
        return "findAllAttendance";
    }

    @GetMapping("/findByMonth")
    public String findByMonth(String month, Model model, HttpSession session) {
        Integer monInt = Integer.parseInt(month.split("-")[1]);
        model.addAttribute("attendance", attendanceRepository.findByMMAndUserIdOrderByDateAsc(
                            monInt, Integer.parseInt(session.getAttribute("userId").toString())));
        return "findAllAttendance";
    }

}
