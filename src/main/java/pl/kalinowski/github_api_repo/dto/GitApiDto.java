package pl.kalinowski.github_api_repo.dto;

import java.util.List;

public record GitApiDto(String repositoryName, String ownerLogin, List<BranchDto> branches) {
}