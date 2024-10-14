package com.dev.accountservice.domain.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Setter
@Getter
@AllArgsConstructor
public class KeyResponseDto extends RepresentationModel<KeyResponseDto> {
    private Long valor;
}
