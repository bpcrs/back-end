package fpt.capstone.bpcrs.payload;

import java.io.Serializable;

public class DappPayload {


    public static class ResultChaincode implements Serializable {
        private boolean success;
        private String data;

        public ResultChaincode() {
            this.success = false;
            this.data = "";
        }

        public ResultChaincode(int code, boolean success, String data) {
            this.success = success;
            this.data = data;
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }

    public static class RegisterUser{
        public RegisterUser(String username) {
            this.username = username;
        }

        private String username;
    }
}
