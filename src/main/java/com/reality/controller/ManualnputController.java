package com.reality.controller;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
	
	/**
	 * 勤怠情報の手動入力画面表示
	 */
	@GetMapping("/manualInput")
	public String manualInput() {
		return "manualInput";
	}
	
	/**
	 * 手動入力した勤怠情報をDBに登録
	 * 
	 * @param date 日付
	 * @param startTime 開始時間
	 * @param endTime 終了時間
	 * @param workHours 勤務時間
	 * @param division 区分
	 * @param project プロジェクト
	 * @param place 作業場所
	 * @param remarks 備考
	 */
	@PostMapping("/doManualInput")
	public String doManualInput(String date, String startTime, String endTime, String workHours,
			String division, String project, String place, String remarks, Model model, HttpSession session) throws ParseException {
			Attendance attendance = new Attendance();
			User user = userRepository.getReferenceById(Integer.parseInt(session.getAttribute("userId").toString()));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date dateTemp = new SimpleDateFormat("yyyy-MM-dd").parse(date);

			if (project.equals("新入社員研修") && attendanceRepository.findByUserAndDate(user, dateTemp).size() != 0) {
				if(attendanceRepository.findByUserAndDate(user, dateTemp).stream().filter(a->a.getProject()==null).collect(java.util.stream.Collectors.toList()).size() != 0) {
					attendance = attendanceRepository.findByUserAndDate(user, dateTemp).stream().filter(a -> a.getProject()==null).collect(Collectors.toList()).get(0);
				} else {
					model.addAttribute("stat", "attendanceError");
					return "error";
				}
			} else {
				attendance.setDate(dateTemp);
			}

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
