package com.dev.accountservice.application.dto;

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
