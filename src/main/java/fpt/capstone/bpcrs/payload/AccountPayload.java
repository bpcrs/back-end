package fpt.capstone.bpcrs.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

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

        @NonNull
        private String role;
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
