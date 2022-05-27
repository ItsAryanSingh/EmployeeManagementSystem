package ltts.ems.com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import ltts.ems.com.model.EmployeeDetails;
import ltts.ems.com.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeRepository employeerepository;

	@Override
	public List<EmployeeDetails> getAllEmployees() {
		return employeerepository.findAll();

	}

	@Override
	public Page<EmployeeDetails> findPaginated(int pageNo, int pageSize) {
		PageRequest pageable = PageRequest.of(pageNo - 1, pageSize);
		return this.employeerepository.findAll(pageable);
	}

	@Override
	public EmployeeDetails saveEmployee(EmployeeDetails employee) {

		return employeerepository.save(employee);

	}

	@Override
	public EmployeeDetails getEmployeeById(int id) {

		return employeerepository.findById(id).get();
	}

	@Override
	public void deleteEmployeeById(int id) {

		this.employeerepository.deleteById(id);

	}

	@Override
	public EmployeeDetails findByUserNameAndPassword(String userName, String password) {

		return employeerepository.findByUserNameAndPassword(userName, password);
	}

}
