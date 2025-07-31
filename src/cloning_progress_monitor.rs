// This module simulates a cloning progress monitor.

pub struct CloningProgressMonitor;

impl CloningProgressMonitor {
    pub fn new() -> CloningProgressMonitor {
        CloningProgressMonitor
    }

    pub fn start(&self) {
        println!("Cloning started...");
    }

    pub fn finish(&self) {
        println!("Cloning finished.");
    }
}