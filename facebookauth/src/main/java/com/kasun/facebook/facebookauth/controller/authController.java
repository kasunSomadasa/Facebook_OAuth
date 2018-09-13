package com.kasun.facebook.facebookauth.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.kasun.facebook.facebookauth.Model.UserPhoto;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


@Controller
public class authController {

	
	ArrayList<UserPhoto> linkList = new ArrayList<>();
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() {
		return "index";
	}

	
	   @RequestMapping(value = "/redirect", method = RequestMethod.GET)
	    public void method(HttpServletResponse httpServletResponse) {
	    	
	    	   String AUTH_ENDPOINT = "https://www.facebook.com/dialog/oauth";
	    	   String RESPONSE_TYPE = "code";
	    	   String CLIENT_ID = "1315552335241962";
	    	   String REDIRECT_URI = "https://localhost:8090/callback";
	    	   String SCOPE = "public_profile user_posts user_friends user_photos";
	    	   
	    	   String requestEndpoint = null;
			try {
				requestEndpoint = AUTH_ENDPOINT + "?" +
				            "response_type=" + RESPONSE_TYPE + "&" +
				            "client_id=" + CLIENT_ID + "&" +
				            "redirect_uri=" + URLEncoder.encode(REDIRECT_URI,"UTF-8" ) + "&" +
				            "scope=" + URLEncoder.encode(SCOPE,"UTF-8" );
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	            
	        httpServletResponse.setHeader("Location", requestEndpoint);
	        httpServletResponse.setStatus(302);
	    }
		

	 
	


}
