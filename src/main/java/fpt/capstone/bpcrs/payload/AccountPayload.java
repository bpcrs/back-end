package fpt.capstone.bpcrs.payload;

import lombok.*;

import java.time.LocalDateTime;

public class AccountPayload {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GoogleRequestLogin {
        private String token;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoginResponse {
        private String accessToken;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AccountResponse {
        @NonNull
        private int id;

        @NonNull
        private String email;

        @NonNull
        private String fullName;

        @NonNull
        private String imageUrl;

//        @NonNull
//        private String carLicensePlate;

        @NonNull
        private String role;

//        private String phoneNumber;

//        @NonNull
        private LocalDateTime createdDate;

        private String phone;

        private String identification;

        private String imageLicense;
//        private String imageUrlLicense1;
//
//        private String imageUrlLicense2;
//
//        private String imageUrlLicense3;
//
//        private String imageUrlLicense4;

        @NonNull
        private boolean licenseCheck;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AccountResponseChecking {
        @NonNull
        private int id;

        @NonNull
        private String email;

        @NonNull
        private String fullName;

        @NonNull
        private String imageUrl;

        @NonNull
        private boolean active;

//        @NonNull
        private LocalDateTime createdDate;

        private String phone;

        private  String imageLicense;
//        private String identification;
//
//        private String imageUrlLicense1;
//
//        private String imageUrlLicense2;
//
//        private String imageUrlLicense3;
//
//        private String imageUrlLicense4;

        @NonNull
        private boolean licenseCheck;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AccountAddressUpdate {
        @NonNull
        private int id;
        private String city;
        private String district;
        private String ward;
        private String street;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AccountRequestUpdate {
        private String phone;

        private boolean active;

        private String identification;

        private String imageLicense;
//        private String imageUrlLicense1;
//
//        private String imageUrlLicense2;
//
//        private String imageUrlLicense3;
//
//        private String imageUrlLicense4;

        @NonNull
        private boolean licenseCheck;
    }
}
