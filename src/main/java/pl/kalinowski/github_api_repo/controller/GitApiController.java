package pl.kalinowski.github_api_repo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kalinowski.github_api_repo.dto.GitApiDto;
import pl.kalinowski.github_api_repo.exception.ErrorResponse;
import pl.kalinowski.github_api_repo.exception.UserNotFoundException;
import pl.kalinowski.github_api_repo.service.GitApiService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GitApiController {


    private final GitApiService gitApiService;

    public GitApiController(GitApiService gitApiService) {
        this.gitApiService = gitApiService;
    }

    @GetMapping("/{username}/repos")
    public ResponseEntity<?> getUserRepositories(@PathVariable String username) {
        try {
            List<GitApiDto> repositories = gitApiService.getAllRepositories(username);
            return ResponseEntity.ok(repositories);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(404).body(new ErrorResponse(404, e.getMessage()));
        }
    }
}
