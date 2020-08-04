package fpt.capstone.bpcrs.hepler;

import org.springframework.beans.factory.annotation.Autowired;

public class RunHelper {
    private static HFHelper hfHelper = new HFHelper();
    public static void main(String[] args) {
        try {
            hfHelper.enrollAdmin();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
