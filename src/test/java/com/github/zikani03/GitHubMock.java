package com.github.zikani03;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.rules.ExternalResource;
import spark.Spark;

import java.util.Arrays;
import java.util.List;

/**
 * Mocks the GitHub Organizations Repos API using Spark Java
 */
public class GitHubMock extends ExternalResource {

    static final String organisation = "example";

    static final ObjectMapper objectMapper = new ObjectMapper();

    static final List<RepoInfo> repos = Arrays.asList(
            new RepoInfo("dogfood", "example/dogfood", 5000, "Java", "https://localhost:4567/example/dogfood.git"),
            new RepoInfo("gemma", "example/gemma", 5000, "Ruby", "https://localhost:4567/example/gemma.git"),
            new RepoInfo("everywhere", "example/everywhere", 5000, "C", "https://localhost:4567/example/everywhere.git"),
            new RepoInfo("namesrus", "example/namesrus", 5000, "Rust", "https://localhost:4567/example/namesrus.git"),
            new RepoInfo("dogfood-microservice", "example/dogfood-microservice", 5000, "Java", "https://localhost:4567/example/dogfood-microservice.git")
    );

    public static GitHubMock start() {
        return new GitHubMock();
    }

    @Override
    public void before() {
        Spark.get("/api/:organization/repos", (request, response) -> {
            String organization = request.params("organization");

            if (organization.equalsIgnoreCase("example")) {
                response.status(200);
                response.body(objectMapper.writeValueAsString(repos));
            } else {
                response.status(404);
            }
            return response;
        });

        Spark.get("/data/:organization/:repo", (request, response) -> {
            String organization = request.params("organization");

            if (organization.equalsIgnoreCase("example")) {
                response.status(200);
                response.body(objectMapper.writeValueAsString(repos));
            } else {
                response.status(404);
            }
            return response;
        });
    }

    @Override
    protected void after() {
        Spark.stop();
    }
}
