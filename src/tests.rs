#[cfg(test)]
mod tests {
    use crate::multiclone::Multiclone;
    use crate::cloning_progress_monitor::CloningProgressMonitor;

    #[test]
    fn test_format_repo_url() {
        let multiclone = Multiclone::new(
            "http://localhost:4567/api/{organization}/repos",
            "http://localhost:4567/data/{organization}/{repo}.git",
        );
        let formatted_url = multiclone.format_repo_url("example", "something");
        assert_eq!(formatted_url, "http://localhost:4567/data/example/something.git");
    }

    #[test]
    fn test_cloning_progress_monitor() {
        let monitor = CloningProgressMonitor::new();
        monitor.start(); // Expectation: prints "Cloning started..."
        monitor.finish(); // Expectation: prints "Cloning finished."
    }
}