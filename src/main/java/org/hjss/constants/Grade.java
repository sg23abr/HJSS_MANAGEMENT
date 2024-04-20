package org.hjss.constants;

public enum Grade {
    GRADE_1(1),
    GRADE_2(2),
    GRADE_3(3),
    GRADE_4(4),
    GRADE_5(5);

    private final Integer value;

    Grade(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public static Grade valueOfGrade(int grade) {
        switch (grade) {
            case 1:
                return GRADE_1;
            case 2:
                return GRADE_2;
            case 3:
                return GRADE_3;
            case 4:
                return GRADE_4;
            case 5:
                return GRADE_5;
            default:
                throw new IllegalArgumentException("Invalid Grade " + grade);
        }
    }
}
