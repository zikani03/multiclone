package com.github.zikani03;

import org.junit.Rule;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Test for Multiclone
 */
public class MulticloneTest {

    @Rule
    public GitHubMock gitHubMock = GitHubMock.start();

    @Test
    public void testShouldFormatBasedOnTemplates() {
        Multiclone multiclone = new Multiclone("http://localhost:4567/api/{organization}/repos",
                "http://localhost:4567/data/{organization}/{repo}.git" );

        assertEquals("http://localhost:4567/data/example/something.git", multiclone.formatRepoUrl("example", "something"));
    }

    @Test
    public void testShouldGetRepoInfoForNames() throws Exception {
        Multiclone multiclone = new Multiclone("http://localhost:4567/api/{organization}/repos",
                "http://localhost:4567/data/{organization}/{repo}.git" );

        List<RepoInfo> repoInfoList = multiclone.getRepoInfos("example", Arrays.asList("dogfood", "everywhere"));

        assertFalse(repoInfoList.isEmpty());
        assertEquals(2, repoInfoList.size());
    }

    /*
    @Test
    public void testShouldGetRepoInfo() {
        Multiclone multiclone = new Multiclone("http://localhost:4567/api/{organization}/repos",
                                               "http://localhost:4567/{organization}/{repo}.git" );
        Path path = Paths.get(".");

        multiclone.process(organisation, Arrays.asList("dogfood", "gemma", "everywhere"), path);
    }
    */
}
