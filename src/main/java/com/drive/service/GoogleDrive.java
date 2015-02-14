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

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.commons.httpclient.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import entities.AccessToken;

/**
 * 
 * @author TRAN Nhat Quang
 *
 */

@Path("/google")
public class GoogleDrive implements Drive{
	private String clientID = "1054441386484-5dtcfjldd0do0qobqnj2drdtqe0uojrj.apps.googleusercontent.com";
	private String clientSC = "Cr6MdLUXuC3vztOwBEIqIXDi";
	private String redirect_uri = "http://localhost:8080/drive/google/token";
	private String rootLink = "https://www.googleapis.com/drive/v2";
	/**
	 * Authorization by using OAuth2
	 */
	@GET
	@Path("/login")
	public void doAuthorization(@Context HttpServletResponse response,
			@Context HttpServletRequest request) throws IOException {
		String link = "https://accounts.google.com/o/oauth2/auth";
		
		link += "?client_id=" + this.clientID;
		link += "&response_type=code";
		link += "&scope=https://www.googleapis.com/auth/drive%20profile";
		link += "&redirect_uri=" + this.redirect_uri;
		link += "&state=!@#$%";
		
		response.sendRedirect(link);
	}

	public String getCode(@Context HttpServletResponse response,
			@Context HttpServletRequest request) throws IOException, URISyntaxException {
		// TODO Auto-generated method stub
		return null;
	}

	@GET
	@Path("/token")
	@Produces("application/json")
	public String getToken(@Context HttpServletResponse response,
			@Context HttpServletRequest request) throws IOException {
		HttpSession session = request.getSession();
		String code = request.getParameter("code").toString();		
		String link = "https://www.googleapis.com/oauth2/v3/token";		
		org.apache.http.client.HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(link);
		List<NameValuePair> para = new ArrayList<NameValuePair>();

		para.add(new BasicNameValuePair("code", code));
		para.add(new BasicNameValuePair("client_id", this.clientID));
		para.add(new BasicNameValuePair("client_secret", this.clientSC));
		para.add(new BasicNameValuePair("redirect_uri", this.redirect_uri));
		para.add(new BasicNameValuePair("grant_type", "authorization_code"));
		post.setEntity(new UrlEncodedFormEntity(para));
		
		HttpResponse res = client.execute(post);
		
		BufferedReader rd = new BufferedReader(new InputStreamReader(res.getEntity().getContent()));
		StringBuffer rs = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null)
			rs.append(line);
		
		AccessToken token = new AccessToken(rs.toString());
		session.setAttribute("gg_access_token", token.getAccess_token());
		session.setAttribute("gg_token_id", token.getUid());
		
		return res.getStatusLine().getStatusCode()+"";
		//return rs.toString();
	}

	@GET
	@Path("/accountInfo")
	@Produces("application/json")
	public String getAccoutInfo(@Context HttpServletResponse response,
			@Context HttpServletRequest request) throws Exception {
		String link = "https://www.googleapis.com/plus/v1/people/me";
		
		return this.callGetMethod(link, request);
	}

	public String uploadFile(@Context HttpServletResponse response,
			@Context HttpServletRequest request) throws HttpException, IOException {
		return null;
	}

	@GET
	@Path("/home{path:.*}")
	@Produces("application/json")
	public String getMetadata(@Context HttpServletResponse response,
			@Context HttpServletRequest request, @PathParam("path") String path) throws Exception {
		String link = this.rootLink + "/files" + path;
		
		return this.callGetMethod(link, request);
	}

	@GET
	@Path("/file/{fID}")
	@Produces("application/json")
	public Response getFile(@Context HttpServletResponse response,
			@Context HttpServletRequest request, @PathParam("fID") String path) throws Exception {
		String link = this.rootLink + "/files/" + path + "/copy";
		
		if (request.getSession().getAttribute("gg_access_token") != null)
		{			
			URL url = new URL(link);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.addRequestProperty("Authorization", "Bearer " + request.getSession().getAttribute("gg_access_token"));
			
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
			resBuilder.header("Content-Disposition", "attachment; filename=\"" + "yourfile" + "\"");
			return resBuilder.build();
		}
		
		return Response.status(401).build();
	}

	public String getShareLink(@Context HttpServletResponse response,
			@Context HttpServletRequest request, String path)
			throws ClientProtocolException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@GET
	@Path("/search")
	@Produces("application/json")
	public String search(@Context HttpServletResponse response,
			@Context HttpServletRequest request, String path)
			throws IllegalStateException, IOException {
		// TODO Auto-generated method stub
		return "Under construction";
	}

	@GET
	@Path("/create_folder")
	@Produces("application/json")
	public String createFolder(@Context HttpServletResponse response,
			@Context HttpServletRequest request, String path)
			throws ClientProtocolException, IOException {
		// TODO Auto-generated method stub
		return "Under Construction";
	}

	//TODO untested
	@GET
	@Path("/delete/{fID}")
	@Produces("application/json")
	public String deleteFile(@Context HttpServletResponse response,
			@Context HttpServletRequest request, @PathParam("fID")String path)
			throws ClientProtocolException, IOException {
		String link = this.rootLink + "/files/" + path + "/trash";
		
		return this.callPostMethod(link, request, new ArrayList<NameValuePair>());
	}

	@GET
	@Path("/move")
	@Produces("application/json")
	public String moveFile(@Context HttpServletResponse response,
			@Context HttpServletRequest request) throws ClientProtocolException,
			IOException {
		// TODO Auto-generated method stub
		return "Under construction";
	}

	@GET
	@Path("/copy")
	@Produces("application/json")
	public String copyFile(@Context HttpServletResponse response,
			@Context HttpServletRequest request) throws ClientProtocolException,
			IOException {
		// TODO Auto-generated method stub
		return "Under construction";
	}

	@GET
	@Path("/rename")
	@Produces("application/json")
	public String rename(@Context HttpServletResponse response,
			@Context HttpServletRequest request, String path)
			throws ClientProtocolException, IOException {
		// TODO Auto-generated method stub
		return "Under construction";
	}
	


	private String callPostMethod(String link, HttpServletRequest request, List<NameValuePair> para) throws ClientProtocolException, IOException
	{
		if (request.getSession().getAttribute("gg_access_token") != null)
		{
			org.apache.http.client.HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(link);
			
			post.setHeader("Authorization", "Bearer " + request.getSession().getAttribute("gg_access_token").toString());
			
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
		if (request.getSession().getAttribute("gg_access_token") != null)
		{
			org.apache.http.client.HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet(link);
			
			get.setHeader("Authorization", "Bearer " + request.getSession().getAttribute("gg_access_token").toString());
			
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
	
	private String callDeleteMethod(String link, HttpServletRequest request) throws IllegalStateException, IOException
	{
		if (request.getSession().getAttribute("gg_access_token") != null)
		{
			org.apache.http.client.HttpClient client = new DefaultHttpClient();
			HttpDelete get = new HttpDelete(link);
			
			get.setHeader("Authorization", "Bearer " + request.getSession().getAttribute("gg_access_token").toString());
			
			HttpResponse res = client.execute(get);
			
			return res.getStatusLine().getStatusCode() + "";
		}
		
		return "401";
	}
}
