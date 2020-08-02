package fpt.capstone.bpcrs.controller;

import com.google.maps.errors.ApiException;
import fpt.capstone.bpcrs.exception.BpcrsException;
import fpt.capstone.bpcrs.hepler.GoogleMapsHelper;
import fpt.capstone.bpcrs.payload.ApiResponse;
import fpt.capstone.bpcrs.service.BlockchainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/contract")
@Slf4j
public class ContractController {
    @Autowired
    private BlockchainService blockchainService;

//    @GetMapping()
//    public ResponseEntity<?> getContract() {
////        blockchainService.submitContract();
//        GoogleMapsHelper googleMapsHelper = new GoogleMapsHelper();
//        googleMapsHelper.distanceBetweenTwoLocation("Trường Đại học FPT, Lô E2a-7, Đường D1, Khu Công Nghệ Cao, Long Thạnh Mỹ, Quận 9, Thành phố Hồ Chí Minh","Căn hộ Sky 9, Liên Phường, Bình Trưng Đông, Quận 9, Thành phố Hồ Chí Minh");
//        return new ResponseEntity(new ApiResponse<>(true,""), HttpStatus.BAD_REQUEST);
//    }


}
