mod multiclone;
#[path = "repo_info.rs"] mod repo_info;


#[cfg(test)] mod tests;

use std::fs;

use std::path::Path;
use crate::multiclone::Multiclone;

use std::env;

fn main() {
    let args: Vec<String> = env::args().collect();
    if args.len() < 3 {
        eprintln!("Usage: multiclone-rs <organization/username> <repo1> <repo2> ...");
        return;
    }

    let organization_or_user = &args[1];
    let multiclone = Multiclone::new(
        "https://github.com/{organization}/{repo}.git"
    );

    for repo in &args[2..] {
        let repo_parts: Vec<&str> = repo.split('/').collect();
        let (organization, repo_name) = if repo_parts.len() == 2 {
            (repo_parts[0], repo_parts[1])
        } else {
            (organization_or_user.as_str(), repo.as_str())
        };

        let destination = Path::new(&repo_name);

        if destination.exists() {
            fs::remove_dir_all(destination).unwrap(); // Clean up before cloning
        }

        let repo_url = multiclone.format_repo_url(organization, repo_name);

        match multiclone.clone_repo(&repo_url, &destination) {
            Ok(output) => {
                if output.status.success() {
                    println!("Successfully cloned {}.", repo_name);
                } else {
                    println!("Failed to clone {}: \n{}\n", repo_name, String::from_utf8_lossy(&output.stderr));
                }
            }
            Err(err) => println!("Error cloning {}: {}", repo_name, err),
        }
    }

}