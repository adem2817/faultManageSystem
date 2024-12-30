package com.ademyilmaz.faultsystem.models;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.UUID;

@Getter
@Setter
public class FaultSuccessModel {
    private UUID customerUUID;
    @Min(1)
    @Max(5)
    private Integer satisfactionScore;
}
