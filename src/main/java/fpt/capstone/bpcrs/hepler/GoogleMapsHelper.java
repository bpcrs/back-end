package fpt.capstone.bpcrs.hepler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.*;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.TravelMode;
import fpt.capstone.bpcrs.exception.BpcrsException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class GoogleMapsHelper {

    @Value("${google.api.key}")
    private String GOOGLE_API_KEY;

    public String distanceBetweenTwoLocation(String location, String destination) throws BpcrsException {
        double result = 0;
        GeoApiContext context = new GeoApiContext.Builder().apiKey(GOOGLE_API_KEY).build();
        DistanceMatrixApiRequest request = DistanceMatrixApi.newRequest(context);
        DistanceMatrix matrix = null;
        try {
            matrix = request.origins(location).destinations(destination).mode(TravelMode.DRIVING)
                    .avoid(DirectionsApi.RouteRestriction.HIGHWAYS).language("vi-VN").await();
        } catch (Exception e) {
            throw new BpcrsException(e.getMessage());
        }
        return matrix.rows[0].elements[0].distance.humanReadable;
    }
}
