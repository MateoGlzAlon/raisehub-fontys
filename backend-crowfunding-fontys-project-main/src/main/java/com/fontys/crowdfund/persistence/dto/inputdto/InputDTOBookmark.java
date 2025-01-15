package com.fontys.crowdfund.persistence.dto.inputdto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InputDTOBookmark {

    @NotNull
    private int userId;

    @NotNull
    private int projectId;

}
