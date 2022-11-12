package com.pas.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public abstract class IdTrait {
    private UUID id;
}
