package fpt.capstone.bpcrs.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {

  private boolean success;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String message;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private T data;

  public ApiResponse(boolean success, T data) {
    this.success = success;
    this.data = data;
  }

  public ApiResponse(boolean success, String message) {
    this.success = success;
    this.message = message;
  }


}
