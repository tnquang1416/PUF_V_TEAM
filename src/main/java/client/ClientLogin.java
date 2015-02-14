package client;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ClientLogin
 */
public class ClientLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private String APP_KEY = "aje8p4qsxs9h4bo";
	private String APP_SECRET = "s91c7fj5ddewd94";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClientLogin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String pageTitle = "LOGIN";
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title>" + pageTitle + "</title>");
		out.println("</head>");
		out.println("</html>");
		out.println("<body>");
		
		out.println("<br>");
		out.println("<br>");
		out.println("<br>");
		
		out.println("<table align='center' border='1' cellpadding='5' cellspacing='0' width='400'>");
	    out.println("<tr bgcolor='#CCCCFF' align='center' valign='center' height='20'>");
	    out.println("  <td colspan ='2'><h3>" + pageTitle + "</h3></td>");
	    out.println("</tr>");
	    out.println("<tr>");
	    out.println("<td>Access dropbox</td>" );
		out.println("<td> <button onclick=\"window.location.href='https://www.dropbox.com/1/oauth2/authorize?client_id=" + this.APP_KEY 
				+ "&response_type=code&redirect_uri=http://localhost:8080/drive/dropbox/token'\">Login Dropbox</button></td>");
		out.println("</tr>");
	    out.println("<tr>");
	    out.println("<td>Access google</td>");
	    out.println("<td> <button type='button'>Login Google</button></td>");
		out.println("</tr>");
	    out.println("</table>");
	    out.println("<br>");
	    out.println("</body");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
