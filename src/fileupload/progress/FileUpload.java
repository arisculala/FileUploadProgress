package fileupload.progress;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Servlet implementation class FileUpload
 */
@WebServlet("/fileUpload")
@MultipartConfig(maxFileSize = -1L, maxRequestSize = -1L, fileSizeThreshold = 0)
public class FileUpload extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileUpload() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Checks if the request is multipart
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		System.out.println("Request is multipart: "+isMultipart);

		if(isMultipart) {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);

			FileUploadProgressListener fileUploadProgressListener = new FileUploadProgressListener();
			upload.setProgressListener(fileUploadProgressListener);

			HttpSession session = request.getSession();
			session.setAttribute("fileUploadProgressListener", fileUploadProgressListener);
	
			try {
				// Generate file upload path
				String uploadPath = generateFileUploadLocationPath();

				int uploadFileCnt = 0;
				// Parses the request's content to extract file data
				List<FileItem> formItems = upload.parseRequest(request);
				if (formItems != null && formItems.size() > 0) {
					for (FileItem item : formItems) {
						if(item.isFormField()) {
							//Form fields (ex. textfield, textarea etc)
						} else {
							String fileName = new File(item.getName()).getName();
							System.out.println("Filename to upload: "+fileName);

							if(!fileName.equals("")) {
								if(uploadFileCnt == 0) {
									// creates the directory if it does not exist
									File uploadDir = new File(uploadPath);
									if (!uploadDir.exists()) {
										uploadDir.mkdir();
									}
								}
								uploadFileCnt++;
								
								String filePath = uploadPath + File.separator + fileName;
								File storeFile = new File(filePath);
		
								// saves the file on disk
								item.write(storeFile);
							}
						}
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				System.out.println("ERROR ENCOUNTERED: saving file: "+ex.getMessage());
			}
		}
	}

	/**
	 * Generate file upload directory path
	 * @return
	 */
	private String generateFileUploadLocationPath() {
		//Used user desktop location then create instance of every upload in directory using timeinmillis
		String filepath = "C:\\Users\\arisc\\Desktop\\FILE_UPLOADED" + File.separator + Calendar.getInstance().getTimeInMillis();
		System.out.println("File upload path: "+filepath);
		return filepath;
	}

}
