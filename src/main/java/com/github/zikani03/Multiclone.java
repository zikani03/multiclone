package com.github.zikani03;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Multiclone API
 *
 */
public class Multiclone {
    private final int MAXIMUM_THREAD_COUNT = 3;
    private static final String BANNER = "Cloning [%s] repos from %s to %s %n";

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final String organizationApiUrl;
    private final String gitUrlTemplate;

    private boolean skipOnFailure = true;

    /**
     * Whether or not to check if the repository exists before trying to process
     */
    private boolean checkRepoExists = false;

    private ExecutorService executorService;

    public Multiclone(String organizationApiUrl, String gitUrlTemplate) {
        this.organizationApiUrl = organizationApiUrl;
        this.gitUrlTemplate = gitUrlTemplate;
    }

    public boolean isSkipOnFailure() {
        return skipOnFailure;
    }

    public void setSkipOnFailure(boolean skipOnFailure) {
        this.skipOnFailure = skipOnFailure;
    }

    public void process(String organizationName, List<String> repoNames, Path targetDir) {
        int repoCount = repoNames.size();
        if (executorService == null) {
            this.executorService = Executors.newFixedThreadPool(repoCount > MAXIMUM_THREAD_COUNT ? MAXIMUM_THREAD_COUNT : repoCount);
        }

        // Write out the banner
        System.out.printf(BANNER, repoCount, organizationName, targetDir.toString());

        cloneAll(organizationName, repoNames, targetDir);

        executorService.shutdown();
    }

    private void cloneAll(String organizationName, List<String> repoNames, Path targetDir) {

        if (checkRepoExists) {
            try {
                List<RepoInfo> repos = getRepoInfos(organizationName, repoNames);
            } catch (HttpClient.HttpException | IOException e) {
                throw new RuntimeException("Failed to verify repo");
            }
        }
        for(String repoName: repoNames) {
            RepoInfo repo = new RepoInfo();
            repo.setName(repoName);
            repo.setGitUrl(formatRepoUrl(organizationName, repoName));
            repo.setFullName(String.format("%s/%s", organizationName, repoName));
            repo.setLanguage("unknown");
            repo.setSize(0);
            executorService.execute(() -> clone(targetDir, repo));
        }
    }

    public String formatRepoUrl(String org, String repo) {
        return gitUrlTemplate.replace("{organization}", org)
                .replace("{repo}",repo);
    }

    private void clone(Path targetDir, RepoInfo repo) {
        try {
            this.cloneRepository(targetDir, repo);
        } catch (GitAPIException ige) {
            if (isSkipOnFailure()) {
                System.out.println("Failed to clone: " + repo.getName() + ". Skipping.");
            } else {
                ige.printStackTrace();
                throw new RuntimeException("Failed to clone repo: " + repo);
            }
        }
    }

    public List<RepoInfo> getRepoInfos(String organisationName, List<String> repoNames)
            throws HttpClient.HttpException, IOException {
        List<RepoInfo> allRepos;

        String reposUrl = organizationApiUrl.replace("{organization}", organisationName);

        String json = HttpClient.get(reposUrl);

        allRepos = objectMapper.readValue(json, new TypeReference<List<RepoInfo>>() {});

        List<RepoInfo> filtered = new ArrayList<>(repoNames.size());

        for(RepoInfo r: allRepos) {
            System.out.println(String.format("Found repo '%s' at %s", r.getName(), r.getFullName()));
            for (String name: repoNames) {
                if (name.equalsIgnoreCase(r.getName())) {
                    filtered.add(r);
                }
            }
        }
        return filtered;
    }

    private void cloneRepository(Path targetDir, RepoInfo repo) throws GitAPIException {
        CloneCommand clone = Git.cloneRepository();
        clone.setURI(repo.getGitUrl());
        clone.setProgressMonitor(new CloningProgressMonitor(repo));
        clone.setDirectory(targetDir.resolve(repo.getName()).toFile());
        try(Git git = clone.call()) {
            System.out.println("Completed downloading: " + repo.getName());
        }
    }
}
