package fpt.capstone.bpcrs.constant;

public enum BookingEnum {
    REQUEST("REQUEST"), PENDING("PENDING"), CREATE("CREATE"), CONFIRM("CONFIRM"), DENY("DENY"), CANCEL("CANCEL"), RETURN("RETURN"),PAID("PAID"), DONE("DONE");


    BookingEnum(String status) {

    }
}
