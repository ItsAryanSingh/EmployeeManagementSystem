package ltts.ems.com.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;  
import java.util.Date;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import ltts.ems.com.model.Attendance;
import ltts.ems.com.model.Department;
import ltts.ems.com.repository.AttendanceRepository;
import ltts.ems.com.repository.DepartmentRepository;


@ExtendWith(MockitoExtension.class)
class AttendanceServiceTest {
	
	@Mock
	private AttendanceRepository atdRepo;
	
	private AttendanceService atdService;
	
	List<Attendance> atdList = new ArrayList<Attendance>();

	@BeforeEach
	 void setup() throws ParseException {
		String sDate1="31/12/1998";  
	    Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);  
		this.atdService = new AttendanceService(this.atdRepo);
		atdList.add(new Attendance(101,101,"MockName1",date1,date1,"MockStatus1"));
		atdList.add(new Attendance(102,102,"MockName2",date1,date1,"MockStatus2"));
		atdList.add(new Attendance(103,103,"MockName3",date1,date1,"MockStatus3"));
		atdList.add(new Attendance(104,104,"MockName4",date1,date1,"MockStatus4"));
	}
	
	
	@Test
	void getAllAttendanceTest() {
		Mockito.when(atdRepo.findAll()).thenReturn(atdList);
		Assert.assertTrue(atdService.getAllAttendance().equals(atdList));
	}
	
	@Test
	void insertAttendanceTest() {
		Mockito.when(atdRepo.save(atdList.get(0))).thenReturn(atdList.get(0));
		Assert.assertTrue(atdService.insertAttendance(atdList.get(0)).equals(atdList.get(0)));
	}
	
	@Test
	void updateAttendanceTest() {
		Mockito.when(atdRepo.save(atdList.get(0))).thenReturn(atdList.get(0));
		Assert.assertTrue(atdService.updateAttendance(atdList.get(0)).equals(atdList.get(0)));
	}
	
	@Test
	void getAttendanceByIdTest() {
		Mockito.when(atdRepo.getById(0)).thenReturn(atdList.get(0));
		Assert.assertTrue(atdService.getAttendanceById(0).equals(atdList.get(0)));
	}
	
	@Test
	void deleteByIdTest() {
		atdService.deleteById(0);
		verify(atdRepo).deleteById(0);
	}

}
