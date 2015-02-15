package com.drive.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.NameValuePair;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import entities.AccessToken;
import entities.FileName;

/**
 * 
 * @author TRAN Nhat Quang
 * Sync with Dropbox API
 *
 */

@Path("/dropbox")
public class DropboxDrive implements Drive{
	private String APP_KEY = "aje8p4qsxs9h4bo";
	private String APP_SECRET = "s91c7fj5ddewd94";
	private String redirect_uri = "http://localhost:8080/drive/dropbox/token";
	
	/**
	 * Authorization by using OAuth2
	 * @param response
	 * @param request
	 * @throws IOException
	 */
	@GET
	@Path("/login")
	public void doAuthorization(@Context HttpServletResponse response,
										@Context HttpServletRequest request) throws IOException
	{
		String authorLink = "https://www.dropbox.com/1/oauth2/authorize?client_id=" + this.APP_KEY 
				+ "&response_type=code&redirect_uri=" + this.redirect_uri;
		response.sendRedirect(authorLink);
	}
	
	/**
	 * get access_token
	 * @param response
	 * @param request
	 * @throws Exception 
	 */
	
	/*
	@POST
	@Path("/token")
	@Produces("application/json")
	public String getToken(@Context HttpServletResponse response,
								@Context HttpServletRequest request) throws Exception
	{
		System.out.println("Go");
		String code = "";
		if (request.getSession().getAttribute("code") != null)
			code = request.getSession().getAttribute("code").toString();
		
		String linkGetTokenInfo = "https://api.dropbox.com/1/oauth2/token?code=" + code 
				+ "&grant_type=authorization_code&client_id=" + this.APP_KEY
				+ "&client_secret=" + this.APP_SECRET
				+ "&redirect_uri=http://localhost:8080/drive/dropbox/tokenInfo";
		
		ClientRequest req = new ClientRequest(linkGetTokenInfo);
		req.accept("application/json");
		System.out.println("-->1");
		ClientResponse<String> res = req.get(String.class);
				
		return res.getEntity();
	}*/
	
	public String showToken(@Context HttpServletResponse response,
							@Context HttpServletRequest request)
	{
		if (request.getSession().getAttribute("token_info") != null)
			return request.getSession().getAttribute("token_info").toString();
		
		return "null";
	}
	
	/**
	 * [REDIRECT_URI]?code=ABCDEFG&state=[STATE]
	 * @throws URISyntaxException 
	 */
	public String getCode(@Context HttpServletResponse response,
							@Context HttpServletRequest request) throws URISyntaxException
	{
		HttpSession session = request.getSession();
		session.setAttribute("code", request.getParameter("code"));
		
		return session.getAttribute("code").toString();
	}
	
	@GET
    @Path("/hello")
	@Produces("application/json")
    public String hello(@Context HttpServletResponse response,
			@Context HttpServletRequest request){
        return "Hello World";    
    }

	/**
	 * Get account info
	 * @throws Exception 
	 */
	@GET
	@Path("/account")
	@Produces("application/json")
	public String getAccoutInfo(@Context HttpServletResponse response,
			@Context HttpServletRequest request) throws Exception {
		String accountInfoLink = "https://api.dropbox.com/1/account/info?locale=" + Locale.getDefault().toString();
		HttpSession session = request.getSession(true);
		String auth = "";
		
		if (session.getAttribute("access_token") != null)
			auth = "Bearer " + session.getAttribute("access_token").toString();
		
		ClientRequest client = new ClientRequest(accountInfoLink);
		client.header("Authorization", auth);
		client.accept("application/json");
		
		ClientResponse<String> clientResponse = client.get(String.class);
		
		return clientResponse.getEntity();
	}

	@GET
	@Path("/token")
	@Produces("application/json")
	public String getToken(@Context HttpServletResponse response,
							@Context HttpServletRequest request) throws IOException{
		String code = "";
		String linkGetTokenInfo = "";
		HttpClient client = new HttpClient();
		PostMethod method;
		
		if (request.getParameter("code") != null)
		{
			code = request.getParameter("code");
			request.getSession().setAttribute("code", code);
		}
		
		linkGetTokenInfo = "https://api.dropbox.com/1/oauth2/token?code=" + code 
				+ "&grant_type=authorization_code&client_id=" + this.APP_KEY
				+ "&client_secret=" + this.APP_SECRET
				+ "&redirect_uri=" + redirect_uri;
		
		method = new PostMethod(linkGetTokenInfo);
		
		if (client.executeMethod(method) == 200)
		{
			AccessToken acc = new AccessToken(method.getResponseBodyAsString());
			HttpSession session = request.getSession();
			
			session.setAttribute("access_token", acc.getAccess_token());
			session.setAttribute("uid", acc.getUid());
			
			//return session.getAttribute("access_token").toString();
			//return method.getResponseBodyAsString();
			
			response.sendRedirect("http://localhost:8080/client/show");
			
			return "200";
		}
		
		return client.executeMethod(method)+"";
	}
	
