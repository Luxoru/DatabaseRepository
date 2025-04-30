package me.luxoru.databaserepository.utils;

import lombok.experimental.UtilityClass;

import java.util.Random;

@UtilityClass
public class StringUtils {

    private static final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String numbers = "0123456789";


    /**
     * Generates a random string of the specified length, including letters and optionally numbers.
     *
     * @param length the length of the random string
     * @return a random string of the specified length
     */
    public static String generateRandomString(int length) {
        return generateRandomString(length, true, -1);
    }

    /**
     * Generates a random string of the specified length, including letters and optionally numbers.
     *
     * @param length the length of the random string
     * @return a random string of the specified length
     */
    public static String generateRandomString(int length, long seed) {
        return generateRandomString(length, true, seed);
    }

    public static String generateRandomString(int length, boolean isUsingNumbers) {
        return generateRandomString(length, false, -1);
    }

    /**
     * Generates a random string of the specified length, including letters and optionally numbers.
     *
     * @param length         the length of the random string
     * @param isUsingNumbers if true, the generated string will include numbers
     * @return a random string of the specified length
     */
    public static String generateRandomString(int length, boolean isUsingNumbers, long seed, char... excludedCharacters) {

        String characters = StringUtils.characters;

        Random rand = new Random();
        if (seed != -1) {
            rand.setSeed(seed);
        }
        StringBuilder sb = new StringBuilder(length);

        if (isUsingNumbers) {
            characters += numbers;
        }

        if (excludedCharacters != null && excludedCharacters.length > 0) {
            String excludedRegex = "[" + new String(excludedCharacters) + "]";
            characters = characters.replaceAll(excludedRegex, "");
        }

        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(rand.nextInt(characters.length())));
        }

        return sb.toString();
    }
}
