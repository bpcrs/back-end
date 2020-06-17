package fpt.capstone.bpcrs.dto;

import fpt.capstone.bpcrs.model.Car;
import fpt.capstone.bpcrs.model.Image;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImagesDTO {
    @NotNull
    private String link;


    private Car car;

    public Image buildImage() {
        return Image.builder().link(link).car(car)
                .build();
    }
}
