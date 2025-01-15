package com.fontys.crowdfund.business;

import com.fontys.crowdfund.persistence.dto.inputdto.InputDTOLogin;
import com.fontys.crowdfund.persistence.dto.outputdto.OutputDTOLogin;

public interface LoginService {
    OutputDTOLogin login(InputDTOLogin loginRequest);
}
