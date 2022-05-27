package ltts.ems.com.repository;


import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ltts.ems.com.model.EmployeeDetails;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeDetails, Integer> {
	
    public EmployeeDetails findByUserNameAndPassword(String userName, String password);
}
  