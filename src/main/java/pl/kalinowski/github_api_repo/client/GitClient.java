package pl.kalinowski.github_api_repo.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pl.kalinowski.github_api_repo.exception.UserNotFoundException;
import pl.kalinowski.github_api_repo.model.BranchResponse;
import pl.kalinowski.github_api_repo.model.GitResponse;

@Component
public class GitClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String GITHUB_API_URL = "https://api.github.com/users/%s/repos";
    private static final String GITHUB_BRANCHES_URL = "https://api.github.com/repos/%s/%s/branches";

    public GitResponse[] getRepositories(String username) {
        String url = GITHUB_API_URL.formatted(username);
        try {
            return restTemplate.getForObject(url, GitResponse[].class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new UserNotFoundException("GitHub user not found: " + username);
        }
    }

    public BranchResponse[] getBranches(String owner, String repoName) {
        String url = GITHUB_BRANCHES_URL.formatted(owner, repoName);
        return restTemplate.getForObject(url, BranchResponse[].class);
    }

}
