package pl.kalinowski.github_api_repo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GitApiIntegrationTests {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldReturnNonForkedRepositoriesWithBranchesForValidUser() {

        // given
        String username = "Lukowski92";

        // when
        String url = "http://localhost:" + port + "/api/" + username + "/repos";
        ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<?> repositories = response.getBody();
        assertThat(repositories).isNotNull();
        assertThat(repositories).isNotEmpty();

        for (Object repoObj : repositories) {
            assertThat(repoObj).isInstanceOf(Map.class);
            Map<String, Object> repo = (Map<String, Object>) repoObj;

            assertThat(repo).containsKeys("repositoryName", "ownerLogin", "branches");
            assertThat(repo.get("repositoryName")).isInstanceOf(String.class);
            assertThat(repo.get("ownerLogin")).isEqualTo(username);

            List<?> branches = (List<?>) repo.get("branches");
            assertThat(branches).isNotNull();

            for (Object branchObj : branches) {
                assertThat(branchObj).isInstanceOf(Map.class);
                Map<String, Object> branch = (Map<String, Object>) branchObj;

                assertThat(branch).containsKeys("name", "lastCommitSha");
                assertThat(branch.get("name")).isInstanceOf(String.class);
                assertThat(branch.get("lastCommitSha")).isInstanceOf(String.class);
            }
        }
    }
}
