package pl.kalinowski.github_api_repo.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GitResponse(
        String name,
        boolean fork,
        Owner owner
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Owner(String login) {
    }
}