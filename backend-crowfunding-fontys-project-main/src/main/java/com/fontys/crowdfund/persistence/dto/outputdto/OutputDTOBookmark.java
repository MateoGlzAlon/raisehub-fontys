package com.fontys.crowdfund.persistence.dto.outputdto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OutputDTOBookmark {

    @NotNull
    private int id;

    @NotNull
    private int userId;

    @NotNull
    private int projectId;

}
