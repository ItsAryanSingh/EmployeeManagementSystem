package ltts.ems.com.controller;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.VerticalAlign;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.slf4j.Logger;

import java.util.List;
//import org.springframework.stereotype.*;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
//import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
//import org.springframework.http.HttpStatus;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

import ltts.ems.com.model.Attendance;
import ltts.ems.com.model.Department;
import ltts.ems.com.model.EmployeeDetails;
import ltts.ems.com.repository.AttendanceRepository;
import ltts.ems.com.repository.DepartmentRepository;
import ltts.ems.com.service.AttendanceService;
import ltts.ems.com.service.DepartmentService;
import ltts.ems.com.service.EmployeeService;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;

@Controller
public class EmployeeController {

	@Autowired
	EmployeeService employeeservice;
	@Autowired
	AttendanceService attendance_service;
	@Autowired
	AttendanceRepository ad;
	@Autowired
	DepartmentService departmentservice;
	@Autowired
	DepartmentRepository dp;


	 private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	// =======================================
	// ==========ADMIN PAGES==================


	/**
	 * Admin side page
	 * Admin Dashboard Page
	 * Method for displaying all existing employees details
	 * @param model contains attribute of employees list
	 * @return all the existing records of employee from database
	 * @throws JRException 
	 * @throws FileNotFoundException 
	 */


	@GetMapping("/Admin/AdminDashboard")

	public String viewHomePage(Model model) throws FileNotFoundException, JRException {

		// shows employee repository
		model.addAttribute("listEmployees", employeeservice.getAllEmployees());
		System.out.print(employeeservice.getAllEmployees());
		return findPaginated(1, model);
	}
	
	
	@GetMapping("/Admin/AdminDashboard/PDF")

