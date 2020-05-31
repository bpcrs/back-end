package fpt.capstone.bpcrs.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

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
