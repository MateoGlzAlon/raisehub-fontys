package com.fontys.crowdfund.persistence.specialdto;

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
public class ProjectDetailsDTO {

    private int id;
    private String name;
    private int userId;
    private String ownerName;
    private String ownerProfilePicture;
    private float fundingGoal;
    private float moneyRaised;
    private List<String> images;

    private String description;
    private String location;
    private String type;
    private Date dateCreated;

}
