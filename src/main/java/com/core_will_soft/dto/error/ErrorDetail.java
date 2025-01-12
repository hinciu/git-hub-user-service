package com.core_will_soft.dto.error;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"target", "message"})
public class ErrorDetail implements Serializable {
  private String target;
  private String message;
}
