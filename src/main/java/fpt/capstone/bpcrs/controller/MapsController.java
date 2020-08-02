package fpt.capstone.bpcrs.controller;

import fpt.capstone.bpcrs.exception.BpcrsException;
import fpt.capstone.bpcrs.hepler.GoogleMapsHelper;
import fpt.capstone.bpcrs.payload.ApiResponse;
import fpt.capstone.bpcrs.payload.MapsPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/maps")
@Slf4j
public class MapsController {

    @Autowired
    private GoogleMapsHelper googleMapsHelper;

    @PostMapping()
    public ResponseEntity<?> distanceBetweenTwoLocation(@RequestBody MapsPayload.RequestDistanceBetweenLocation request) {
        MapsPayload.ResponseDistanceBetweenLocation response = new MapsPayload.ResponseDistanceBetweenLocation();
        try {
            response.setDistance(googleMapsHelper.distanceBetweenTwoLocation(request.getLocation(),
                    request.getDestination()));
        } catch (BpcrsException e) {
            return new ResponseEntity(new ApiResponse<>(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(new ApiResponse<>(true, response));
//        return new ResponseEntity(new ApiResponse<>(true, response), HttpStatus.BAD_REQUEST);
    }

}
