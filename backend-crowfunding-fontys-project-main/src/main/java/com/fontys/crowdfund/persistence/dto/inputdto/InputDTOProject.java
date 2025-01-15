package com.fontys.crowdfund.persistence.dto.inputdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InputDTOProject {

    private String name;
    private String description;
    private String location;
    private String type;
    private Date dateCreated;
    private float fundingGoal;
    private int userId;
    private List<String> images;

}
