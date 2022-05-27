package ltts.ems.com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ltts.ems.com.model.Attendance;
import ltts.ems.com.repository.AttendanceRepository;

@Service
public class AttendanceService  {

	@Autowired
	AttendanceRepository attendanceDao;

	/**
	 * Method to save new attendance into the database
	 * @param attendance
	 * @return Save attendance request
	 */

	public Attendance insertAttendance(Attendance attendance){
		return attendanceDao.save(attendance);
	}

	/**
	 * Method to update attendance in the database
	 * @param attendance
	 * @return Update attendance request
	 */

	public Attendance updateAttendance(Attendance attendance){
		return attendanceDao.save(attendance);
	}

	/**
	 * Method to return all attendance requests
	 * @return List of all attendance requests
	 */

	public List<Attendance> getAllAttendance(){
		return attendanceDao.findAll();
	}

	/**
	 * Method to return attendance of particular employee
	 * @param id Contains unique employee id to fetch employee details
	 * @return Particular employee attendance requests
	 */

	public Attendance getAttendanceById(int id){
		return attendanceDao.getById(id);
	}


	public void deleteById(int id){
		attendanceDao.deleteById(id);
		return;
	}
}
