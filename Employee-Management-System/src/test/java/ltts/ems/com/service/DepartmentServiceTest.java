package ltts.ems.com.service;


import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

//import static org.junit.jupiter.api.Assertions.*;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ltts.ems.com.model.Department;
import ltts.ems.com.repository.DepartmentRepository;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {
	
	@Mock
	private DepartmentRepository depRepo;
	
	private DepartmentService depService;
	
	List<Department> depList = new ArrayList<Department>();

	@BeforeEach
	 void setup() {
		this.depService = new DepartmentService(this.depRepo);
		depList.add(new Department(101,"MockDep1","MockLocation1"));
		depList.add(new Department(102,"MockDep2","MockLocation2"));
		depList.add(new Department(103,"MockDep3","MockLocation3"));
		depList.add(new Department(104,"MockDep4","MockLocation4"));
	}

	@Test
	void getAllDepartmentsTest() {
		Mockito.when(depRepo.findAll()).thenReturn(depList);
		Assert.assertTrue(depService.getAllDepartments().equals(depList));
	}
	
	@Test
	void saveDepartmentTest() {
		Mockito.when(depRepo.save(depList.get(0))).thenReturn(depList.get(0));
		Assert.assertTrue(depService.saveDepartment(depList.get(0)).equals(depList.get(0)));
	}
	
	@Test
	void updateDepartmentTest() {
		Mockito.when(depRepo.save(depList.get(0))).thenReturn(depList.get(0));
		Assert.assertTrue(depService.updateDepartment(depList.get(0)).equals(depList.get(0)));
	}
	
	@Test
	void getAllDepartmentByIdTest() {
		Mockito.when(depRepo.getById(0)).thenReturn(depList.get(0));
		Assert.assertTrue(depService.getDepartmentById(0).equals(depList.get(0)));
	}
	
	@Test
	void deleteByIdTest() {
		depService.deleteByid(0);
		verify(depRepo).deleteById(0);
	}

}
