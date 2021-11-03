package ads.better_code;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;

import com.fasterxml.jackson.databind.ObjectMapper;

public class GitHubRestAPI {
	private final String authorization;
	private final String baseUrl;
	public final ObjectMapper objectMapper;
	
	public GitHubRestAPI(String authorization, String baseUrl) {
		this.authorization = authorization;
		this.baseUrl = baseUrl;
		this.objectMapper =  new ObjectMapper();
	}
	
	
	
	
	
	public String get(String path) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(baseUrl + path))
                .setHeader("Authorization", authorization)
                .GET()
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, BodyHandlers.ofString());
        return response.body();
    }
    

	
	
	
	public String delete(String path) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(baseUrl + path))
                .setHeader("Authorization", authorization)
                .DELETE()
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, BodyHandlers.ofString());
        return response.body();
    }

	
	
	
	
    public String post(String path, String body) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(baseUrl + path))
                .setHeader("Authorization", authorization)
                .POST(BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, BodyHandlers.ofString());
        return response.body();
    }

    
    
    
    
    public String put(String path, String body) throws IOException, InterruptedException {
    	System.out.println("body");
    	System.out.println(body);
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(baseUrl + path))
                .setHeader("Authorization", authorization)
                .PUT(BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, BodyHandlers.ofString());
        return response.body();
    }
    
    
    
    
    
    public String getMasterBranchSHA() throws IOException, InterruptedException {
        String body = get("/git/refs/heads");
        String sha = objectMapper.readTree(body)
                .get(0)
                .get("object")
                .get("sha")
                .asText();
        return sha;
    }
    
    public String getFileSHA(String file) throws IOException, InterruptedException {
        String body = this.get("/contents/"+file);
        String sha = objectMapper.readTree(body)
                .get("sha")
                .asText();

        return sha;
    }
}