	// TODO uploading file to drive
	@GET
	@Path("/up")
	@Produces("application/json")
	public String uploadFile(@Context HttpServletResponse response,
							@Context HttpServletRequest request) throws HttpException,IOException 
	{
		return "Underconstruction";
		//return request.getParameter("file");
	}
	
	/**
	 * get directory
	 * @throws Exception 
	 */
	@GET
	@Path("/home{path:.*}")
	@Produces("application/json")
	public String getMetadata(@Context HttpServletResponse response,
			@Context HttpServletRequest request, @PathParam("path") String path) throws Exception{
		String link = "https://api.dropbox.com/1/metadata/auto/" + path;
		
		if (request.getSession().getAttribute("access_token") != null)
		{
			String token = request.getSession().getAttribute("access_token").toString();
			ClientRequest req = new ClientRequest(link);
			req.header("Authorization", "Bearer " + token);
			req.accept("application/json");
			
			ClientResponse<String> res = req.get(String.class);
			
			return res.getEntity();
		}
		
		return "401";
	}

	/**
	 * download file
	 * @throws Exception 
	 */
	@GET
	@Path("/file{path:.*}")
	@Produces("application/json")
	public Response getFile(@Context HttpServletResponse response,
			@Context HttpServletRequest request, @PathParam("path") String path) throws Exception{
		if (path == null)
			return Response.status(404).build();
		
		String link = "https://api-content.dropbox.com/1/files/auto" + path;
		
		FileName download = new FileName(link);
		
		if (request.getSession().getAttribute("access_token") != null)
		{			
			URL url = new URL(link);
			URLConnection conn = url.openConnection();
			conn.addRequestProperty("Authorization", "Bearer " + request.getSession().getAttribute("access_token"));
			
			File temp = new File("temp");
			temp.createNewFile();
			
			InputStream is = conn.getInputStream();
			OutputStream os = new FileOutputStream(temp);
			
			int read = 0;
			byte[] bytes = new byte[1024];
	 
			while ((read = is.read(bytes)) != -1) {
				os.write(bytes, 0, read);
			}
			
			ResponseBuilder resBuilder = Response.ok((Object) temp);
			resBuilder.header("Content-Disposition", "attachment; filename=\"" + download.getFullName() + "\"");
			return resBuilder.build();
		}
		
		return Response.status(401).build();
	}

