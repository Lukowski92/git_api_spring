package pl.kalinowski.github_api_repo.service;

import org.springframework.stereotype.Service;

import pl.kalinowski.github_api_repo.client.GitClient;
import pl.kalinowski.github_api_repo.dto.BranchDto;
import pl.kalinowski.github_api_repo.dto.GitApiDto;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GitApiService {

    private final GitClient gitClient;

    public GitApiService(GitClient gitClient) {
        this.gitClient = gitClient;
    }

    public List<GitApiDto> getAllRepositories(String username) {
        var repos = gitClient.getRepositories(username);

        if (repos == null) return List.of();

        return Arrays.stream(repos)
                .filter(repo -> !repo.fork())
                .map(repo -> {
                    var branches = getBranchDtos(repo.owner().login(), repo.name());
                    return new GitApiDto(repo.name(), repo.owner().login(), branches);
                })
                .collect(Collectors.toList());
    }

    private List<BranchDto> getBranchDtos(String owner, String repoName) {
        var branches = gitClient.getBranches(owner, repoName);
        if (branches == null) return List.of();

        return Arrays.stream(branches)
                .map(branch -> new BranchDto(branch.name(), branch.commit().sha()))
                .collect(Collectors.toList());
    }

}
