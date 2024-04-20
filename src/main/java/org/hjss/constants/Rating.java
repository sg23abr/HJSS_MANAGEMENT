package org.hjss.constants;

public enum Rating {
    VERY_DISSATISFIED(1),
    DISSATISFIED(2),
    OK(3),
    SATISFIED(4),
    VERY_SATISFIED(5);

    private final int value;

    Rating(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Rating valueOfRating(int rating) {
        switch (rating) {
            case 1:
                return VERY_DISSATISFIED;
            case 2:
                return DISSATISFIED;
            case 3:
                return OK;
            case 4:
                return SATISFIED;
            case 5:
                return VERY_SATISFIED;
            default:
                throw new IllegalArgumentException("Invalid rating: " + rating);
        }
    }

}
