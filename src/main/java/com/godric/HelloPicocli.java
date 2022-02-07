package com.godric;

import picocli.CommandLine;

@CommandLine.Command()
public class HelloPicocli implements Runnable {

    @Override
    public void run() {
        System.out.println("Hello, Picocli");
    }

    public static void main(String[] args) {
        new CommandLine(new HelloPicocli()).execute(args);
    }
}
