package ltts.ems.com.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="EmployeeDetailsTable")
public class EmployeeDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int empId;
	private String firstName;
	private String lastName;
	@Column(unique = true)
	private String userName;
	private String password;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateOfJoining;
	private String gender;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateOfBirth;
	private String role;
	private String departmentName;
	@Column(length = 45, nullable = true)
	private String photo;
	
	
	public EmployeeDetails() {
		super();
		// TODO Auto-generated constructor stub
	}


	public EmployeeDetails(int empId, String firstName, String lastName, String userName, String password,
			LocalDate dateOfJoining, String gender, LocalDate dateOfBirth, String role, String departmentName,
			String photo) {
		super();
		this.empId = empId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.password = password;
		this.dateOfJoining = dateOfJoining;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.role = role;
		this.departmentName = departmentName;
		this.photo = photo;
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


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public LocalDate getDateOfJoining() {
		return dateOfJoining;
	}


	public void setDateOfJoining(LocalDate dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}


	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}


	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}


	public String getDepartmentName() {
		return departmentName;
	}


	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}


	public String getPhoto() {
		return photo;
	}


	public void setPhoto(String photo) {
		this.photo = photo;
	}
	

	@Transient
	public String getPhotoImagePath(){
		if(photo == null || empId==0 ) return null;
			return "/Employee-Photos/" + empId + "/" + photo;
	}



	@Override
	public String toString() {
		return "EmployeeDetails [empId=" + empId + ", firstName=" + firstName + ", lastName=" + lastName + ", userName="
				+ userName + ", password=" + password + ", dateOfJoining=" + dateOfJoining + ", gender=" + gender
				+ ", dateOfBirth=" + dateOfBirth + ", role=" + role + ", departmentName=" + departmentName + ", photo="
				+ photo + "]";
	}









}
	
	
	