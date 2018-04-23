package start;

import java.sql.SQLException;
import java.util.logging.Logger;

import bll.CustomerBLL;
import presentation.View;

public class Start {
	protected static final Logger LOGGER = Logger.getLogger(Start.class.getName());

	private static CustomerBLL customerBLL = new CustomerBLL();
	
	public static void main(String[] args) throws SQLException {
		new View();
		
//		customerBLL.findAll();
		

//		Student student = new Student("dummy name", "dummy address", "dummy@address.co", 12);
//
//		StudentBLL studentBll = new StudentBLL();
//		int id = studentBll.insertStudent(student);
//		if (id > 0) {
//			studentBll.findStudentById(id);
//		}
//		
//		
//		// Generate error
//		try {
//			studentBll.findStudentById(1);
//		} catch (Exception ex) {
//			LOGGER.log(Level.INFO, ex.getMessage());
//		}
//		
//		
//		//obtain field-value pairs for object through reflection
//		ReflectionExample.retrieveProperties(student);
	}
	
	

}
