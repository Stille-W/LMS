package com.reality.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.reality.entity.Attendance;
import com.reality.entity.User;
import com.reality.repository.AttendanceRepository;
import com.reality.repository.UserRepository;
import com.reality.util.Form2ExcelMM;

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

        if (attendanceRepository.findByUser(user).stream().anyMatch(e->sdf.format(e.getDate()).equals(sdf.format(date)))) {
            model.addAttribute("stat", "attendanceError");
            return "error";
        }

        attendance.setDate(date);
        attendance.setStartTime("9:00");
        attendance.setEndTime("18:00");
        attendance.setDivision("");
        attendance.setWorkHours("8:00");
        attendance.setProject("新入社員研修");
        attendance.setPlace("高田馬場事務所");
        attendance.setRemarks("やっとむ屋");
        attendance.setUser(user);

        attendanceRepository.save(attendance);

        model.addAttribute("stat", "attendance");
        return "loading";
    }

    @GetMapping("/findAllAttendance")
    public String findAllAttendance(Model model, HttpSession session) {
        User user = userRepository.getReferenceById(Integer.parseInt(session.getAttribute("userId").toString()));
        model.addAttribute("attendance", attendanceRepository.findByUserOrderByDateAsc(user));
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
