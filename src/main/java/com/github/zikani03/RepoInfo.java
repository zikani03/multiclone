package com.github.zikani03;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Subset of the Repository information provided by GitHub's API
 *
 * @date 2017-04-01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RepoInfo {
    @JsonProperty("name")
    private String name;

    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("size")
    private int size;

    @JsonProperty("language")
    private String language;

    @JsonProperty("git_url")
    private String gitUrl;
}
