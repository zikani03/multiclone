package com.github.zikani03;


import org.eclipse.jgit.lib.ProgressMonitor;

import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

public class CloningProgressMonitor implements ProgressMonitor {

    /**
     * Repository information
     */
    private final RepoInfo repoInfo;
    private final AtomicInteger completed = new AtomicInteger(0);
    private final AtomicInteger totalTasks = new AtomicInteger(0);
    private StringBuilder curTask;

    public CloningProgressMonitor(RepoInfo repoInfo) {
        this.repoInfo = repoInfo;
        this.curTask = new StringBuilder();
    }

    @Override
    public void start(int i) {
        totalTasks.set(i);
    }

    @Override
    public void beginTask(String s, int i) {
        totalTasks.set(i);
        curTask.setLength(0);
        curTask.append(s);
    }

    @Override
    public void update(int i) {
        completed.addAndGet(i);
        String progress = String.format("[%-9s] [%s] Downloading %s%n",
                repeat("#", completed.get() % 10),
                curTask.toString(),
                repoInfo.getName());
        // TODO: push progress onto a shared buffer/queue??
        System.out.print(progress);
    }

    @Override
    public void endTask() {
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    private static String repeat(String s, int n) {
        return String.join("", Collections.nCopies(n, s));
    }
}
