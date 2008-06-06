package dmhw.servlet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import dmhw.model.*;

public class PrefsControllerServlet extends ControllerServlet {
	private static final long serialVersionUID = -6122606456722720455L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			User user = getUser(request);
//			if (user == User.GUEST) {
//				this.simpleErrRespone(response, "You should be logged in to do so");
//				return;
//			}
			if (ServletFileUpload.isMultipartContent(request)){
				ServletFileUpload fu = new ServletFileUpload(new DiskFileItemFactory());
				List fileItemsList = fu.parseRequest(request);
				for (Object o : fileItemsList) {
					FileItem fileItem = (FileItem)o;
					if (!fileItem.isFormField()) {
						String fn = fileItem.getFieldName();
						if ("css".equals(fn) || "xsl".equals(fn)) {
							
							writeFile(getServletContext().getRealPath("\\custom\\"+user.getUsername()+"."+fn), fileItem.getInputStream());
						}
					}
				}
			}
		} catch (FileUploadException e) {
			internalError(request, response, e);
		}
	}

	private void writeFile(String filename, InputStream inputStream) throws IOException {
		int data;
		File file = new File(filename);
		file.createNewFile();
		OutputStream outputStream = new FileOutputStream(filename);

		while((data=inputStream.read()) != -1)
		   outputStream.write(data);
		 
		inputStream.close();
		outputStream.close();
	}   	  	      	  	    
}