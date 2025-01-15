package com.fontys.crowdfund.persistence.dto.inputdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InputDTOUser {

    private String name;
    private String email;
    private String password;
    private String role;
    private String profilePicture;

}
