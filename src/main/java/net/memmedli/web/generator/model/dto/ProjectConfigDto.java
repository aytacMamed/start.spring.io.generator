package net.memmedli.web.generator.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class ProjectConfigDto {

    private String name;

    private String artifactId;
    private String groupId;
    private String version;
    private String rootPackage;
    private Set<String> models;


    public String getRootPackage() {
        return rootPackage == null ? groupId + "." + artifactId : rootPackage;
    }
}
