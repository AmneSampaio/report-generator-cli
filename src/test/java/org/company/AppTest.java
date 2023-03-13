package org.company;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Unit test for report-generator-cli
 */
public class AppTest {

    @Before
    public void createInputFile() throws IOException {
        FileWriter inputFile = new FileWriter("testCountryCodes.txt");
        inputFile.write("This is a sample text");
        inputFile.close();

        FileWriter txtFile = new FileWriter("textfile.txt");
        txtFile.write("keyA-valueA\nkeyB-valueB");
        txtFile.close();
    }

    @After
    public void deleteInputFile() {
        File inputFile = new File("testCountryCodes.txt");
        inputFile.delete();

        File txtFile = new File("textfile.txt");
        txtFile.delete();
    }



    @Test
    public void shouldCallAPhoneFalseIfHasOtherCharacterThanSpacesAndNumbers()
    {
        String phoneTestWithManyCharacters = "+351 23d2 342 333";
        String phoneTestWithPlusNumbersAndSpaces = "+351 232 342 333";
        String phoneTestWithNumbersOnly = "351232342333";
        boolean firstPhone = Util.isPhoneValid(phoneTestWithManyCharacters);
        boolean secondPhone = Util.isPhoneValid(phoneTestWithPlusNumbersAndSpaces);
        boolean thirdPhone = Util.isPhoneValid(phoneTestWithNumbersOnly);

        assertFalse(firstPhone);
        assertTrue(secondPhone);
        assertTrue(thirdPhone);
    }

    @Test
    public void shouldReturnShortTypeForANumber()
    {
        String phone = "9874";
        boolean shortPhoneType = Util.isShortPhone(phone);

        assertTrue(shortPhoneType);
    }

    @Test
    public void shouldReturnLongTypeForANumber()
    {
        String phone = "+44 65465444";
        List<String> countryCode = new ArrayList<>();
        countryCode.add("44");
        boolean longPhoneType = Util.isLongPhone(phone, countryCode);

        assertTrue(longPhoneType);
    }

    @Test
    public void shouldReturnACountryCode()
    {
        String phone = "+44 65465444";
        int expectedCountryCode = 44;
        int countryCode = Util.getCountryCode(phone);

        assertEquals(countryCode, expectedCountryCode);
    }

    @Test
    public void shouldReadATextFromAFile() throws IOException {
        List<String> fileRead = Util.getDataFromFile("testCountryCodes.txt");
        List<String> expectedText = new ArrayList<>(List.of("This is a sample text"));
        assertEquals(fileRead, expectedText);
    }

    @Test
    public void shouldConvertATextFileToMap() throws IOException {
        List<String> fileRead = Util.getDataFromFile("textfile.txt");
        Map<String,String> textToMap = Util.turnTxtIntoMap(fileRead);

        Map<String,String> expectedText = new HashMap<>();
        expectedText.put("valueA","keyA");
        expectedText.put("valueB","keyB");

        assertEquals(expectedText, textToMap);
    }

    @Test
    public void shouldClassifyAListOfPhonesByCountryNames() {
        List<String> phones = new ArrayList<>(Arrays.asList("+44 65465444","9874","+ 4465465444"));

        List<String> countryNames = new ArrayList<>(Arrays.asList("44","351"));

        var sorted = App.phoneClassification(phones, countryNames);

        ArrayList<Phone> expectedSorted = new ArrayList<>(Arrays.asList(
                new Phone(44, true , "+44 65465444", "LONG"),
                new Phone(351, true , "9874", "SHORT"),
                new Phone(0, false , "+ 4465465444", "INVALID")
        ));

        assertEquals(expectedSorted,sorted);
    }

    @Test
    public void shouldCountPhonesByCountryCode() {
        Map<String, String> countryNamesWithCodes = new HashMap<>();
        countryNamesWithCodes.put("351","Portugal");
        countryNamesWithCodes.put("44", "United Kingdom");

        ArrayList<Phone> sortedPhones = new ArrayList<>(Arrays.asList(
                new Phone(44, true , "+44 65465444", "LONG"),
                new Phone(351, true , "9874", "SHORT"),
                new Phone(0, false , "+ 4465465444", "INVALID")
        ));

        List<Map.Entry<String, Integer>> countPhonesByCountryCode = App.countPhonesByCountryCode(sortedPhones,countryNamesWithCodes );

        Map<String, Integer> expectedPhonesByCountryCode = new HashMap<>();
        expectedPhonesByCountryCode.put("Portugal", 1);
        expectedPhonesByCountryCode.put("INVALID", 1);
        expectedPhonesByCountryCode.put("United Kingdom", 1);

        List<Map.Entry<String, Integer>> expectedCount = new ArrayList<>(expectedPhonesByCountryCode.entrySet());

        assertEquals(expectedCount, countPhonesByCountryCode);
    }




    }
