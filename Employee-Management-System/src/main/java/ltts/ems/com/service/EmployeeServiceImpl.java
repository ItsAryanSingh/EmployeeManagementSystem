package ltts.ems.com.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.SystemProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ltts.ems.com.model.EmployeeDetails;
import ltts.ems.com.repository.DepartmentRepository;
import ltts.ems.com.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeRepository employeerepository;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public EmployeeServiceImpl(EmployeeRepository employeerepositoryMock) {
		employeerepository = employeerepositoryMock;
	}

	@Override
	public List<EmployeeDetails> getAllEmployees() {
		List<EmployeeDetails> listOfEmployees = new ArrayList<EmployeeDetails>(employeerepository.findAll());
		List<EmployeeDetails> listOfEmployeesDecrypted = new ArrayList<EmployeeDetails>(employeerepository.findAll());
		List<String> passList = new ArrayList<String>();
		System.out.println("\n\n\n");
		System.out.println(listOfEmployees);
		for(int i=0;i < listOfEmployees.size();i++) {
			System.out.println(">>>>>");
			System.out.println(listOfEmployees.get(i).getPassword());
			String password = AES256.decrypt(listOfEmployees.get(i).getPassword());
			System.out.println(password);
			passList.add(password);
			//listOfEmployeesDecrypted.get(i).setPassword(password);
			//listOfEmployees.get(i).setPassword(AES256.decrypt(listOfEmployees.get(i).getPassword()));
		}
		System.out.println(passList);
		for(int i=0;i < passList.size();i++) {
			listOfEmployeesDecrypted.get(i).setPassword(passList.get(i));
			//listOfEmployees.get(i).setPassword(AES256.decrypt(listOfEmployees.get(i).getPassword()));
		}
		return listOfEmployeesDecrypted;

	}

	@Override
	public Page<EmployeeDetails> findPaginated(int pageNo, int pageSize) {
		PageRequest pageable = PageRequest.of(pageNo - 1, pageSize);
		return this.employeerepository.findAll(pageable);
	}

	@Override
	public EmployeeDetails saveEmployee(EmployeeDetails employee) {
		employee.setPassword(AES256.encrypt(employee.getPassword()));
		return employeerepository.save(employee);

	}

	@Override
	public EmployeeDetails getEmployeeById(int id) {
		EmployeeDetails emp =  employeerepository.getById(id);
		emp.setPassword(AES256.decrypt(emp.getPassword()));
		return emp;
	}
	@Override
	public EmployeeDetails getEmployeeByUsername(String userName) {
		EmployeeDetails emp = employeerepository.getUserByUserName(userName);
		emp.setPassword(AES256.decrypt(emp.getPassword()));
		return emp;
	}
	@Override
	public void deleteEmployeeById(int id) {
		this.employeerepository.deleteById(id);

	}

	@Override
	public EmployeeDetails findByUserNameAndPassword(String userName, String password) {
		password = AES256.encrypt(password);
		EmployeeDetails emp = employeerepository.findByUserNameAndPassword(userName, password);
		emp.setPassword(AES256.decrypt(emp.getPassword()));
		return emp;
	}

}
