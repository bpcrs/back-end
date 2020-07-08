package fpt.capstone.bpcrs.constant;

public enum RoleEnum {
    ADMINISTRATOR("ADMINISTRATOR"),
    USER("USER");

    private final String text;

    /**
     * @param text
     */
    RoleEnum(final String text) {
        this.text = text;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return text;
    }
}
