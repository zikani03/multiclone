package com.github.zikani03;

import java.nio.file.Paths;
import java.util.Arrays;

/**
 * Created by zikani on 4/1/17.
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
