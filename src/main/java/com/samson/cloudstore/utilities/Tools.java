package com.samson.cloudstore.utilities;

import org.jspecify.annotations.NonNull;

import java.time.LocalDate;

public class Tools {

    public enum Gender { MALE, FEMALE }

    /**
     * Verifies a JMBG number against the provided personal details.
     * * @param jmbg        The 13-digit JMBG string.
     * @param dateOfBirth The expected date of birth.
     * @param gender      The expected gender.
     * @return true if the JMBG format is valid, matches all details, and passes the checksum.
     */

    public static boolean verifyJmbg(String jmbg, LocalDate dateOfBirth, Gender gender) {
        // 1. Basic format validation (must be exactly 13 digits)
        if (jmbg == null || !jmbg.matches("\\d{13}")) return false;

        // 2. Extract components from the JMBG string
        int day = Integer.parseInt(jmbg.substring(0, 2));
        int month = Integer.parseInt(jmbg.substring(2, 4));
        int yearPart = Integer.parseInt(jmbg.substring(4, 7));
        int sequenceAndGender = Integer.parseInt(jmbg.substring(9, 12));
        int actualChecksum = Character.getNumericValue(jmbg.charAt(12));

        // 3. Verify Date of Birth
        int expectedDay = dateOfBirth.getDayOfMonth();
        int expectedMonth = dateOfBirth.getMonthValue();
        int expectedYearPart = dateOfBirth.getYear() % 1000;

        if (day != expectedDay || month != expectedMonth || yearPart != expectedYearPart) return false;

        // 4. Verify Gender
        boolean isMale = sequenceAndGender >= 0 && sequenceAndGender <= 499;
        boolean isFemale = sequenceAndGender >= 500 && sequenceAndGender <= 999;

        if (gender == Gender.MALE && !isMale) return false;

        if (gender == Gender.FEMALE && !isFemale) return false;

        // 5. Calculate and Verify the Checksum
        // Formula: C = 11 - ( (7*(a+g) + 6*(b+h) + 5*(c+i) + 4*(d+j) + 3*(e+k) + 2*(f+l)) % 11 )
        int[] d = new int[13];

        for (int i = 0; i < 13; i++) d[i] = Character.getNumericValue(jmbg.charAt(i));

        int sum = 7 * (d[0] + d[6])
                + 6 * (d[1] + d[7])
                + 5 * (d[2] + d[8])
                + 4 * (d[3] + d[9])
                + 3 * (d[4] + d[10])
                + 2 * (d[5] + d[11]);

        int remainder = sum % 11;
        int expectedChecksum = 11 - remainder;

        // If the calculation results in 10 or 11, the official checksum digit is standardized to 0
        if (expectedChecksum > 9) expectedChecksum = 0;

        return expectedChecksum == actualChecksum;
    }

    static void main(String[] args) {
        String day;
        String month;
        String year;

        System.out.println("Enter date of birth (DD MM YYYY):");

        try (java.util.Scanner scanner = new java.util.Scanner(System.in)) {
            System.out.print("Day: ");
            day = scanner.next();

            System.out.print("Month: ");
            month = scanner.next();

            System.out.print("Year: ");
            year = scanner.next();

            LocalDate dateOfBirth = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
            System.out.println("Date of Birth: " + dateOfBirth);

            System.out.println("Enter gender (M/F):");
            String gender = scanner.next();
            Gender genderEnum = gender.equalsIgnoreCase("M") ? Gender.MALE : Gender.FEMALE;

            System.out.println("Gender: " + genderEnum);

            System.out.println("Enter JMBG number:");
            String jmbg = scanner.next();
            System.out.println("JMBG: " + jmbg);

            boolean isValid = verifyJmbg(jmbg, dateOfBirth, genderEnum);
            System.out.println("JMBG is valid: " + isValid);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
