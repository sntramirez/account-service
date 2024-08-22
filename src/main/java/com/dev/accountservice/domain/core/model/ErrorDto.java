package com.dev.accountservice.domain.core.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class ErrorDto {
    String code;
    String message;
}
