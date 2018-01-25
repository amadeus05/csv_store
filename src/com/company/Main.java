package com.company;

import com.company.csv.CSV;

import java.io.IOException;

public class Main {
    public static final String filePath11 = "all.csv";
    public static void main(String[] args) throws IOException {
	// write your code here
        CSV csv_ = new CSV(filePath11);
        csv_.scan1();
        Emulate e = new Emulate();
        e.start_emulate_days();
    }
}
