package com.samson.cloudstore.sandbox;

import java.time.LocalDate;

public class Collector {

    public LocalDate dateOfBirth;
    public Gender gender;
    public String jmbg;

    public Collector collect() {
        System.out.println("Enter date of birth (DD MM YYYY):");

        try (java.util.Scanner scanner = new java.util.Scanner(System.in)) {
            System.out.print("Day: ");
            String dayOfBirth = scanner.next();

            System.out.print("Month: ");
            String monthOfBirth = scanner.next();

            System.out.print("Year: ");
            String yearOfBirth = scanner.next();

            this.dateOfBirth = LocalDate.of(Integer.parseInt(yearOfBirth), Integer.parseInt(monthOfBirth), Integer.parseInt(dayOfBirth));
            System.out.println("Date of Birth: " + this.dateOfBirth);

            System.out.println("Enter gender (M/F):");
            String tempGender = scanner.next();
            this.gender = tempGender.equalsIgnoreCase("M") ? Gender.MALE : Gender.FEMALE;

            System.out.println("Gender: " + this.gender.name());

            System.out.println("Enter JMBG number:");
            this.jmbg = scanner.next();
            System.out.println("JMBG: " + this.jmbg);


        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return this;
    }

}
