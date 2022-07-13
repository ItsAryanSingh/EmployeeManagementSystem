package toolKit;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.python.util.PythonInterpreter;

public class JavaMail{
	
	public static void mailCraft(String sendTo, String status, String in, String out) throws IOException {
		File file = new File(System.getProperty("user.dir")+"/ma.txt");
		FileWriter fw = new FileWriter(file);
		PrintWriter pw  = new PrintWriter(fw);
		pw.println(sendTo);
		pw.println(status);
		pw.println(in);
		pw.println(out);
		pw.close();
		PythonInterpreter pythonInterpreter = new PythonInterpreter();
		pythonInterpreter.execfile(System.getProperty("user.dir")+"/target/classes/toolKit/mail.py");
		//pythonInterpreter.close();
		
		}
}