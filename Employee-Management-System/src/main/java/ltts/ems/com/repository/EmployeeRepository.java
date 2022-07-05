package ltts.ems.com.repository;


import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ltts.ems.com.model.EmployeeDetails;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeDetails, Integer> {
	
    public EmployeeDetails findByUserNameAndPassword(String userName, String password);
    
    @Query("select u from EmployeeDetails u where u.userName = :userName")
    public EmployeeDetails getUserByUserName(@Param("userName") String userName);
}
  