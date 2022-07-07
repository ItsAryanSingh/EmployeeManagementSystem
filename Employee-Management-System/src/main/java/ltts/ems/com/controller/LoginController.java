package ltts.ems.com.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import ltts.ems.com.model.EmployeeDetails;
import ltts.ems.com.service.EmployeeService;

@RestController
public class LoginController {

	@Autowired
	EmployeeService emp;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	String username;
	String password;


	/**
	 * Method to control the login method 
	 * Login username and password authorization performed here
	 * @return 
	 */

	@RequestMapping("/LoginPage")
	public ModelAndView Login()
	{
		return new ModelAndView("LoginPage");
	}


	/**
	 * Method to verify the user is admin or employee
	 * @param request Server side authorization 
	 * @return
	 */


	@RequestMapping(value="LoginPage" , method=RequestMethod.POST)
	public ModelAndView LoginAuth(HttpServletRequest request)
	{
		
		username=request.getParameter("usrnm");
		password=request.getParameter("psw");
		//String expectedPassword = emp.getEmployeeByUsername(username).getPassword();
		EmployeeDetails theemployee = null;
		//if(passwordEncoder.matches(password, expectedPassword)) {
		theemployee=emp.findByUserNameAndPassword(username,password);
		//}
		if(theemployee==null)
		{
			return new ModelAndView("LoginError");
		}
		
		HttpSession session = request.getSession();
		session.setAttribute("empId", theemployee.getEmpId());
		if(theemployee.getRole().equals("ADMIN")) {
			session.setAttribute("isAdmin", true);
		}
		else {
			session.setAttribute("isAdmin", false);
		}
		if(theemployee.getRole().equals("ADMIN"))
		{
			return new ModelAndView("redirect:/Admin/AdminDashboard");
		}
		else if(theemployee.getRole().equals("admin"))
		{
			return new ModelAndView("redirect:/Admin/AdminDashboard");
		}
		else if(theemployee.getRole().equals("Admin"))
		{
			return new ModelAndView("redirect:/Admin/AdminDashboard");
		}
		else if(theemployee.getRole().equals("Employee")||(theemployee.getRole().equals("employee"))||(theemployee.getRole().equals("EMPLOYEE")))
		{
			return new ModelAndView("redirect:/Employee/"+theemployee.getEmpId()+"/Dashboard");
		}
		else
		{
			return new ModelAndView("redirect:/LoginError");
		}
	}
	

	@RequestMapping("/Admin")
	public ModelAndView mv(HttpServletRequest request)
	{
		username=request.getParameter("usrnm");
		password=request.getParameter("psw");
		String expectedPassword = emp.getEmployeeByUsername(username).getPassword();
		EmployeeDetails theemployee = null;
		if(passwordEncoder.matches(password, expectedPassword)) {
			theemployee=emp.findByUserNameAndPassword(username,expectedPassword);
		}
		return new ModelAndView("Admin/AdminDashboard/"+theemployee.getEmpId()+"{id}");
	}

	@RequestMapping("/LogoutPage")
	public ModelAndView mav(HttpServletRequest request)
	{	
		request.getSession().invalidate();
		return new ModelAndView("redirect:/LoginPage");
	}
}