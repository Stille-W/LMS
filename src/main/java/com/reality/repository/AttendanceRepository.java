package com.reality.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.reality.entity.Attendance;
import com.reality.entity.User;
import org.springframework.transaction.annotation.Transactional;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer>{
	Attendance findByDate(Date date);
	List<Attendance> findByUser(User user);
	List<Attendance> findByUserAndProjectIsNotNullOrderByDateAsc(User user);
	List<Attendance> findByDateAndStartTime(Date date, String startTime);
	void deleteByDateAndStartTimeAndUser(Date date, String startTime, User user);
	List<Attendance> findByUserAndProjectIsNotNullOrderByDateDesc(User user);
	List<Attendance> findByUserAndDate(User user, Date date);
	@Query(value = "select * from attendance where month(date) = :month and uid = :uid and project is not null order by date, TIME_TO_SEC(start_time) asc", nativeQuery = true)
	List<Attendance> findByMMAndUserIdAndProjectIsNotNullOrderByDateAsc(@Param("month") Integer month, @Param("uid") Integer uid);

	@Query(value = "select * from attendance where month(date) = :month and uid = :uid order by date, TIME_TO_SEC(start_time) asc", nativeQuery = true)
	List<Attendance> findByMMAndUserIdOrderByDateAsc(@Param("month") Integer month, @Param("uid") Integer uid);

	@Transactional(rollbackFor = Exception.class)
	@Modifying
	@Query(value = "INSERT INTO attendance(date, uid) VALUES (?1, ?2)", nativeQuery = true)
	void insertDateByUserId(Date date, Integer uid);
}
