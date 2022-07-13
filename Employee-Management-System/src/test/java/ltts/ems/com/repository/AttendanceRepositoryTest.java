package ltts.ems.com.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.format.annotation.DateTimeFormat;

import ltts.ems.com.model.Attendance;
import ltts.ems.com.model.EmployeeDetails;

@SpringBootTest
class AttendanceRepositoryTest {

	@Autowired
	private AttendanceRepository atdRepo;
	@Autowired
	private EmployeeRepository empRepo;
	
	private Attendance atd;
	private EmployeeDetails ed;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private Date inTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private Date outTime;
	
	
    @BeforeEach
    void setUp() {
    	LocalDate ldStart = LocalDate.parse("2022-06-22");
    	LocalDate ldBirth = LocalDate.parse("2022-06-22");
    	ed = new EmployeeDetails(1000,"Dummy","User","dummyUserName1","dummyUserPassWord1",ldStart,"Neutral",ldBirth,"None","None","None","itsaryansingh2019@gmail.com");
    	empRepo.save(ed);
    	inTime = new Date();
    	outTime = new Date();
    	inTime.setTime(1000);
    	outTime.setTime(1000);
    	atd = new Attendance(10,ed.getEmpId(),ed.getFirstName(),inTime,outTime,"DontCare");
    	atdRepo.save(atd);
    	System.out.println("Added DummyVariable");
    }
	
	@Test
	void employeefindByUserNameAndPasswordTest() {
		List<Attendance> atd1 = atdRepo.findAttendanceByEmpId(ed.getEmpId());
		assertEquals(atd1.get(0).getFirstName(),ed.getFirstName());
		atd=atd1.get(0);
	}
	
	@AfterEach
	void deleteTestInstance() {
		empRepo.delete(empRepo.findByUserNameAndPassword(ed.getUserName(), ed.getPassword()));
		atdRepo.delete(atd);
	}


}