	public String pdfGen(Model model) throws FileNotFoundException, JRException {

		// shows employee repository
		model.addAttribute("listEmployees", employeeservice.getAllEmployees());
		System.out.print(employeeservice.getAllEmployees());
		
		
		//trial
		List<EmployeeDetails> listEmployees = employeeservice.getAllEmployees();
		System.out.print("\n Trying the pdf");
		model.addAttribute("listEmployees", listEmployees);
		File file = ResourceUtils.getFile("/Users/itsaryansingh/Library/Containers/com.apple.mail/Data/Library/Mail Downloads/2F1BBA4E-8819-4838-865B-F3AF7A450607/Employee-Management-System/src/employeestrial.jrxml");
		JasperReport jasperReport= JasperCompileManager.compileReport(file.getAbsolutePath());
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listEmployees);
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("Created by Aryan Singh", "Aryan");
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
		JasperExportManager.exportReportToPdfFile(jasperPrint,"EmployeJasper.pdf");
		JasperExportManager.exportReportToHtmlFile(jasperPrint,"EmployeeJasper.html");
		return "AdminDashboard";
	}
	
	@GetMapping("/Admin/AdminDashboard/PDFA")

	public String pdfGenA(Model model) throws IOException{

		// shows employee repository
		model.addAttribute("listEmployees", employeeservice.getAllEmployees());
		System.out.print(employeeservice.getAllEmployees());
		
		
		//trial
		List<EmployeeDetails> listEmployees = employeeservice.getAllEmployees();
		PDDocument document = new PDDocument();
		PDPage firstPage = new PDPage();
		document.addPage(firstPage);
		
		int pageHeight = (int) firstPage.getTrimBox().getHeight();
		//int pageWidth = (int) firstPage.getTrimBox().getWidth();
		
		PDPageContentStream contentStream = new PDPageContentStream(document, firstPage);
		
		contentStream.setStrokingColor(Color.DARK_GRAY);
		contentStream.setLineWidth(1);
		
		int initX = 50;
		int initY = pageHeight-50;
		int cellHeight = 30;
		int cellWidth = 105;
		
		int colCount = 8;
		int rowCount = listEmployees.size();
		
		for(int i = 0; i<rowCount;i++) {
			for(int j = 0; j<colCount; j++) {
				if(j==0) {
					contentStream.addRect(initX, initY, 20, -cellHeight);
				}
				else if((j==2)||(j==4)) {
					contentStream.addRect(initX, initY, 50, -cellHeight);
				}
				else if((j==3)||(j==5)||(j==6)) {
					contentStream.addRect(initX, initY, 60, -cellHeight);
				}
				else {
				contentStream.addRect(initX, initY, cellWidth, -cellHeight);
				}
				
				contentStream.beginText();
				contentStream.newLineAtOffset(initX+2, initY-cellHeight+10);
				contentStream.setFont(PDType1Font.TIMES_ROMAN,8);
				if(i==0) {
					//contentStream.setNonStrokingColor(Color.BLUE);
					if(j==0) {
						contentStream.showText("ID");
					}
					else if(j==1) {
						contentStream.showText("Name");
					}
					else if(j==2) {
						contentStream.showText("User Name");
					}
					else if(j==3) {
						contentStream.showText("Date of Join");
					}
					else if(j==4) {
						contentStream.showText("Gender");
					}
					else if(j==5) {
						contentStream.showText("Date of Birth");
					}
					else if(j==6) {
						contentStream.showText("Role");
					}
					else if(j==7) {
						contentStream.showText("Department");
					}
				}
				else{
					contentStream.setNonStrokingColor(Color.BLUE);
					if(j==0) {
						contentStream.showText(String.valueOf(listEmployees.get(i).getEmpId()));
					}
					else if(j==1) {
						contentStream.showText(listEmployees.get(i).getFirstName()+" "+listEmployees.get(i).getLastName());
					}
					else if(j==2) {
						contentStream.showText(listEmployees.get(i).getUserName());
					}
					else if(j==3) {
						contentStream.showText(String.valueOf(listEmployees.get(i).getDateOfJoining()));
					}
					else if(j==4) {
						contentStream.showText(listEmployees.get(i).getGender());
					}
					else if(j==5) {
						contentStream.showText(String.valueOf(listEmployees.get(i).getDateOfBirth()));
					}
					else if(j==6) {
						contentStream.showText(listEmployees.get(i).getRole());
					}
					else if(j==7) {
						contentStream.showText(listEmployees.get(i).getDepartmentName());
					}
				}
				contentStream.endText();
				if(j==0) {
					initX+=20;
				}
				else if((j==2)||(j==4)) {
					initX+=50;
				}
				else if((j==3)||(j==5)||(j==6)) {
					initX+=60;
				}
				else {
					initX+=cellWidth;
				}
			}
			initX=50;
			initY-=cellHeight;
		}
		
		contentStream.stroke();
		contentStream.close();
		document.save("/Users/itsaryansingh/Library/Containers/com.apple.mail/Data/Library/Mail Downloads/2F1BBA4E-8819-4838-865B-F3AF7A450607/Employee-Management-System/EmployeeList.pdf");
		document.close();
		System.out.println("CreatedPDF succesfully");
		
		return "AdminDashboard";
	}
	
	@GetMapping("/Admin/AdminDashboard/DOCX")

	public String docxGen(Model model) throws IOException{

		// shows employee repository
		model.addAttribute("listEmployees", employeeservice.getAllEmployees());
		System.out.print(employeeservice.getAllEmployees());
		
		
		//trial
		List<EmployeeDetails> listEmployees = employeeservice.getAllEmployees();
		System.out.print("\n Trying the docx");
		//try {
			  XWPFDocument document = new XWPFDocument();
		      FileOutputStream out = new FileOutputStream(new File("/Users/itsaryansingh/Library/Containers/com.apple.mail/Data/Library/Mail Downloads/2F1BBA4E-8819-4838-865B-F3AF7A450607/Employee-Management-System/EmployeeList.docx"));
		      XWPFParagraph paragraph = document.createParagraph();
		      XWPFRun run = paragraph.createRun();
		      run.setBold(true);
		      run.setFontSize(30);
		      run.setText("\t \t \t Employee List");
		      //create table
		      XWPFTable table = document.createTable();
		      //create first row
		      XWPFTableRow tableRowOne = table.getRow(0);
		      tableRowOne.getCell(0).setText("Employee ID");
		      tableRowOne.addNewTableCell().setText("Name");
		      tableRowOne.addNewTableCell().setText("User Name");
		      tableRowOne.addNewTableCell().setText("Date Of Join");
		      tableRowOne.addNewTableCell().setText("Gender");
		      tableRowOne.addNewTableCell().setText("Date of Birth");
		      tableRowOne.addNewTableCell().setText("Role");
		      tableRowOne.addNewTableCell().setText("Department");
		      
		      
		      int i = 0;
		      while(i<listEmployees.size()) {
		    	  XWPFTableRow tableRowTwo = table.createRow();
			      tableRowTwo.getCell(0).setText(String.valueOf(listEmployees.get(i).getEmpId()));
			      tableRowTwo.getCell(1).setText(listEmployees.get(i).getFirstName()+" "+listEmployees.get(i).getLastName());
			      tableRowTwo.getCell(2).setText(listEmployees.get(i).getUserName());
			      tableRowTwo.getCell(3).setText(String.valueOf(listEmployees.get(i).getDateOfJoining()));
			      tableRowTwo.getCell(4).setText(listEmployees.get(i).getGender());
			      tableRowTwo.getCell(5).setText(String.valueOf(listEmployees.get(i).getDateOfBirth()));
			      tableRowTwo.getCell(6).setText(listEmployees.get(i).getRole());
			      tableRowTwo.getCell(7).setText(listEmployees.get(i).getDepartmentName());
			      i+=1;
		      }
			  document.write(out);
		      out.close();
		      document.close();
		      System.out.println("createparagraph.docx written successfully");
		return "AdminDashboard";
	}
	
	@GetMapping("/Admin/AdminDashboard/XLSX")

	public String xlsxGen(Model model) throws IOException{
		// shows employee repository
		model.addAttribute("listEmployees", employeeservice.getAllEmployees());
		System.out.print(employeeservice.getAllEmployees());
				
				
		//trial
		List<EmployeeDetails> listEmployees = employeeservice.getAllEmployees();
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Contacts");
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 14);
		headerFont.setColor(IndexedColors.RED.getIndex());

		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);
		
		Row headerRow = sheet.createRow(0);
		
		headerRow.createCell(0).setCellValue("Employee ID");
		//headerRow.createCell(0).setCellStyle(headerCellStyle);
		headerRow.createCell(1).setCellValue("Name");
		//headerRow.createCell(1).setCellStyle(headerCellStyle);
		headerRow.createCell(2).setCellValue("User Name");
		//headerRow.createCell(2).setCellStyle(headerCellStyle);
		headerRow.createCell(3).setCellValue("Date Of Join");
		//headerRow.createCell(3).setCellStyle(headerCellStyle);
		headerRow.createCell(4).setCellValue("Gender");
		//headerRow.createCell(4).setCellStyle(headerCellStyle);
		headerRow.createCell(5).setCellValue("Date of Birth");
		//headerRow.createCell(5).setCellStyle(headerCellStyle);
		headerRow.createCell(6).setCellValue("Role");
		//headerRow.createCell(6).setCellStyle(headerCellStyle);
		headerRow.createCell(7).setCellValue("Department");
		//headerRow.createCell(7).setCellStyle(headerCellStyle);
		
		int i = 0;
	    while(i<listEmployees.size()) {
	    	Row newRow = sheet.createRow(i+1);
	    	  newRow.createCell(0).setCellValue(String.valueOf(listEmployees.get(i).getEmpId()));
	    	  newRow.createCell(1).setCellValue(listEmployees.get(i).getFirstName()+" "+listEmployees.get(i).getLastName());
	    	  newRow.createCell(2).setCellValue(listEmployees.get(i).getUserName());
	    	  newRow.createCell(3).setCellValue(String.valueOf(listEmployees.get(i).getDateOfJoining()));
	    	  newRow.createCell(4).setCellValue(listEmployees.get(i).getGender());
	    	  newRow.createCell(5).setCellValue(String.valueOf(listEmployees.get(i).getDateOfBirth()));
	    	  newRow.createCell(6).setCellValue(listEmployees.get(i).getRole());
	    	  newRow.createCell(7).setCellValue(listEmployees.get(i).getDepartmentName());
		      i+=1;
	      }
	    
	    for ( i = 0; i <= 7; i++) {
	    	  sheet.autoSizeColumn(i);
	    }
	    
	    FileOutputStream fileOut = new FileOutputStream("EmployeeList.xlsx");
	    workbook.write(fileOut);
	    fileOut.close();
		return "AdminDashboard";
		
	}
	/**
	 * Admin side page -- Admin Dashboard Page
	 * @param pageNo To display the group of records from the database on the particular page number
	 * @param model Contains attributes for employees list & page navigation accordingly
	 * @return the group of existing records on that particular page number
	 * @throws JRException 
	 * @throws FileNotFoundException 
	 */


	@GetMapping("/Admin/AdminDashboard/LIST/{pageNo}")
	public String findPaginated(@PathVariable(value = "pageNo") int pageNo, Model model) throws JRException, FileNotFoundException {

		int pageSize = 4;
		
		System.out.println("ADMIN DASHBOARD PAGE -- VIEW ALL EMPLOYEES LIST");	

		Page<EmployeeDetails> page = employeeservice.findPaginated(pageNo, pageSize);
		//List<EmployeeDetails> listEmployees = page.getContent();

		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		System.out.println("ADMIN DASHBOARD PAGE -- VIEW ALL EMPLOYEES LIST");		
		logger.error("Admin dashboard page is presented");
		return "AdminDashboard";
		
	}
	


	/**
	 * Admin side page -- Add new employee page
	 * Display form for adding new employee records
	 * @param model Contains the employee detail's values
	 * @param modelnew Contains the list of existing departments from the database
	 * @return Accepts the entries in the form & saves it
	 */


	@GetMapping("/Admin/AddEmployee")
	public String showNewEmployeeForm(Model model,Model modelnew) {

		EmployeeDetails employee = new EmployeeDetails();
		model.addAttribute("employee", employee);
		modelnew.addAttribute("departments", getDepartments());
		System.out.println("ADMIN SIDE ADD NEW EMPLOYEE PAGE");
		return "AdminAddNewEmployee";
	}



	/**
	 * Admin side page
	 * Method to save the employee details from form to database records
	 * @param employee Contains employee's details filled in the form
	 * @param multipartFile To save the photograph
	 * @param model 
	 * @return Saves the data in database & then redirects to dashboard page
	 * @throws IOException throws exception when unable to save the photograph
	 */


	@PostMapping("/saveEmployee")
	public String saveEmployee(@ModelAttribute("employee") EmployeeDetails employee,
			@RequestParam("fileImage)") MultipartFile multipartFile,Model model) throws IOException 
	{

		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		employee.setPhoto(fileName);
		EmployeeDetails savedEmployee = employeeservice.saveEmployee(employee);
		String uploadDir = "./Employee-Photos/" + savedEmployee.getEmpId();

		Path uploadPath = Paths.get(uploadDir);

		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}

		try (InputStream inputStream = multipartFile.getInputStream()) {
			Path filePath = uploadPath.resolve(fileName);
			System.out.println(filePath.toFile().getAbsolutePath());
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new IOException("Could not save uploaded file: " + fileName);

		}
		return "redirect:/Admin/AdminDashboard";

	}



	/**
	 * Admin side page
	 * Display the form for updating existing employee details
	 * @param id Contains unique employee id to fetch the respective details from database
	 * @param model Contains the existing details of the employee fetched from the database
	 * @param modelnew Contains the existing departments in the database.
	 * @return Saves the form
	 */

	@GetMapping("/Admin/{id}/UpdateEmployee")
	public String showFormForUpdate(@PathVariable(value = "id") int id, Model model, Model modelnew) {

		// get employee from the service
		EmployeeDetails addemployee = employeeservice.getEmployeeById(id);

		// set employee as a model attribute to pre-populate the form
		model.addAttribute("employee", addemployee);
		modelnew.addAttribute("departments", getDepartments());
		employeeservice.saveEmployee(addemployee);
		System.out.println("ADMIN SIDE UPDATE EMPLOYEE DETAILS PAGE ");
		return "AdminUpdateEmployee";

	}




	/**
	 * Admin side page
	 * Method for saving updated details after changes of existing employee record
	 * @param employee To fetch existing details of the employee
	 * @param m
	 * @param multipartFile To save photograph
	 * @return Saves the data in database & redirects to the dashboard page
	 * @throws IOException throws exception when unable to save the photograph
	 */


	@PostMapping("/updateEmployee")
	public String updateHandler(@ModelAttribute("employee") EmployeeDetails employee, Model m,
			@RequestParam("fileImage") MultipartFile multipartFile) throws IOException {

		try {
			// getting old employee details
			EmployeeDetails oldEmployee = employeeservice.getEmployeeById(employee.getEmpId());


			// image
			if (!multipartFile.isEmpty()) {

				String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
				employee.setPhoto(fileName);
				EmployeeDetails savedEmployee = employeeservice.saveEmployee(employee);
				String uploadDir = "./employee-photos/" + savedEmployee.getEmpId();

				Path uploadPath = Paths.get(uploadDir);

				if (!Files.exists(uploadPath)) {
					Files.createDirectories(uploadPath);

				}

				try (InputStream inputStream = multipartFile.getInputStream()) {
					Path filePath = uploadPath.resolve(fileName);
					System.out.println(filePath);
					System.out.println(filePath.toFile().getAbsolutePath());
					System.out.println("above is path");
					Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e) {
					throw new IOException("Could not save uploaded file: " + fileName);
				}

			} else {
				employee.setPhoto(oldEmployee.getPhoto());

			}
			employeeservice.saveEmployee(employee);

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Employee id: " + employee.getEmpId());
		System.out.println("Employee Name: " + employee.getFirstName());
		return "redirect:/Admin/AdminDashboard";
	}



	/**
	 * Admin side page
	 * Method for deleting existing employee record
	 * @param id Contains unique employee id to fetch the respective details from database
	 * @return Deletes the particular employee record from datatbase & redirects to the dashboard page
	 */

	@GetMapping("/DeleteEmployee/{id}")
	public String deleteEmployee(@PathVariable(value = "id") int id) {
		this.employeeservice.deleteEmployeeById(id);		
		System.out.println("ADMIN SIDE DELETE EMPLOYEE WINDOW");
		return "redirect:/Admin/AdminDashboard";
	}




	/**
	 * Admin side page 
	 * Method to display the list of existing departments
	 * @return list of existing departments in database
	 */

	private List<Department> getDepartments() {

		return departmentservice.getAllDepartments();
	}


	@GetMapping("/Admin/ViewDepartmentList")
	public String getAllDepartments(Model model)
	{
		List<Department> departments= departmentservice.getAllDepartments();
		model.addAttribute("departments",departments);
		System.out.println("ADMIN SIDE VIEW ALL DEPARTMENTS PAGE");
		return "AdminViewAllDepartments";
	}




	/**
	 * Admin side page -- Add new department
	 * @param model Contains list of existing departments
	 * @return List of existing departments
	 */

	@RequestMapping(value = "/Admin/AddDepartment")
	public String adminAddDepartment(Model model) {
		List<Department> departments= departmentservice.getAllDepartments();
		model.addAttribute("departments",departments);
		System.out.println("ADMIN SIDE ADD NEW DEPARTMENT WINDOW");
		return "AdminAddNewDepartment";
	}



	/**
	 * Admin side page 
	 * @param department Contains the form details to save the department
	 * @return Saves the department in database & redirects to the dashboard page
	 * @throws IOException 
	 */

	@PostMapping("/SaveNewDepartment")
	public String saveNewDepartment(@ModelAttribute("department") Department department) throws IOException
	{
		dp.save(department);
		return "redirect:/Admin/AdminDashboard";
	}




	/**
	 * Admin side page 
	 * Method to display all the attendance requests 
	 * @param model Displays all attendance requests 
	 * @return List of attendance requests 
	 */

	@GetMapping("/Admin/ViewAttendanceRequests")
	public String attendance(Model model) {
		model.addAttribute("listAttendance", attendance_service.getAllAttendance());
		System.out.print(attendance_service.getAllAttendance());
		System.out.println("ADMIN SIDE ATTENDANCE PAGE");
		return "AdminViewAllAttendanceRequests";
	}

	@GetMapping("/Admin/ViewAttendanceRequests/new")
	public String newAttendanceForm(Model model) {
		Attendance attendance = new Attendance();
		model.addAttribute("attendance", attendance);
		return "RegularizeAttendance";
	}

	@PostMapping("/saveAttendance")
	public String saveAttendance(@ModelAttribute("attendance") Attendance attendance) {
		// save attendance to db
		//Attendance savedAttendance = attendance_service.insertAttendance(attendance);
		return "redirect:/Admin/ViewAttendanceRequests";
	}

	/**
	 * Admin side page
	 * Admin can accept or reject the attendance
	 * @param id Contains unique employee id to fetch the respective details from database
	 * @return Changes the pending status of the attendance 
	 */

	@GetMapping("/Admin/ViewAttendanceRequests/{id}/accept")
	public String acceptAttendance(@PathVariable(value = "id") int id) {
		Attendance attendance = attendance_service.getAttendanceById(id);
		attendance.setStatus("Accepted");
		attendance_service.updateAttendance(attendance);
		System.out.println("ADMIN SIDE ATTENDANCE PAGE -- APPROVE/REJECT ATTENDANCE");
		return "redirect:/Admin/ViewAttendanceRequests";

	}

	@GetMapping("/Admin/ViewAttendanceRequests/{id}/reject")
	public String rejectAttendance(@PathVariable(value = "id") int id) {
		Attendance attendance = attendance_service.getAttendanceById(id);
		attendance.setStatus("Rejected");
		attendance_service.updateAttendance(attendance);
		return "redirect:/Admin/ViewAttendanceRequests";

	}







	// =============================================
	// ================EMPLOYEE PAGES==============



	/**
	 * Employee side page -- Employee dashboard page
	 * @param id Contains unique employee id to fetch the respective details from database
	 * @param model Display the employee details
	 * @return Display the employee details
	 */


	@GetMapping("/Employee/{id}/Dashboard")
	public String employeeDashboard(@PathVariable(value = "id") int id, Model model) {
		EmployeeDetails employee = employeeservice.getEmployeeById(id);
		model.addAttribute("employee", employee);
		System.out.println("EMPLOYEE SIDE DASHBOARD PAGE -- VIEW SELF DETAILS");
		return "EmployeeDashboard";
	}


	@GetMapping("/EmployeeDashboard")
	public String employeeDashboard(Model model) {

		return "EmployeeDashboard";
	}



	/**
	 * Employee side page -- Employee attendance page
	 * @param id Contains unique employee id to fetch the respective details from database
	 * @param model Attributes for employee id, name & for regularizing the attendance
	 * @return Saves attendance from employee side
	 */


	@GetMapping("/Employee/{id}/RegularizeAttendance")
	public String regularizeAttendance(@PathVariable(value = "id") int id, Model model) {
		EmployeeDetails employee = employeeservice.getEmployeeById(id);
		model.addAttribute("employee", employee);
		Attendance attendance = new Attendance();
		model.addAttribute("attendance", attendance);
		System.out.println("EMPLOYEE SIDE ATTENDANCE REGULARIZATION PAGE");
		return "Employee-RegularizeAttendance";
	}


	/**
	 * Method to save & submit the attendance
	 * @param attendance Model to submit the attendance
	 * @return Saves attendance & redirects to the dashboard page 
	 */

	@PostMapping("/SaveAttendanceDashboard")
	public String saveAttendanceDashboard(@ModelAttribute("attendance") Attendance attendance) {

		// save attendance to db
		Attendance savedAttendance = attendance_service.insertAttendance(attendance);
		return "redirect:/Employee/" + savedAttendance.getEmpId() + "/Dashboard";
	}



	/**
	 * Employee side page -- Employee can view all attendance requests
	 * @param id Contains unique employee id to fetch the respective details from database
	 * @param model All existing attendance requests
	 * @return List of attendance requests of that employee
	 */

	@GetMapping("/Employee/{id}/EmployeeAttendanceRequests")
	public String findAttendanceByEmpId(@PathVariable(value = "id") int id, Model model) {
		EmployeeDetails employee = employeeservice.getEmployeeById(id);
		model.addAttribute("employee", employee);
		List<Attendance> attendances = ad.findAttendanceByEmpId(id);
		model.addAttribute("attendances", attendances);
		System.out.println("EMPLOYEE SIDE VIEW ATTENDANCE PAGE");
		return "EmployeeViewAllAttendance";
	}


	/**
	 * Employee side page -- Update self details
	 * Method to update self details from employee side
	 * @param id Contains unique employee id to fetch the respective details from database
	 * @param model Contains attribute employee to fetch existing details
	 * @param modelnew Contains all list of departments
	 * @return Save the changes
	 */

	@GetMapping("/Employee/{id}/Update")
	public String updateEmpDashboard(@PathVariable(value = "id") int id, Model model,Model modelnew) {

		// fetch employee data from db, put them into form and update them into form
		EmployeeDetails employee = employeeservice.getEmployeeById(id);

		model.addAttribute("employee", employee);
		modelnew.addAttribute("departments", getDepartments());
		System.out.println("EMPLOYEE SIDE UPDATE DETAILS WINDOW");
		return "Update-Employee";
	}


	@PostMapping("/updateEmployeeDashboard")
	public String updateEmployeeDashboard(@ModelAttribute("employee") EmployeeDetails employee, Model m,
			@RequestParam("fileImage") MultipartFile multipartFile) throws IOException {

		try {
			// getting old employee details
			EmployeeDetails oldEmployee = employeeservice.getEmployeeById(employee.getEmpId());

			// image
			if (!multipartFile.isEmpty()) {
				// file work
				// rewrite
				String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
				employee.setPhoto(fileName);
				EmployeeDetails savedEmployee = employeeservice.saveEmployee(employee);
				String uploadDir = "./Employee-Photos/" + savedEmployee.getEmpId();

				Path uploadPath = Paths.get(uploadDir);

				if (!Files.exists(uploadPath)) {
					Files.createDirectories(uploadPath);
				}

				try (InputStream inputStream = multipartFile.getInputStream()) {

					Path filePath = uploadPath.resolve(fileName);
					System.out.println(filePath);
					System.out.println(filePath.toFile().getAbsolutePath());
					System.out.println("above is path");
					Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e) {
					throw new IOException("UNABLE TO SAVE THE UPLOADED FILE " + fileName);
				}

			} else {
				employee.setPhoto(oldEmployee.getPhoto());

			}
			employeeservice.saveEmployee(employee);

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Employee id: " + employee.getEmpId());
		System.out.println("Employee Name: " + employee.getFirstName());
		return "redirect:/Employee/" + employee.getEmpId() + "/Dashboard";
	}



}
