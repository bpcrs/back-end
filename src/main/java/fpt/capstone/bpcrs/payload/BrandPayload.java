package fpt.capstone.bpcrs.payload;

import fpt.capstone.bpcrs.model.Brand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@NoArgsConstructor
public class BrandPayload {

    public interface Request_CreateBrand_Validate {

    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    @NoArgsConstructor
    public static class RequestCreateBrand extends Brand {

    }
}
