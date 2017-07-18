package com.github.zikani03;

import java.nio.file.Paths;
import java.util.Arrays;

/**
 * Main class for the application
 *
 * multiclone [options] OWNER REPO [REPO] .. [REPO]
 *
 * Options
 *
 * -u --username   USERNAME a username to use in authentication with the git server, may prompt for password
 *                 if the configured transport mechanism requires one (e.g. https)
 * -f --from       github|bitbucket|ssh
 * -d --directory  DIRECTORY
 * -s --submodules Clone submodules as well
 *    --shallow    Clone the repositories as shallow repos
 * -v --verbose    Show verbose output
 *
 */
public class Application {
    public static void main(String... args) {
        if (args.length < 2) {
            System.out.println("Usage:\n\tmulticlone ORGANIZATION REPO [REPO] [REPO] .. [REPO]");
            System.exit(-1);
        }

        String organisation = args[0];
        String[] repoNames = new String[args.length - 1];
        for(int i = 1; i < args.length; i++) {
            repoNames[i - 1] = args[i];
        }
        Multiclone multiclone = new Multiclone("https://api.github.com/orgs/{organization}/repos",
                                               "https://github.com/{organization}/{repo}.git");
        multiclone.setSkipOnFailure(true);
        multiclone.process(organisation, Arrays.asList(repoNames), Paths.get("."));
    }
}
