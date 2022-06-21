package ltts.ems.com.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import ltts.ems.com.model.Attendance;
import ltts.ems.com.model.EmployeeDetails;
import ltts.ems.com.repository.EmployeeRepository;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {
	
	@Mock
	EmployeeRepository employeerepository;
	
	EmployeeServiceImpl employeeService;
	
	List<EmployeeDetails> edList = new ArrayList<EmployeeDetails>();
	
	@BeforeEach
	void setup() {
		this.employeeService = new EmployeeServiceImpl(this.employeerepository);
		edList.add(new EmployeeDetails(1,"firstName","lastName", "userName","password",LocalDate.now(), "gender" , LocalDate.now(), "role","departmentName","photo"));
		edList.add(new EmployeeDetails(2,"firstName","lastName", "userName","password",LocalDate.now(), "gender" , LocalDate.now(), "role","departmentName","photo"));
		edList.add(new EmployeeDetails(3,"firstName","lastName", "userName","password",LocalDate.now(), "gender" , LocalDate.now(), "role","departmentName","photo"));
		edList.add(new EmployeeDetails(4,"firstName","lastName", "userName","password",LocalDate.now(), "gender" , LocalDate.now(), "role","departmentName","photo"));
	}
	
	@Test
	void getAllEmployeesTest() {
		Mockito.when(employeerepository.findAll()).thenReturn(edList);
		Assert.assertTrue(employeeService.getAllEmployees().equals(edList));
	}
	
	@Test
	void saveEmployeesTest() {
		Mockito.when(employeerepository.save(edList.get(0))).thenReturn(edList.get(0));
		Assert.assertTrue(employeeService.saveEmployee(edList.get(0)).equals(edList.get(0)));
	}
	
	@Test
	void getEmployeeByIdTest() {
		Mockito.when(employeerepository.getById(0)).thenReturn(edList.get(0));
		Assert.assertTrue(employeeService.getEmployeeById(0).equals(edList.get(0)));
	}
	
	@Test
	void findByUserNameAndPasswordTest() {
		Mockito.when(employeerepository.findByUserNameAndPassword("A", "A")).thenReturn(edList.get(0));
		Assert.assertTrue(employeeService.findByUserNameAndPassword("A", "A").equals(edList.get(0)));
	}
	
	@Test
	void deleteEmployeeByIdTest() {
		employeeService.deleteEmployeeById(0);
		verify(employeerepository).deleteById(0);
	}
	
	@Test
	void findPaginatedTest() {
		employeeService.findPaginated(10, 10);
		PageRequest pageable = PageRequest.of(9,10);
		verify(employeerepository).findAll(pageable);
	}
}
