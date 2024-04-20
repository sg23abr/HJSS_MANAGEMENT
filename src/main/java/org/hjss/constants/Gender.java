package org.hjss.constants;

public enum Gender {
    MALE("Male"),
    FEMALE("Female");

    private final String value;

    Gender(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Gender valueOfGenderString(String genderString) {
        if (genderString.equalsIgnoreCase("male")) {
            return MALE;
        } else if (genderString.equalsIgnoreCase("female")) {
            return FEMALE;
        } else {
            throw new IllegalArgumentException("Invalid gender string: " + genderString);
        }
    }
}
