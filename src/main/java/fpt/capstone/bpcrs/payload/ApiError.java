package fpt.capstone.bpcrs.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Collections;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {

  private final boolean success = false;
  private String message;
  private List<String> errors;


  public ApiError(final String message, final String error) {
    this.message = message;
    errors = Collections.singletonList(error);
  }

  public ApiError(String message, List<String> errors) {
    this.message = message;
    this.errors = errors;
  }
}
