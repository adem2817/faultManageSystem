package com.ademyilmaz.faultsystem.models;


import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
public class FaultCancelModel {
    private UUID customerUUID;
    private String description;
}
