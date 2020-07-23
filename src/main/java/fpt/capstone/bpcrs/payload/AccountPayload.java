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

        private String city;

        private String district;

        private String ward;

        private String street;

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

        @NonNull
        private LocalDateTime createdDate;
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
}
