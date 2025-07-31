use std::process::{Command, Output};
use std::path::Path;

pub struct GitClient;

impl GitClient {
    pub fn new() -> GitClient {
        GitClient
    }

    pub fn clone_repo(&self, repo_url: &str, destination: &Path) -> Result<Output, std::io::Error> {
        Command::new("git")
            .arg("clone")
            .arg(repo_url)
            .arg(destination)
            .output()
    }

    pub fn fetch_origin(&self, path: &Path) -> Result<Output, std::io::Error> {
        Command::new("git")
            .current_dir(path)
            .arg("fetch")
            .arg("origin")
            .output()
    }
}