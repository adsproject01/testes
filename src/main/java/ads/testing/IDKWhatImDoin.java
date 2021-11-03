package ads.testing;

import java.io.IOException;

/*import org.kohsuke.github.GHBranch;
import org.kohsuke.github.GHContentBuilder;
import org.kohsuke.github.GHDeploymentBuilder;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHRepository.Setter;
import org.kohsuke.github.GitHub;*/
// This one has CRUD
//import org.kohsuke.github.GitHubBuilder;

// I don't think this one has CUD operations
/*import com.jcabi.github.Github;
import com.jcabi.github.RtGithub;
import com.jcabi.http.wire.RetryWire;
*/


public class IDKWhatImDoin {
	/*static public void main(String[] args) {
		String oauthKey = "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";
		String repository_name = "lunayue/testing_ads";
		/*Github github_no_cud = new RtGithub(
				   new RtGithub(oauthKey)
				     .entry()
				     .through(RetryWire.class)
				 );*/
		
		
		/*try {
			GitHub github = new GitHubBuilder().withOAuthToken(oauthKey).build();
			
			//GHRepository repo = new GHCreateRepositoryBuilder("testing_ads",github,"http://github.com/lunayue/testing_ads.git").done();
					//repo.delete();
			GHRepository repo = github.getRepository(repository_name);
			System.out.println(repo.getBranches());
			
			GHContentBuilder cb = repo.createContent();

			
			//cb = cb.branch("boop");
			cb = cb.message("trying to make a new branch");
			cb = cb.content("ads_github_java_links.txt");
			cb.commit();
			/*repo.update();
			GHDeploymentBuilder boop = repo.createDeployment(repository_name);
			boop.create();*/
			/*System.out.println(repo.getBranches());
			Setter idk = repo.set();
			idk.done();*/
		/*} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
	}*/
}
