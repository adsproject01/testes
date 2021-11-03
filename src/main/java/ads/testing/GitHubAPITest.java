package ads.testing;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

// Tutorial https://www.twilio.com/blog/improve-workflow-github-api-java
// Code from https://github.com/emilyjkrohn/github-api-test/blob/master/src/main/java/GitHubAPITest.java#L23

public class GitHubAPITest {
	private static final String authentication = "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";
	private static final String owner = "lunayue";
	private static final String repository = "testing_ads";
	private static final String authorization = "Bearer "+ authentication;
    private static final String baseUrl = "https://api.github.com/repos/"+owner+"/"+repository;
    private static final ObjectMapper objectMapper = new ObjectMapper();
	
    public static void main(String[] args) throws IOException, InterruptedException {
    	new GitHubAPITest().executeExample();
	}
    
    private String get(String path) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(baseUrl + path))
                .setHeader("Authorization", authorization)
                .GET()
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, BodyHandlers.ofString());
        return response.body();
    }
    
    private String delete(String path) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(baseUrl + path))
                .setHeader("Authorization", authorization)
                .DELETE()
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, BodyHandlers.ofString());
        return response.body();
    }
    
    private String post(String path, String body) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(baseUrl + path))
                .setHeader("Authorization", authorization)
                .POST(BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, BodyHandlers.ofString());
        return response.body();
    }
    
    private String put(String path, String body) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(baseUrl + path))
                .setHeader("Authorization", authorization)
                .PUT(BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, BodyHandlers.ofString());
        return response.body();
    }
    
    private String getResourceFile(String filename) throws IOException {
    	System.out.println(filename);
    	InputStream fileStream = new FileInputStream(filename);
        //InputStream fileStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
        System.out.println(fileStream);
        return new String(Objects.requireNonNull(fileStream).readAllBytes(), StandardCharsets.UTF_8);
    }
    
    private String getMasterBranchSHA() throws IOException, InterruptedException {
        String body = this.get("/git/refs/heads");
        System.out.println(body);

        String sha = objectMapper.readTree(body)
                .get(0)
                .get("object")
                .get("sha")
                .asText();

        return sha;
    }
   
    private String createBranch(String sha) throws IOException, InterruptedException {
        Map<String, String> createBranchMap = Map.of(
                "ref", "refs/heads/new-branch",
                "sha", sha);
        String requestBody = objectMapper.writeValueAsString(createBranchMap);
        return this.post("/git/refs", requestBody);
    }
    
    private String createFile() throws IOException, InterruptedException {
        String fileToAdd = getResourceFile("C:\\Users\\susan\\eclipse-workspace\\testing\\Untitled1");
        String encodedContent = java.util.Base64.getEncoder().encodeToString(fileToAdd.getBytes());

        Map<String,String> createMap = Map.of(
                "message", "New file added",
                "content", encodedContent,
                "branch", "new-branch");

        String requestBody = objectMapper.writeValueAsString(createMap);
        return this.put("/contents/new_file.txt", requestBody);
    }
    
    private String createPullRequest() throws IOException, InterruptedException {
    	 Map<String,String> createPullRequestMap = Map.of(
                "title", "test-pull-request",
                "head", "new-branch",
                "base", "main");

        String requestBody = objectMapper.writeValueAsString(createPullRequestMap);
        return this.post("/pulls", requestBody);
    }
    
    private String getPullNumber(String pullRequestResponse) throws JsonProcessingException {
        return objectMapper.readTree(pullRequestResponse)
                .get("number")
                .asText();
    }

    private String mergePullRequest(String pullNumber) throws IOException, InterruptedException {

        Map<String,String> mergeMap = Map.of(
                "commit_message", "Merging pull request");

        String requestBody = objectMapper.writeValueAsString(mergeMap);
        String url = String.format("/pulls/%s/merge", pullNumber);

        return this.put(url, requestBody);
    }
    
    private String deleteBranch() throws IOException, InterruptedException {
        return this.delete("/git/refs/heads/new-branch");
    }
    
    private void executeExample() throws IOException, InterruptedException {
        String masterSHA = this.getMasterBranchSHA();
        this.createBranch(masterSHA);
        this.createFile();

        String pullRequestResponse = this.createPullRequest();
        System.out.println(pullRequestResponse);
        String pullNumber = this.getPullNumber(pullRequestResponse);

        this.mergePullRequest(pullNumber);
        this.deleteBranch();
    }
}
