package no.fun.stuff.linux.bash;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            new Prop2env().convertAll();
        } catch (IOException e) {
            System.out.println("Error in env2prop. Message: " + e.getMessage());
        }
    }
}