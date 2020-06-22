package fpt.capstone.bpcrs.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class PagingPayload<T> {
    private T data;
    private int count;
}
