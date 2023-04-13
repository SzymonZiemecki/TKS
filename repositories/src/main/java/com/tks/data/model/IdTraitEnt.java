package com.tks.data.model;

import java.util.UUID;

import com.tks.IdTrait;
import com.tks.data.user.CartEnt;
import com.tks.model.Cart;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.apache.commons.beanutils.BeanUtils;

@Data
@SuperBuilder
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public abstract class IdTraitEnt {
    private UUID id;

}
