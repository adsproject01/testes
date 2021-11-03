package ads.better_code;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

public class Editor {
	private ReadIni ini;
	private GitHubRestAPI github;
	//private String email;
	//private String message;
	
	public Editor(String ini_name) {
		this.ini = new ReadIni(ini_name);
		github = new GitHubRestAPI(ini.getNonCurator(), ini.getBaseUrl());
	}
	
	
	
	
	
	public String createBranch(String email) throws IOException, InterruptedException {
		String new_branch_name = email/*+"_"+LocalDateTime.now()*/; //needs to be changed so each update goes to a new branch no matter what
        Map<String, String> createBranchMap = Map.of(
                "ref", "refs/heads/"+new_branch_name,
                "sha", github.getMasterBranchSHA());
        String requestBody = github.objectMapper.writeValueAsString(createBranchMap);
        //return github.post("/git/refs", requestBody);
        github.post("/git/refs", requestBody);
        return new_branch_name;
    }
	
	
	
	
	
	public void makeContribuition(String file, String content, String message, String email) throws IOException, InterruptedException {
		updateFile(file, content, message, createBranch(email));
	}
	
	public void updateFile(String file, String message, String content, String branch) throws IOException, InterruptedException {
		System.out.println(message);
		String sha = github.getFileSHA(file);
		String encodedContent = java.util.Base64.getEncoder().encodeToString(content.getBytes());
		Map<String,String> createMap = Map.of(
				"content", encodedContent,
                "message", message,
                "branch", branch,
                "sha", sha);
		System.out.println(createMap.toString());
        try {
        	String requestBody = github.objectMapper.writeValueAsString(createMap);
			String boop = github.put("/contents/"+file, requestBody);
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// Should delete the branch if the update doesn't go right
		}
	}
	
	public static void main(String[] args) {
		try {
			Editor editor = new Editor("resources/config.ini");
			InputStream fileStream = new FileInputStream("C:\\Users\\susan\\eclipse-workspace\\testing\\filename.txt");
			String content = new String(Objects.requireNonNull(fileStream).readAllBytes(), StandardCharsets.UTF_8);
			editor.makeContribuition("new_file.txt","let's see if this works", content, "hello");
			//editor.github.get("/git/refs/heads");
			System.out.println("hello?");
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
