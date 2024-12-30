package com.ademyilmaz.faultsystem.entities;

import com.ademyilmaz.faultsystem.enums.FaultType;
import com.ademyilmaz.faultsystem.models.FaultCreateModel;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Objects;
import java.util.UUID;

@Entity(name = "Fault")
@Table(name = "fault")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Fault {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(nullable = false, updatable = false)
    private UUID uuid;
    private FaultType faultType;
    private String description;
    private String cancelReason;
    private Integer satisfactionScore;
    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;

    public static Fault getFault(FaultCreateModel faultCreateModel, Customer customer) {
        return Fault.builder()
                .customer(customer)
                .uuid(UUID.randomUUID())
                .faultType(faultCreateModel.getFaultType())
                .description(faultCreateModel.getDescription())
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Fault fault)) return false;
        return Objects.equals(id, fault.id) && Objects.equals(uuid, fault.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uuid);
    }
}
