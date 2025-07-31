// This module represents repository information.

#[derive(Debug)]
pub struct RepoInfo {
    pub name: String,
    pub description: Option<String>,
}

impl RepoInfo {
    pub fn new(name: &str, description: Option<&str>) -> RepoInfo {
        RepoInfo {
            name: name.to_string(),
            description: description.map(|s| s.to_string()),
        }
    }
}