/**
 * Write a description of class Speeches here.
 *
 * @author Kevin Mattias
 * @version 10/14/2026
 */

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Speeches
{
    public static void main(String[] args) throws IOException {
    // let them choose the file
    System.out.println("What file do you want to scan?");
    Scanner scan = new Scanner(System.in);
    String file1= scan.next();
    File file = new File("speeches/" + file1);
    
    Scanner scanner = new Scanner(file);
    String rawJson = "";
    while (scanner.hasNextLine()) {
        rawJson += scanner.nextLine() + " ";
    }
    scanner.close();

    // Extracting fields using our helper method
    System.out.println(rawJson);
    String president = extractJsonValue(rawJson, "president");
    String date = extractJsonValue(rawJson, "date");
    String speechText = extractJsonValue(rawJson, "text");
    // print the president, date, and charachter length.
    System.out.println("President: " + president);
    System.out.println("Date: " + date);
    System.out.println("Speech Length (Chars): " + rawJson.length());
    //get our target words
    String[] targetWords = {"people", "government", "united", "our"};
    //make everything lowercase
    String cleanText = rawJson.replaceAll("[^a-zA-Z]", " ").toLowerCase();
    //makes it an array and splits each word into the array correctly
    String[] words = cleanText.trim().split("\\s+");
        for (int i = 0; i < targetWords.length; i++) {
    String target = targetWords[i];
    
    int count = 0;
    for (int j = 0; j < words.length; j++) {
        if (words[j].equals(target)) {
            count++;
        }
    }
    double percentage = ((double) count / rawJson.length()) * 100.0;
    //print our results
    System.out.printf(" - %-10s Count: %-5d Percentage: %.2f%%%n", target + ":", count, percentage);
        System.out.println("==========================================");
    }
    double totalCharacters = 0;
    // get the length of the charachters
    for (int i = 0; i < words.length; i++) {
        totalCharacters += words[i].length();
    }
    int wordCount = words.length;
    // print the word count and the average word length    
    System.out.println("Total word count: " + wordCount);
    System.out.println("Average Word Length is: "+ (double)(totalCharacters/words.length));
    /**
     * Extracts the String value associated with a JSON key.
     * Example: extractJsonValue(jsonString, "president") returns "George Washington"
     */ 
}
    public static String extractJsonValue(String json, String key) {
    String keyTarget = "\"" + key + "\":";
    int keyIndex = json.indexOf(keyTarget);
    if (keyIndex == -1) {
        return "";
    }

    // Locate the first opening quote after the key
    int startQuote = json.indexOf("\"", keyIndex + keyTarget.length());
    if (startQuote == -1) {
        return "";
    }

    // Locate the matching closing quote
    int endQuote = json.indexOf("\"", startQuote + 1);
    
    // Handle escaped quotes inside the speech text (e.g., \")
    while (endQuote != -1 && json.charAt(endQuote - 1) == '\\') {
        endQuote = json.indexOf("\"", endQuote + 1);
    }

    if (endQuote == -1) {
        return "";
    }

    return json.substring(startQuote + 1, endQuote);
}

}