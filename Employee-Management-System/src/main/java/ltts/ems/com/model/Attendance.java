package ltts.ems.com.model;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;

import ltts.ems.com.EmployeeUserDetailsServiceImpl;
import ltts.ems.com.repository.EmployeeRepository;
import ltts.ems.com.service.EmployeeService;
import toolKit.JavaMail;

@Entity
@Table(name="Attendance")
public class Attendance {


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int attId;
	private int empId;
	private String firstName;
	private String lastName;

	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private Date inTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private Date outTime;
	private String  status = "Pending";
	//private String email = employee.getEmail();



	public Attendance() {
	}
	public Attendance(int attId, int empId,String firstName, Date inTime, Date outTime, String status) {
		this.attId = attId;
		this.empId = empId;
		this.firstName=firstName;
		this.inTime = inTime;
		this.outTime = outTime;
		this.status = status;
	}
	public int getAttId() {
		return attId;
	}
	public void setAttId(int attId) {
		this.attId = attId;
	}
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Date getInTime() {
		return inTime;
	}
	public void setInTime(Date inTime) {
		this.inTime = inTime;
	}
	public Date getOutTime() {
		return outTime;
	}
	public void setOutTime(Date outTime) {
		this.outTime = outTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status, String email) {
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
            String in = dateFormat.format(this.inTime);  
            String out = dateFormat.format(this.outTime);  
            System.out.println(email);
            
			JavaMail.mailCraft(email,status, in, out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.status = status;
	}
	@Override
	public String toString() {
		return "Attendance [attId=" + attId + ", empId=" + empId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", inTime=" + inTime + ", outTime=" + outTime + ", status=" + status + "]";
	}





}
