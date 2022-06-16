
   
package ltts.ems.com.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import ltts.ems.com.model.EmployeeDetails;

@Service
public interface EmployeeService {

	public EmployeeDetails findByUserNameAndPassword(String userName,String password);


	/**
	 * Method to return list of existing employees
	 * @return List of existing employees
	 */
	List<EmployeeDetails> getAllEmployees();

	
	
	

	/**
	 * Method to implement pagination feature
	 * @param pageNo Contains page number value
	 * @param pageSize Contains number of records to be displayed 
	 * @return List of existing employees
	 */
	Page<EmployeeDetails> findPaginated(int pageNo,int pageSize);

	/**
	 * Method to save employee details
	 * @param employee Contains details of the employee 
	 * @return Saves employee details into database
	 */
	EmployeeDetails saveEmployee(EmployeeDetails employee);

	/**
	 * Method to return particular employee detail using employee id
	 * @param id Unique employee id to fetch particular employee details
	 * @return Particular employee details using id
	 */
	EmployeeDetails getEmployeeById(int id);


	/**
	 * Method to delete employee details record using employee id
	 * @param id Unique employee id to delete particular employee details
	 */
	void deleteEmployeeById(int id);


}