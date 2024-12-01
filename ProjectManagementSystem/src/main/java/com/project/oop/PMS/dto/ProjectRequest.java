package com.project.oop.PMS.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;


import com.project.oop.PMS.entity.Project;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectRequest {
	
    private String name;
    private String description;
    public Project constructFromDTO() {
        Project project = new Project(name, description);
       
        return project;
    }
}

