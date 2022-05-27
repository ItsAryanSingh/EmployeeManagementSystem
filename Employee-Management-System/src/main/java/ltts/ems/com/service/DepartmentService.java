package ltts.ems.com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ltts.ems.com.model.Department;
import ltts.ems.com.repository.DepartmentRepository;

@Service
public class DepartmentService {

	@Autowired
	DepartmentRepository departmentDao;

	/**
	 * Method to save new department details
	 * @param department Contains details of new department to be added
	 * @return Saves department into the database
	 */

	public Department saveDepartment(Department department)
	{
		return departmentDao.save(department);
	}


	public Department updateDepartment(Department department)
	{
		return departmentDao.save(department);
	}

	/**
	 * Method to return all existing departments
	 * @return List of all existing departments
	 */

	public List<Department> getAllDepartments(){
		return departmentDao.findAll();
	}

	/**
	 * Method to return particular department using unique department id
	 * @param deptId Fetches department details using unique department id
	 * @return Department details using department id
	 */

	public Department getDepartmentById(int deptId)
	{
		return departmentDao.getById(deptId);
	}

	public void deleteByid(int id) {
		departmentDao.deleteById(id);
		return;
	}

}
