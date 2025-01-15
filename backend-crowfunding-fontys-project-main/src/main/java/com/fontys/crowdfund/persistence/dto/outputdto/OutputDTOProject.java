package com.fontys.crowdfund.persistence.dto.outputdto;

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
public class OutputDTOProject {

    private int id;
    private String name;
    private int userId;
    private float fundingGoal;
    private float moneyRaised;
    private List<String> images;

    private String description;
    private String location;
    private String type;
    private Date dateCreated;

}
