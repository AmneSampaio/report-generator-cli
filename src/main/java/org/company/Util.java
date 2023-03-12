package org.company;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Util {

    public static boolean isPhoneValid(String phone) {
        String phoneInternal = phone;

        if (phone.charAt(0) == '+') {
            phoneInternal = phone.substring(1);
        }

        if(phoneInternal.charAt(0) != ' ' && phoneInternal.matches("^[0-9\\ ]*$")){
            return true;
        }

        return false;
    }

    public static String checkPhoneType(String phone, List<String> countryCodes) {

        if (isShortPhone(phone)) {
            return "SHORT";
        }

        if (isLongPhone(phone, countryCodes)) {
            return "LONG";
        }

        return "INVALID";
    }

    public static boolean isShortPhone(String phone) {

        phone = phone.replaceAll(" ","");
        if(phone.charAt(0) == '0') {
            return false;
        }

        if (phone.length() <4 || phone.length() >6){
            return false;
        }

        return true;
    }

    public static boolean isLongPhone(String phone, List<String> countryCodes) {

        String[] splitPhone = phone.split(" ");

        if (splitPhone[0].equals("+") ||
                splitPhone[0].equals("0") ||
                splitPhone[0].equals("00") ) {

            return false;
        }

        String phoneInternal = phone;

        Boolean hasPlusSign = false;

        Boolean hasDoubleZeroPrefix = false;

        if (phone.charAt(0) == '+') {
            hasPlusSign = true;
            phoneInternal = phone.substring(1);
        } else if (phone.substring(0,1) == "00") {
            hasDoubleZeroPrefix = true;
            phoneInternal = phone.substring(2);
        }

        String[] splitPhoneInternal = phoneInternal.split(" ", 2);

        String phoneInternalWithoutSpaces = splitPhoneInternal[0].concat(splitPhoneInternal[1].replaceAll(" ", ""));
        if (phoneInternalWithoutSpaces.length() <9 || phoneInternalWithoutSpaces.length() >14){
            return false;
        }

        if (!countryCodes.contains(splitPhoneInternal[0])) {
            return false;
        }

        return true;
    }

    protected static Integer getCountryCode(String phone) {

        String phoneInternal = phone;

        if (phone.charAt(0) == '+') {
            phoneInternal = phone.substring(1);
        } else if (phone.substring(0,1) == "00") {
            phoneInternal = phone.substring(2);
        }

        String[] splitPhoneInternal = phoneInternal.split(" ", 2);

        return Integer.valueOf(splitPhoneInternal[0]);
    }

    static List<String> getDataFromFile(String filePath) throws IOException {
        List<String> fileLines = new ArrayList<>();
        try {
            Path path = FileSystems.getDefault().getPath(filePath);
            fileLines = Files.lines(path).collect(Collectors.toList());

        } catch (IOException exception) {
            System.out.format("File %s not found!", exception.getMessage());
            System.exit(1);
        }

        return fileLines;
    }

    static Map<String, String> turnTxtIntoMap(List<String> dataFromFile) throws IOException {

        Map<String,String> countryCodesFile = dataFromFile.stream()
                .map(s -> s.split("-"))
                .collect(Collectors.toMap(keyPosition -> keyPosition[1],
                        valuePosition -> valuePosition[0],
                        (keyPosition1, keyPosition2) -> keyPosition1.concat("," + keyPosition2)));

        return countryCodesFile;
    }



}

