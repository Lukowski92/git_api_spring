package pl.kalinowski.github_api_repo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pl.kalinowski.github_api_repo.dto.BranchDto;
import pl.kalinowski.github_api_repo.dto.GitApiDto;
import pl.kalinowski.github_api_repo.exception.UserNotFoundException;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GitApiService {
    private final RestTemplate restTemplate = new RestTemplate();
    private static final String GITHUB_API_URL = "https://api.github.com/users/%s/repos";
    private static final String GITHUB_BRANCHES_URL = "https://api.github.com/repos/%s/%s/branches";


    public List<GitApiDto>  getAllRepositories(String username) {
        String url = GITHUB_API_URL.formatted(username);

        GitResponse[] repos;
        try {
            repos = restTemplate.getForObject(url, GitResponse[].class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new UserNotFoundException("GitHub user not found: " + username);
        }
        if (repos == null) return List.of();
        return Arrays.stream(repos)
                .filter(repo -> !repo.fork())
                .map(repo -> {
                    List<BranchDto> branches = getBranches(repo.owner().login(), repo.name());
                    return new GitApiDto(repo.name(), repo.owner().login(), branches);
                })
                .collect(Collectors.toList());
    }
    private List<BranchDto> getBranches(String owner, String repoName) {
        String url = GITHUB_BRANCHES_URL.formatted(owner, repoName);
        BranchResponse[] branches = restTemplate.getForObject(url, BranchResponse[].class);

        if (branches == null) return List.of();

        return Arrays.stream(branches)
                .map(branch -> new BranchDto(branch.name(), branch.commit().sha()))
                .collect(Collectors.toList());
    }

}
