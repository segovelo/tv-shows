package tv.shows.app.httpclient;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import tv.shows.app.beans.TVShows;
import tv.shows.app.utility.JsonUtility;

/** 
* 8 Dec 2023
* @Javadoc TODO 
*
* @author Segovelo  **/

public class HttpClientUtility {

	/*
	 *  https://stackoverflow.com/questions/68711922/java-best-way-to-send-multiple-http-requests
     */

	public static List<TVShows> retrieveTVShowsAsync(List<String> urls) {
		Instant start = Instant.now();
		List<TVShows> tvShows = new ArrayList<TVShows>();
		Map<String, String> queryParams = new HashMap<>();
		try {
			int i = 0;
			List<String> urlsQueryParams = new ArrayList<>();
			for (String url : urls) {
				queryParams.put("page", String.valueOf(++i));
				urlsQueryParams.add(getParamsString(url,queryParams));
			}
			
			List<URI> uris = urlsQueryParams.stream().map(url-> URI.create(url)).collect(Collectors.toList());
			final ExecutorService executorService = Executors.newFixedThreadPool(20);

			HttpClient httpClient = HttpClient.newHttpClient();
					HttpClient.newBuilder()
			        .executor(executorService)
			        .version(HttpClient.Version.HTTP_2)
			        .connectTimeout(Duration.ofSeconds(10))
			        .build();
					
			    List<HttpRequest> requests = uris.stream()
			        .map(HttpRequest::newBuilder)
			        .map(reqBuilder -> reqBuilder.header("Content-Type", "application/json"))
			        .map(reqBuilder -> reqBuilder.GET())
			        .map(reqBuilder -> reqBuilder.build())
			        .collect(Collectors.toList());
			    
			    List<CompletableFuture<HttpResponse<String>>> listOfCompletableFutures =  requests.stream()
			                                       .map(request -> httpClient.sendAsync(request, BodyHandlers.ofString()))
			                                       .collect(Collectors.toList());
			    
			     List<HttpResponse<String>> tvShowsResponse = listOfCompletableFutures
			     .stream()
			     .map(CompletableFuture::join)
			     .filter(Objects::nonNull)
			     .filter(response -> response.headers().firstValue(":status").isPresent())
			     .filter(response -> "200".equals(response.headers().firstValue(":status").get()))
			     .collect(Collectors.toList());
			     			     
			     tvShows = tvShowsResponse.stream()
                                          .map(response->JsonUtility.jsonToTVShows(response.body()))
                                          .flatMap(List::stream)
			     						  .collect(Collectors.toList());	                                         
			    
		} catch(Exception ex) {
			System.out.println(ex);
		}
		timeToComplete(start);
		return tvShows;
	}
	public static List<TVShows> retrieveTVShows(String urlString, String no_page) {
		Instant start = Instant.now();
    	List<TVShows> tvShows = new ArrayList<TVShows>();
		Map<String, String> queryParams = new HashMap<>();
		queryParams.put("page", no_page);
		try {
			URL url = new URL(getParamsString(urlString, queryParams));
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");			
	        connection.setRequestProperty("Accept", "application/json");
	        connection.setConnectTimeout(5000);
	        connection.setReadTimeout(5000);
			int status = connection.getResponseCode();
			Reader streamReader = null;
			if (status > 299) {
			    streamReader = new InputStreamReader(connection.getErrorStream());
			} else {
			    streamReader = new InputStreamReader(connection.getInputStream());
			}
			BufferedReader in = new BufferedReader(streamReader);
			String inputLine;
			StringBuffer content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
			    content.append(inputLine);
			}
			in.close();
			String contentStr = content.toString();
			int index =  contentStr.indexOf("\"data\":");
			String tvShowsStr = contentStr.substring(index+7);
			if (status < 300 && index > -1) {
				tvShows = JsonUtility.jsonToTVShows(contentStr);
			}		
			connection.disconnect();
	    }catch (Exception e){
	        System.out.println(e);
	        System.out.println("The GET request failed");
	    }
		timeToComplete(start);
		return tvShows;
	}
	
	public static Instant timeToComplete(Instant start) {
		if (start == null) {
			return Instant.now();
		}
		Instant finish = Instant.now();
		long timeElapsed = Duration.between(start, finish).toMillis();
		System.out.println(String.format("Time to make call : %d", timeElapsed));
		return finish;
	}
    public static String getParamsString(String url, Map<String, String> queryParams) 
    	      throws UnsupportedEncodingException{
    	        StringBuilder result = new StringBuilder();
                result.append(url);
                result.append("?");
    	        for (Map.Entry<String, String> entry : queryParams.entrySet()) {
    	          result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
    	          result.append("=");
    	          result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
    	          result.append("&");
    	        }

    	        String resultString = result.toString();
    	        return resultString.length() > 0
    	          ? resultString.substring(0, resultString.length() - 1)
    	          : resultString;
    	    }

}
