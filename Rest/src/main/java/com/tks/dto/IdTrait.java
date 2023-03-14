package com.tks.dto;

import lombok.*;

import java.util.UUID;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class IdTrait {
    UUID id;
}
