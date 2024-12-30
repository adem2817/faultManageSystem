package com.ademyilmaz.faultsystem.models;

import com.ademyilmaz.faultsystem.enums.FaultType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.UUID;


@Getter
@Setter
public class FaultCreateModel {
    @NotNull
    private UUID customerUUID;
    @NotNull
    private FaultType faultType;
    @NotNull
    private String description;
}
