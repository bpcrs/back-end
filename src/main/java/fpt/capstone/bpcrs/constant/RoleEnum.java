package fpt.capstone.bpcrs.constant;

public enum RoleEnum {

    ADMINISTRATOR("ADMINISTRATOR"),
    USER("USER");

    public class RoleType {
        public static final String ADMINISTRATOR = "ADMINISTRATOR";
        public static final String USER = "USER";
    }

    private final String label;

    RoleEnum(final String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return this.label;
    }
}
