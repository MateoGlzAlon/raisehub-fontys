package com.fontys.crowdfund.persistence.dto.inputdto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InputDTOLogin {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

}
