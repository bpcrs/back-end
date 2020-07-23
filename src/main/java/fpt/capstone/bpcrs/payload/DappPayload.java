package fpt.capstone.bpcrs.payload;

import java.io.Serializable;

public class DappPayload {

    public static class ResultChaincode implements Serializable {
        private int code;
        private String message;
        private String data;

        public ResultChaincode() {
            this.code = 200;
            this.data = "";
        }

        public ResultChaincode(int code, String message, String data) {
            this.code = code;
            this.message = message;
            this.data = data;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }
}
