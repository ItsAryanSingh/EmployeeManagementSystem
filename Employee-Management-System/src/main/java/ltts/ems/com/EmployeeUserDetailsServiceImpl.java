package ltts.ems.com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import ltts.ems.com.model.EmployeeDetails;
import ltts.ems.com.repository.EmployeeRepository;

public class EmployeeUserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	EmployeeRepository employeerepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		EmployeeDetails user = employeerepository.getUserByUserName(username);
		System.out.println("/n/n/n?>>>>>");
		System.out.println(user);
		if(user==null) {
			throw new UsernameNotFoundException("User Not Found");
		}
		employeeUserDetails  emplUserDet = new employeeUserDetails(user);
		return emplUserDet;
	}

}
