package edu.guilford;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Stack;
import java.util.TreeSet;
import java.util.Vector;

public class DataStructure 
{
    public static void main( String[] args )
    {
        Scanner scan = new Scanner(System.in);


        Scanner scanFile = null;
        Path dataLocation = null;
        TreeSet<String> values = null;
        String fileName = null;

        System.out.println("Please enter the name of the file you would like to read from: ");
        fileName = scan.nextLine();
        //asks user to enter the name of the file they would like to read from

        //get the file location
        try {
            dataLocation = Paths.get(DataStructure.class.getResource("/" + fileName).toURI());
            FileReader dataFile = new FileReader(dataLocation.toString());
            BufferedReader dataBuffer = new BufferedReader(dataFile);
            scanFile = new Scanner(dataBuffer);
            values = readData(scanFile);
        } catch (URISyntaxException | FileNotFoundException | NullPointerException e) {
           e.printStackTrace();
        }

        try {
            writeData(values, "output.txt");
        } catch (IOException | URISyntaxException ex) {
            ex.printStackTrace();
            System.exit(1);
        }
    }

    //has to change this from double to string because we are reading words, not numbers 
    public static TreeSet readData(Scanner scan) {

        //decided to create a stack and a tree set to store the data
        //because the stack will allow us to remove the punctuation 
        //and the tree set will allow us to organize the data alphabetically
        Stack<String> stack = new Stack<String>();
        TreeSet<String> organizedData = new TreeSet<String>();
       

        //remove all punctuation from the file
        while (scan.hasNext()) {
            String word = scan.next();
            word = word.replaceAll("[^a-zA-Z0-9]", "");
            word = word.toLowerCase();
            word = word.replaceAll("[0-9]", "");
            stack.push(word);
        }

        //add all other elements from the file to the stack
        while (scan.hasNext()) {
            stack.push(scan.next());
        }

        //use the new class (Occurances) to store the number of occurances of each word
        //while stack is not empty, have it pop off the words off stack and put it in organizedData. Count 
        //the number of occurances of each word and add it to the tree set
        //for loop for the size of the stack, if the word is equal to the word at the index, remove the word at the index
        //and add one to the count. Then create a new Occurances object and add it to the tree set
        while (!stack.isEmpty()) {
            String word = stack.pop();
            int count = 1;
            for (int i = 1; i < stack.size(); i++) {
                if (word.equals(stack.get(i))) {
                    stack.remove(i);
                    count++;   
                }
            }
            Occurances o = new Occurances(word, count);
            //add the word and the number of occurances to the tree set
            organizedData.add(o.getWord() + " " + o.getCount());
            //add all of the occurances of the same word together and
            //remove extra copies of the word
            while (stack.contains(word)) {
                stack.remove(word);
            }
        }

        //pop each element of the stack and print it
        // while (!stack.isEmpty()) {
        //     System.out.println(stack.pop());
        // }

        //add all elements from the file to the tree set
        // while (!stack.isEmpty()) {
        //     organizedData.add(stack.pop());
        // }

        for (String s : organizedData) {
            System.out.println(s);
        }

        //prompt the user for a string, and return the number of occurances of that string
        // Scanner scan2 = new Scanner(System.in);
        // System.out.println("Please enter a string: ");
        // String input = scan2.nextLine();
        // // use the getCount() getter to get the number of occurances of the string
        // int count = 0;
        // for (int i = 0; i < organizedData.size(); i++) {
        //     if (input.equals(organizedData.get(i))) {
        //         count++;
        //     }
        // }
        // System.out.println("The number of occurances of " + input + " is " + count);


        //try reading the data from the file, catching any excpetions 
        // try {
        //     while (scan.hasNext()) {
        //         organizedData.add(scan.next());
        //     }
        // } catch (InputMismatchException ex) {
        //     System.out.println("Wrong type of data.");
        // } catch (NoSuchElementException ex) {
        //     System.out.println("Ran out of words.");
        // } 
        return organizedData;
    }


    //write the strings to a file 
    public static void writeData(TreeSet<String> values, String fileName) throws IOException, URISyntaxException {
        Path locationPath = Paths.get(DataStructure.class.getResource("/edu/guilford/").toURI());
        FileWriter outputFile = new FileWriter(locationPath.toString() + "/" + fileName);
        BufferedWriter bufferWrite = new BufferedWriter(outputFile);
        //write the data to the file
        for (String s : values) {
            bufferWrite.write(s);
            bufferWrite.newLine();
        }
        bufferWrite.close();
    }
    

    public static class ScannerException extends Exception {
        public ScannerException(String message) {
            super(message);
        }
    }


    //create a class for the number of occurances of each string
    public static class Occurances implements Comparable<Occurances>{
        private String word;
        private int count;

        public Occurances(String word, int count) {
            this.word = word;
            this.count = count;
        }

        public String getWord() {
            return word;
        }

        public int getCount() {
            return count;
        }

        public void setWord(String word) {
            this.word = word;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int compareTo(Occurances o) {
            if (this.count > o.count) {
                return 1;
            } else if (this.count < o.count) {
                return -1;
            } else {
                return 0;
            }
        }
    }

}

