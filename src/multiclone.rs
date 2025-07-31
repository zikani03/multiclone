// This module implements the main functionality for cloning repositories.

use std::path::Path;
use std::process::{Command, Output};

pub struct Multiclone {

    repo_template_url: String,
}

impl Multiclone {
    pub fn new(repo_template_url: &str) -> Multiclone {
        Multiclone {
            repo_template_url: repo_template_url.to_string(),
        }
    }

    pub fn format_repo_url(&self, organization_or_user: &str, repo: &str) -> String {
        self.repo_template_url
            .replace("{organization}", organization_or_user)
            .replace("{repo}", repo)
    }

    pub fn clone_repo(&self, repo_url: &str, destination: &Path) -> Result<Output, std::io::Error> {
        use std::io::{self, Write};

        if destination.exists() {
            print!("Destination path '{}' exists. Do you want to delete it and re-clone? [y/N]: ", destination.display());
            io::stdout().flush().unwrap();

            let mut input = String::new();
            io::stdin().read_line(&mut input).unwrap();
            let input = input.trim().to_lowercase();

            if input != "y" {
                println!("Aborting cloning process.");
                return Err(std::io::Error::new(std::io::ErrorKind::Other, "Cloning operation aborted by user."));
            }

            std::fs::remove_dir_all(destination).unwrap();
        }

        Command::new("git")
            .arg("clone")
            .arg(repo_url)
            .arg(destination)
            .output()
    }
}