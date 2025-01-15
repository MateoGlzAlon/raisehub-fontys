package com.fontys.crowdfund.persistence.dto.outputdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OutputDTOProjectImage {

    private int id;
    private int projectId;
    private String imageURL;
    private int imageOrder;

}
