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
        private int id;

        @NonNull
        private String email;

        @NonNull
        private String fullName;

        private String imageUrl;

        @NonNull
        private boolean active;
    }
}
