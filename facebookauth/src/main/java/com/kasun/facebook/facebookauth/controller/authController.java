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
	public void getAuthCode(HttpServletResponse httpServletResponse) {
	    	
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
			
    @RequestMapping(value="callback", method = RequestMethod.GET)
	public String getAccessToken(@RequestParam("code") String token,Model model) throws ClientProtocolException, IOException, UnsupportedOperationException, JSONException{
	    	
	    	final String TOKEN_ENDPOINT = "https://graph.facebook.com/oauth/access_token";
            final String GRANT_TYPE = "authorization_code";
            final String REDIRECT_URI = "https://localhost:8090/callback";
            final String CLIENT_ID = "1315552335241962";
            final String CLIENT_SECRET = "87df47821d7cc2df910fb8756e1e674b";
	    	
            HttpPost httpPost = new HttpPost(TOKEN_ENDPOINT +
            		"?grant_type=" + GRANT_TYPE +
                    "&code=" + token +
                    "&redirect_uri=" + URLEncoder.encode(REDIRECT_URI,"UTF-8") +
                    "&client_id=" + CLIENT_ID);
            
            String clientCredentials = CLIENT_ID + ":" + CLIENT_SECRET;
            String encodedClientCredentials = new String(Base64.encodeBase64(clientCredentials.getBytes()));
            httpPost.setHeader("Authorization", "Basic " +encodedClientCredentials);
            
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpResponse httpResponse = httpClient.execute(httpPost);

            // Handle access token response
            Reader reader = new InputStreamReader
                    (httpResponse.getEntity().getContent());
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = bufferedReader.readLine();

            System.out.println(line);
        
            final JSONObject obj = new JSONObject(line);
            String accessToken = obj.getString("access_token");
            
    		JSONObject albums = new JSONObject(getJsonData("https://graph.facebook.com/v2.8/me/albums",accessToken));
         
    		for (int i = 0; i < albums.getJSONArray("data").length(); i++) {
    		    JSONObject albumobject = albums.getJSONArray("data").getJSONObject(i);

    		    String id = albumobject.getString("id");
    		    JSONObject photos = new JSONObject(getJsonData("https://graph.facebook.com/v2.8/"+id+"/photos",accessToken));
    		    
            	for (int p = 0; p < photos.getJSONArray("data").length(); p++) {
        		    JSONObject photos_object = photos.getJSONArray("data").getJSONObject(p);
        		    String p_id = photos_object.getString("id");
        		   
        		    JSONObject photo_object = new JSONObject(getJsonData("https://graph.facebook.com/"+p_id+"?fields=images",accessToken));	    
        		    System.out.println(getJsonData("https://graph.facebook.com/"+p_id+"?fields=images",accessToken));
                    JSONObject image = photo_object.getJSONArray("images").getJSONObject(1);
        		    String link = image.getString("source");
                    UserPhoto up = new UserPhoto();
                    up.setLink(link);
                    linkList.add(up);
            	}              
    		}
    	model.addAttribute("photos", linkList);
  	    return "images";    
	    }
	    
	    public String getResourceData(String url,String accessToken) throws IOException{
	    	
	    		URL urlobj = new URL(url);
	    		HttpURLConnection con = (HttpURLConnection) urlobj.openConnection();

	    		//optional default is GET
	    		con.setRequestMethod("GET");

	    		//add request header
	    		con.setRequestProperty("Authorization", "Bearer " + accessToken);

	    		int responseCode = con.getResponseCode();
	    		System.out.println("\nSending 'GET' request to URL : " + url);
	    		System.out.println("Response Code : " + responseCode);

	    		BufferedReader in = new BufferedReader(
	    		        new InputStreamReader(con.getInputStream()));
	    		String inputLine;
	    		StringBuffer response = new StringBuffer();

	    		while ((inputLine = in.readLine()) != null) {
	    			response.append(inputLine);
	    		}
	    		in.close();
	    		return response.toString();
	    }	    

	
}
