package com.fontys.crowdfund.persistence.dto.inputdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InputDTOPayment {

    private int projectId;
    private int backerId;
    private float amountFunded;
    private Date paymentDate;

}
