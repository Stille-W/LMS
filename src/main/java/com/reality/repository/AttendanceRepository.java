package com.reality.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.reality.entity.Attendance;
import com.reality.entity.User;
public interface AttendanceRepository extends JpaRepository<Attendance, Integer>{
	Attendance findByDate(Date date);
	List<Attendance> findByUser(User user);
	List<Attendance> findByUserOrderByDateAsc(User user);
	List<Attendance> findByDateAndStartTime(Date date, String startTime);
	void deleteByDateAndStartTimeAndUser(Date date, String startTime, User user);
//	List<Attendance> findByDateContaining(Date date);
	List<Attendance> findByUserOrderByDateDesc(User user);
	@Query(value = "select * from attendance where month(date) = :month and uid = :uid order by date asc", nativeQuery = true)
	List<Attendance> findByMMAndUserIdOrderByDateAsc(@Param("month") Integer month, @Param("uid") Integer uid);
}
