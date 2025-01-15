package com.fontys.crowdfund.persistence.specialdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectOnlyCoverLandingPage {

    private int id;
    private String name;
    private String imageCover;
    private float moneyRaised;
    private float fundingGoal;
    private Date dateCreated;
    private String description;


}
