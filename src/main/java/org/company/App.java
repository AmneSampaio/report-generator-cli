package org.company;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.io.IOException;
import java.util.*;

public class App {
    public static void main(String[] args) throws IOException {

        Options options = new Options();

        Option invalidPhones = new Option("S", "show-invalid", false, "show invalid phones");
        options.addOption(invalidPhones);

        Option help = new Option("h", "help", false, "show help");
        options.addOption(help);

        Option version = new Option("v", "version", false, "show version");
        options.addOption(version);

        Option input = new Option("i", "input", true, "input phone file");
        options.addOption(input);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;
        String usageHelper = "java -jar report-generator.jar [INPUT FILE] [OPTIONS]";
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp(usageHelper, options);

            System.exit(1);
        }

        if (args.length == 0) {
            formatter.printHelp(usageHelper, options);
            System.exit(1);
        }

        if(cmd.hasOption("h")) {
            formatter.printHelp(usageHelper, options);
            System.exit(0);
        }

        if(cmd.hasOption("v")) {
            System.out.println("report-generator-cli.jar v1.0.0");
            System.exit(0);
        }

        String inputFile;

        if(cmd.hasOption("i")) {
            inputFile = cmd.getOptionValue("input");
        } else {
            String[] positionalArgs = cmd.getArgs();
            inputFile = positionalArgs[0];
        }

        List<String> inputPhones = Util.getDataFromFile(inputFile);
        Map<String, String> countryCodes = Util.turnTxtIntoMap(Util.getDataFromFile("./countryCodes.txt"));

        ArrayList<Phone> sortedPhones = phoneClassification(inputPhones, countryCodes.keySet().stream().toList());
        List<Map.Entry<String, Integer>> countryCodesReport = countPhonesByCountryCode(sortedPhones, countryCodes);
        List<Map.Entry<String, Integer>> countryCodesReportCopy = new ArrayList<>(countryCodesReport);

        if (!cmd.hasOption("S")) {

            for (Map.Entry<String, Integer> stringIntegerEntry : countryCodesReport) {
                if (stringIntegerEntry.getValue() == 0 || stringIntegerEntry.getKey().equals("INVALID")) {
                    countryCodesReportCopy.remove(stringIntegerEntry);
                }
            }
        }

        countryCodesReportCopy.forEach(System.out::println);

    }

    public static List<Map.Entry<String, Integer>> countPhonesByCountryCode(ArrayList<Phone> sortedPhonesList, Map<String, String> countryNamesWithCodes){

        HashMap<String, Integer> phonesByCountryReport = new HashMap<>();

        for (String country: countryNamesWithCodes.values()) {
            phonesByCountryReport.put(country,0);
        }

        phonesByCountryReport.put("INVALID",0);

        String countryName;

        for (Phone phone: sortedPhonesList) {
            Integer countryCode;
            countryCode = phone.getCountryCode();
            if (countryCode == 0) {
                countryName = "INVALID";
            } else {
                countryName = countryNamesWithCodes.get(String.valueOf(countryCode));
            }
            phonesByCountryReport.put(countryName, phonesByCountryReport.get(countryName) + 1);
        }

        List<Map.Entry<String,Integer>> nlist = new ArrayList<>(phonesByCountryReport.entrySet());
        nlist.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        return nlist;

    }

    public static ArrayList<Phone> phoneClassification(List<String> inputPhones, List<String> countryCodes) {
        ArrayList<Phone> result = new ArrayList<>();
        for (String linePhone: inputPhones) {

            boolean phoneStatus = Util.isPhoneValid(linePhone);
            if (!phoneStatus) {
                Phone phone = new Phone(0, phoneStatus, linePhone,"INVALID");
                result.add(phone);
                continue;
            }

            String phoneType = Util.checkPhoneType(linePhone, countryCodes);
            int countryCode = 0;

            if (phoneType.equals("SHORT")){
                countryCode=351;
            } else if (phoneType.equals("LONG")) {
                countryCode = Util.getCountryCode(linePhone);
            }

            Phone phone = new Phone(countryCode, phoneStatus, linePhone, phoneType);

            result.add(phone);
        }
        return result;

    }
}
