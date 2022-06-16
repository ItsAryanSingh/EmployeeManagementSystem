package ltts.ems.com.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import ltts.ems.com.model.EmployeeDetails;

import java.time.LocalDate;

@SpringBootTest
class EmployeeRepositoryTest {

	@Autowired
	private EmployeeRepository empRepo;
	
	private EmployeeDetails ed;
	
	
    @BeforeEach
    void setUp() {
    	LocalDate ldStart = LocalDate.parse("2022-06-22");
    	LocalDate ldBirth = LocalDate.parse("2022-06-22");
    	ed = new EmployeeDetails(1000,"Dummy","User","dummyUserName1","dummyUserPassWord1",ldStart,"Neutral",ldBirth,"None","None","None");
    	empRepo.save(ed);
    	System.out.println("Added DummyVariable");
    }
	
	@Test
	void employeefindByUserNameAndPasswordTest() {
		EmployeeDetails ed1 = empRepo.findByUserNameAndPassword("dummyUserName1","dummyUserPassWord1");
		assertEquals(ed1.getFirstName(),ed.getFirstName());
		ed=ed1;
	}
	
	@AfterEach
	void deleteTestInstance() {
		empRepo.delete(ed);
	}

}
