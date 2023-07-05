package com.reality.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;

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

/**
 * @author yagami.wakana
 *
 */


@Controller
public class AttendanceController {
    @Autowired
    AttendanceRepository attendanceRepository;

    @Autowired
    UserRepository userRepository;

    /**
     * 勤怠関連のメニュー画面を表示
     */
    @GetMapping("/attendanceSystem")
    public String attendanceSystem(HttpSession session) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        int month = Integer.parseInt(sdf.format(date));
        int uid = Integer.parseInt(session.getAttribute("userId").toString());

        LocalDate now = LocalDate.now();
        LocalDate first = now.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate last = now.with(TemporalAdjusters.lastDayOfMonth());

        Date startDate = Date.from(first.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(last.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

        if (attendanceRepository.findByMMAndUserIdOrderByDateAsc(month, uid).size() == 0) {
            Calendar startCal = Calendar.getInstance();
            startCal.setTime(startDate);
            Calendar endCal = Calendar.getInstance();
            endCal.setTime(endDate);
            while (startCal.getTimeInMillis() <= endCal.getTimeInMillis()) {
                attendanceRepository.insertDateByUserId(startCal.getTime(), uid);
                startCal.add(Calendar.DATE, 1);
            }
        }
        return "attendanceSystem";
    }

    /**
     * 当日の出勤を登録
     */
    @PostMapping("/attendanceRegister")
    public String attendanceRegister(Model model, HttpSession session) {

        User user = userRepository.getReferenceById(Integer.parseInt(session.getAttribute("userId").toString()));

        LocalDate now = LocalDate.now();
        Date date = Date.from(now.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Attendance attendance = new Attendance();
        if (attendanceRepository.findByUserAndDate(user, date).size() != 0) {
            if (attendanceRepository.findByUserAndDate(user, date).stream().filter(a->a.getProject()==null).collect(java.util.stream.Collectors.toList()).size() == 0) {
                model.addAttribute("stat", "attendanceError");
                return "error";
            } else {
                attendance = attendanceRepository.findByUserAndDate(user, date).stream().filter(a->a.getProject()==null).collect(java.util.stream.Collectors.toList()).get(0);
            }
        } else {
            attendance.setDate(date);
        }

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

    /**
     * 登録済みの勤怠情報一覧を表示
     */
    @GetMapping("/findAllAttendance")
    public String findAllAttendance(Model model, HttpSession session) {
        User user = userRepository.getReferenceById(Integer.parseInt(session.getAttribute("userId").toString()));
        model.addAttribute("attendance", attendanceRepository.findByUserAndProjectIsNotNullOrderByDateAsc(user));
        Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		String dateStr = sdf.format(date);
		session.setAttribute("date", dateStr);
        return "findAllAttendance";
    }

    /**
     *  登録済みの勤怠情報一覧を月別検索して表示
     * @param month 月
     */
    @GetMapping("/findByMonth")
    public String findByMonth(String month, Model model, HttpSession session) {
        Integer monInt = Integer.parseInt(month.split("-")[1]);
        model.addAttribute("attendance", attendanceRepository.findByMMAndUserIdAndProjectIsNotNullOrderByDateAsc(
                            monInt, Integer.parseInt(session.getAttribute("userId").toString())));
        return "findAllAttendance";
    }

}
