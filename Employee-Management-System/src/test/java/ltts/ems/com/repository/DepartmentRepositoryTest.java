package ltts.ems.com.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ltts.ems.com.model.Department;

@SpringBootTest
class DepartmentRepositoryTest {
	@Autowired
	private DepartmentRepository depRepo;
	
	private Department d;
	
	
    @BeforeEach
    void setUp() {
    	d = new Department(1,"Dummy", "Location");
    	depRepo.save(d);
    	System.out.println("Added DummyVariable");
    }
	
	@Test
	void employeefindByUserNameAndPasswordTest() {
		List<Department> d1 = depRepo.findById(1);
		//Department d1= ed1[0];
		assertEquals(d1.get(0).getDeptName(),d.getDeptName());
		d=d1.get(0);
	}
	
	@AfterEach
	void deleteTestInstance() {
		depRepo.delete(d);
	}

}
