package com.tks;

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
