package ltts.ems.com.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
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
		EmployeeDetails theemployee=emp.findByUserNameAndPassword(username,password);
		if(theemployee==null)
		{
			return new ModelAndView("LoginError");
		}
		else if(theemployee.getRole().equals("ADMIN"))
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
		EmployeeDetails theemployee=emp.findByUserNameAndPassword(username,password);
		return new ModelAndView("Admin/AdminDashboard/"+theemployee.getEmpId()+"{id}");
	}

	@RequestMapping("/Logout")
	public ModelAndView mav()
	{
		return new ModelAndView("redirect:/LoginPage");
	}
}
