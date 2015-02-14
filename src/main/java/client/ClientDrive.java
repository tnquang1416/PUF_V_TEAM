package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * Servlet implementation class ClientDrive
 */
public class ClientDrive extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClientDrive() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//String accountInfoLink = "http://localhost:8080/drive/dropbox/account";
		String accountInfoLink = "https://api.dropbox.com/1/account/info?locale=" + Locale.getDefault().toString();
		HttpSession session = request.getSession(true);
		String auth = "";
		
		if (session.getAttribute("access_token") != null)
			auth = "Bearer " + session.getAttribute("access_token").toString();
		
		ClientRequest client = new ClientRequest(accountInfoLink);
		client.header("Authorization", auth);
		client.accept("application/json");
		
		String homeInfoLink = "https://api.dropbox.com/1/metadata/auto/";
		
		ClientRequest client2 = new ClientRequest(homeInfoLink);
		client2.header("Authorization", auth);
		client2.accept("application/json");
		
		
		String json = "init";
		String json2 = "";
		try {
			ClientResponse<String> clientResponse = client.get(String.class);
			ClientResponse<String> clientResponse2 = client2.get(String.class);
			json = clientResponse.getEntity();
			json2 = clientResponse2.getEntity();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Home home = new Home();
		ClientDrive converter = new ClientDrive();
		DropboxUser du = (DropboxUser) converter.fromJson(json);
		
		home = (Home) converter.fromJson2(json2);
		
			//HttpSession session = request.getSession();
		session.getAttribute("access_token"); //link Dropboc
		session.getAttribute("gg_access_token"); //link google
		String pageTitle = "Dropbox Account";
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title>" + pageTitle + "</title>");
		out.println("</head>");
		out.println("</html>");
		out.println("<body>");
		
		out.println("<table border='1' cellpadding='5' cellspacing='0' width='400'>");
	    out.println("<tr bgcolor='#CCCCFF' align='center' valign='center' height='20'>");
	    out.println("  <td colspan ='2'><h3>" + pageTitle + "</h3></td>");
	    out.println("</tr>");
	    out.println("<tr>");
	    out.println("<td>Access token</td>" );
		out.println("<td> " + session.getAttribute("access_token") + "</td>");
		out.println("</tr>");
	    out.println("<tr>");
	    out.println("  <td >Name</td>");
	    out.println("  <td >" + du.getDisplay_name() + "</td>");
	    out.println("</tr>");
	    out.println("<tr>");
	    out.println("  <td >Email</td>");
	    out.println("  <td >" + du.getEmail() + "</td>");
	    out.println("</tr>");
	    out.println("<tr>");
	    out.println("  <td >Country</td>");
	    out.println("  <td >" + du.getCountry() + "</td>");
	    out.println("</tr>");
	    out.println("</table>");
	    out.println("<<----------------------------------------------------------------");
	    
	    out.println("<table border='1' cellpadding='5' cellspacing='0' width='400'>");
	    out.println("<tr bgcolor='#CCCCFF' align='center' valign='center' height='20'>");
	    out.println("  <td colspan ='2'><h3>Metadata Information</h3></td>");
	    out.println("</tr>");
	    out.println("<tr>");
	    out.println("  <td >Is dir</td>");
	    out.println("  <td >" + home.getIs_dir() + "</td>");
	    out.println("</tr>");
	    out.println("<tr>");
	    out.println("  <td >Icon</td>");
	    out.println("  <td >" + home.getIcon() + "</td>");
	    out.println("</tr>");
	    out.println("<tr>");
	    out.println("  <td >Root</td>");
	    out.println("  <td >" + home.getRoot() + "</td>");
	    out.println("</tr>");
	    out.println("<tr>");
	    out.println("  <td >Size</td>");
	    out.println("  <td >" + home.getSize() + "</td>");
	    out.println("</tr>");
	    out.println("<tr bgcolor='#CCCCFF' align='left' valign='center' height='15'>");
	    out.println("  <td colspan='2'>Content</td>");
	    out.println("</tr>");
	    int i = 0;
	    for (Content co: home.getContents()){
	    	out.println("<tr bgcolor='#CCCCFE' align='left' valign='center' height='15'>>");
		    out.println("  <td colspan = '2'>No."+ ++i +"</td>");
		    out.println("</tr>");
		    out.println("<tr>");
		    out.println("  <td >Directory</td>");
		    out.println("  <td >" + co.getIs_dir() + "</td>");
		    out.println("</tr>");
		    out.println("<tr>");
		    out.println("  <td >Rev</td>");
		    out.println("  <td >" + co.getRev() + "</td>");
		    out.println("</tr>");
		    out.println("<tr>");
		    out.println("  <td >Path</td>");
		    out.println("  <td >" + co.getPath() + "</td>");
		    out.println("</tr>");
		    out.println("<tr>");
		    out.println("  <td >Type</td>");
		    out.println("  <td >" + co.getMime_type() + "</td>");
		    out.println("</tr>");
		    out.println("<tr>");
		    out.println("  <td >Size</td>");
		    out.println("  <td >" + co.getSize() + "</td>");
		    out.println("</tr>");
		    out.println("<tr>");
		    out.println("  <td >Modified</td>");
		    out.println("  <td >" + co.getModified() + "</td>");
		    out.println("</tr>");
	    }
	    out.println("</table>");
	    out.println("</body>");
	}

	
	public Object fromJson(String json) throws JsonParseException
		    , JsonMappingException, IOException{
		DropboxUser dbU = new ObjectMapper().readValue(json, DropboxUser.class);
		
		return dbU;
	}

	public Object fromJson2(String json) throws JsonParseException
    , JsonMappingException, IOException{
		Home home = new ObjectMapper().readValue(json, Home.class);
		
		return home;
}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
