package com.drive.service;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.apache.commons.httpclient.HttpException;
import org.apache.http.client.ClientProtocolException;

/**
 * 
 * @author TRAN Nhat Quang
 *
 */

public interface Drive {
	// Authorization
	public void doAuthorization(@Context HttpServletResponse response,
			@Context HttpServletRequest request) throws IOException;
	public String getCode(@Context HttpServletResponse response,
			@Context HttpServletRequest request) throws IOException, URISyntaxException;
	public String getToken(@Context HttpServletResponse response,
			@Context HttpServletRequest request) throws IOException;
	
	// Account
	public String getAccoutInfo(@Context HttpServletResponse response,
			@Context HttpServletRequest request) throws Exception;
	
	// Files and metadata
	public String uploadFile(@Context HttpServletResponse response,
			@Context HttpServletRequest request) throws HttpException, IOException;
	public String getMetadata(@Context HttpServletResponse response,
			@Context HttpServletRequest request, String path) throws Exception;
	public Response getFile(@Context HttpServletResponse response,
			@Context HttpServletRequest request, String path) throws Exception;
	public String getShareLink(@Context HttpServletResponse response,
			@Context HttpServletRequest request, String path) throws ClientProtocolException, IOException;
	public String search(@Context HttpServletResponse response,
			@Context HttpServletRequest request, String path) throws IllegalStateException, IOException;
	
	// File operations
	public  String createFolder(@Context HttpServletResponse response,
			@Context HttpServletRequest request, String path) throws ClientProtocolException, IOException;
	public String deleteFile(@Context HttpServletResponse response,
			@Context HttpServletRequest request, String path) throws ClientProtocolException, IOException;
	public String moveFile(@Context HttpServletResponse response,
			@Context HttpServletRequest request) throws ClientProtocolException, IOException;
	public String copyFile(@Context HttpServletResponse response,
			@Context HttpServletRequest request) throws ClientProtocolException, IOException;
	public String rename(@Context HttpServletResponse response,
			@Context HttpServletRequest request, String path) throws ClientProtocolException, IOException;
}
