package com.fontys.crowdfund.persistence.dto.outputdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OutputDTOUser {

    private long id;
    private String name;
    private String email;
    private String role;
    private String profilePicture;
}
