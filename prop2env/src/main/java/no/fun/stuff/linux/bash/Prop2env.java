package no.fun.stuff.linux.bash;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class Prop2env {
    public void convertAll() throws IOException {
        if (System.in.available() == 0) {
            return;
        }
        final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line = reader.readLine();
        while (line != null) {
            String s = System.lineSeparator();
            System.out.print(convertLine(line + s));
            line = reader.readLine();
        }
    }

    public String convertLine(String line) {
        if(commentedLine.check(line)) {
            return line;
        }
        String[] split = line.split("=");
        List<String> list = Arrays.asList(split);
        if (split.length < 2) {
            return line;
        }
        if (split.length > 2) {
            List<String> strings = list.subList(1, list.size());
            split = new String[]{split[0], contact(strings, "=")};
        }
        String[] variableName = split[0].split("\\.");
        if(!allLowerCase.check(variableName)) {
            return line;
        }

        String lowerCase = contact(Arrays.asList(variableName), "_").toUpperCase();
        return lowerCase.concat("=").concat(split[1]);
    }

    public static String contact(final List<String> list, String delimiter) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0 ; i < list.size() - 1; i++) {
            sb.append(list.get(i)).append(delimiter);
        }
        sb.append(list.get(list.size() - 1));
        return sb.toString();
    }
    private final CheckRule<String> commentedLine = input -> input.startsWith("#");
    private final CheckRule<String[]> allLowerCase = input -> {
        if(input == null) {
            return false;
        }
        for(String s : input) {
            if(s == null || s.isEmpty()) {
                return false;
            }
            for (int i = 0; i<s.length();i++) {
                if(Character.isUpperCase(s.charAt(i))) {
                    return false;
                }
            }
        }
        return true;
    };
    private interface CheckRule<T> {
        boolean check(final T input);
    }
}