	/**
	 * create folder
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	@GET
	@Path("/create_folder{path:.+}")
	@Produces("application/json")
	public String createFolder(@Context HttpServletResponse response,
			@Context HttpServletRequest request, @PathParam("path") String path) throws ClientProtocolException, IOException{
		String link = "https://api.dropbox.com/1/fileops/create_folder";
		
		List<NameValuePair> para = new ArrayList<NameValuePair>();
		para.add(new BasicNameValuePair("root", "auto"));
		para.add(new BasicNameValuePair("path", path));
		
		return callPostMethod(link, request, para);
	}

	/**
	 * Delete file and folder
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	@GET
	@Path("delete{path:.+}")
	@Produces("application/json")
	public String deleteFile(@Context HttpServletResponse response,
			@Context HttpServletRequest request, @PathParam("path") String path) throws ClientProtocolException, IOException {
		String link = "https://api.dropbox.com/1/fileops/delete";
		
		List<NameValuePair> para = new ArrayList<NameValuePair>();
		para.add(new BasicNameValuePair("root", "auto"));
		para.add(new BasicNameValuePair("path", path));
		
		return callPostMethod(link, request, para);
	}

	/**
	 * Move file: from_Path and to_Path are in the query of URL
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	@GET
	@Path("/move")
	@Produces("application/json")
	public String moveFile(@Context HttpServletResponse response,
			@Context HttpServletRequest request) throws ClientProtocolException, IOException{		
		String pathFrom = request.getParameter("from");
		String tempList[] = pathFrom.split("/");
		String fileName = tempList[tempList.length-1];
		String pathTo = request.getParameter("to") + fileName;
		String link = "https://api.dropbox.com/1/fileops/move";
		
		return fileCopyMove(link, request, pathFrom, pathTo);
	}

	/**
	 * Copy file: from_Oath and to_Path are in the query of URL
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	@GET
	@Path("/copy")
	@Produces("application/json")
	public String copyFile(@Context HttpServletResponse response,
			@Context HttpServletRequest request) throws ClientProtocolException, IOException{
		String pathFrom = request.getParameter("from");
		String tempList[] = pathFrom.split("/");
		String fileName = tempList[tempList.length-1];
		String pathTo = request.getParameter("to") + fileName;
		String link = "https://api.dropbox.com/1/fileops/copy";
		
		return fileCopyMove(link, request, pathFrom, pathTo);
	}


	
	/**
	 * Get shared link
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	@GET
	@Path("/shares{path:.+}")
	@Produces("application/json")
	public String getShareLink(@Context HttpServletResponse response,
			@Context HttpServletRequest request, @PathParam("path") String path) throws ClientProtocolException, IOException{
		String link = "https://api.dropbox.com/1/shares/auto" + path;
		
		return callPostMethod(link, request, new ArrayList<NameValuePair>());
	}

	/**
	 * Rename file
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	@GET
	@Path("/rename{path:.+}")
	@Produces("application/json")
	public String rename(@Context HttpServletResponse response,
			@Context HttpServletRequest request, @PathParam("path") String path) throws ClientProtocolException, IOException {
		String newName = request.getParameter("newName");
		String[] temp = path.split("/");
		String newLink = "";
		
		for (int i =0; i<temp.length-1; i++)
			newLink += temp[i] + "/";
		
		String pathTo = newLink + newName;
		String link = "https://api.dropbox.com/1/fileops/move";
		
		return fileCopyMove(link, request, path, pathTo);
	}

	/**
	 * Searching file
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@GET
	@Path("/search{path:.*}")
	@Produces("application/json")
	public String search(@Context HttpServletResponse response,
			@Context HttpServletRequest request, @PathParam("path") String path) throws IllegalStateException, IOException {
		String key = "";
		
		if (request.getParameter("key") != null)
			key += request.getParameter("key").toString();
		
		String link = "https://api.dropbox.com/1/search/auto" + path + "?query=" + key;
		System.out.println(link);
		return this.callGetMethod(link, request);
	}
	
	private String fileCopyMove(String link, HttpServletRequest request, String pathFrom, String pathTo) throws ClientProtocolException, IOException
	{
		if (request.getSession().getAttribute("access_token") != null)
		{
			org.apache.http.client.HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(link);
			
			post.setHeader("Authorization", "Bearer " + request.getSession().getAttribute("access_token").toString());
			
			List<NameValuePair> para = new ArrayList<NameValuePair>();
			para.add(new BasicNameValuePair("root", "auto"));
			para.add(new BasicNameValuePair("from_path", pathFrom));
			para.add(new BasicNameValuePair("to_path", pathTo));
			
			post.setEntity(new UrlEncodedFormEntity(para));
			
			HttpResponse res = client.execute(post);
			
			BufferedReader rd = new BufferedReader(new InputStreamReader(res.getEntity().getContent()));
			StringBuffer rs = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null)
				rs.append(line);
			
			return rs.toString();
		}
		
		return "401";
	}

	private String callPostMethod(String link, HttpServletRequest request, List<NameValuePair> para) throws ClientProtocolException, IOException
	{
		if (request.getSession().getAttribute("access_token") != null)
		{
			org.apache.http.client.HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(link);
			
			post.setHeader("Authorization", "Bearer " + request.getSession().getAttribute("access_token").toString());
			
			post.setEntity(new UrlEncodedFormEntity(para));
			
			HttpResponse res = client.execute(post);
			
			BufferedReader rd = new BufferedReader(new InputStreamReader(res.getEntity().getContent()));
			StringBuffer rs = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null)
				rs.append(line);
			
			return rs.toString();
		}
		
		return "401";
	}
	
	private String callGetMethod(String link, HttpServletRequest request) throws IllegalStateException, IOException
	{
		if (request.getSession().getAttribute("access_token") != null)
		{
			org.apache.http.client.HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet(link);
			
			get.setHeader("Authorization", "Bearer " + request.getSession().getAttribute("access_token").toString());
			
			HttpResponse res = client.execute(get);
			
			BufferedReader rd = new BufferedReader(new InputStreamReader(res.getEntity().getContent()));
			StringBuffer rs = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null)
				rs.append(line);
			
			return rs.toString();
		}
		
		return "401";
	}
}
