package fileupload.progress;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class FileUploadProgress
 */
@WebServlet("/progress")
public class FileUploadProgress extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileUploadProgress() {
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
		boolean isSessionNull = false;

		HttpSession session = request.getSession(true);
		if(session == null) {
			isSessionNull = true;
		}

		long bytesRead = 0;
		long contentLength = 0;
		int percentDone = 0;

		FileUploadProgressListener listener = (FileUploadProgressListener) session.getAttribute("fileUploadProgressListener");
		if(listener == null) {
			isSessionNull = true;
		} else {
			bytesRead = listener.getTheBytesRead();
			contentLength = listener.getTheContentLength();
			percentDone = listener.getPercentDone();
		}
		System.out.println("File upload listener bytesRead: "+bytesRead);
		System.out.println("File upload listener contentLength: "+contentLength);
		System.out.println("File upload listener percentDone: "+percentDone);

		StringBuffer out = new StringBuffer();
		out.append("{");
		if(isSessionNull) {
			out.append("\"isSuccess\":false,");
		} else {
			out.append("\"isSuccess\":true,");
		}
		out.append("\"bytesRead\" : "+bytesRead+",");
		out.append("\"contentLength\" : "+contentLength+",");
		out.append("\"percentDone\" : "+percentDone+"");
		out.append("}");
		System.out.println("File upload Progress Check result: "+out.toString());

		PrintWriter writer = response.getWriter();
		writer.println(out.toString());
		writer.flush();
	}

}
