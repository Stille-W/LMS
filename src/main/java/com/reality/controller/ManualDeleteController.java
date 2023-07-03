package com.reality.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.reality.entity.User;
import com.reality.repository.AttendanceRepository;
import com.reality.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class ManualDeleteController {
	@Autowired
	AttendanceRepository attendanceRepository;
	
	@Autowired
	UserRepository userRepository;

	@GetMapping("/manualDelete")
	public String manualDelete(Model model, HttpSession session) {
		User user = userRepository.getReferenceById(Integer.parseInt(session.getAttribute("userId").toString()));
		model.addAttribute("attendance", attendanceRepository.findByUserOrderByDateAsc(user));
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		String dateStr = sdf.format(date);
		session.setAttribute("date", dateStr);
		return "manualDelete";
	}
	
    @GetMapping("/findByDateDescManualDelete")
    public String findByDateDescManualDelete(Model model, HttpSession session) {
    	User user = userRepository.getReferenceById(Integer.parseInt(session.getAttribute("userId").toString()));
    	model.addAttribute("attendance", attendanceRepository.findByUserOrderByDateDesc(user));
    	return "manualDelete";
    }
    
    @GetMapping("/findByDateAscManualDelete")
    public String findByDateAscManualDelete(Model model, HttpSession session) {
    	User user = userRepository.getReferenceById(Integer.parseInt(session.getAttribute("userId").toString()));
    	model.addAttribute("attendance", attendanceRepository.findByUserOrderByDateAsc(user));
    	return "manualDelete";
    }
	
//	@PostMapping("/doManualDelete")
//	@Transactional(rollbackFor = Exception.class)
//	public String doManualDelete(String date, String startTime, Model model, HttpSession session) throws ParseException {
//		User user = userRepository.getReferenceById(Integer.parseInt(session.getAttribute("userId").toString()));
//		Date dateTemp = new SimpleDateFormat("yyyy-MM-dd").parse(date);
//		startTime = removeFirstChar(startTime);
//		
//		attendanceRepository.deleteByDateAndStartTimeAndUser(dateTemp, startTime, user);
//		return "redirect:/findAllAttendance";
//	}
	
	@GetMapping("/findByMonthManualDelete")
    public String findByMonthManualDelete(String month, Model model, HttpSession session) {
        Integer monInt = Integer.parseInt(month.split("-")[1]);
        model.addAttribute("attendance", attendanceRepository.findByMMAndUserIdOrderByDateAsc(
                            monInt, Integer.parseInt(session.getAttribute("userId").toString())));
        return "manualDelete";
    }
	
	@PostMapping("/doManualDeleteAjax")
	@Transactional(rollbackFor = Exception.class)
	public String doManualDelete1(Integer aId) throws ParseException {
		attendanceRepository.deleteById(aId);
		return "redirect:/manualDelete";
	}

//	//　時刻の入力形式変更 ex) 09:00 >> 9:00
//	private String removeFirstChar (String str) {
//		if(str.startsWith("0")) {
//			str = str.substring(1);
//		}
//		return str;
//	}
}
