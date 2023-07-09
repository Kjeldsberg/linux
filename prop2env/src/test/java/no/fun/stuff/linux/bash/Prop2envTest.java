package no.fun.stuff.linux.bash;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class Prop2envTest {

    @Test
    void convertTest() throws IOException {
        String str = "hei.hallo=test";
        String str2 = "hallo.hei=ta=da";
        String str3 = "#HALLO_HEI=etst";
        String str4 = "HALLO_hei=etst";

        StringBuilder sb = new StringBuilder(str).append("\n")
                .append(str2).append("\n")
                .append(str3).append("\n")
                .append(str4).append("\n");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(sb.toString().getBytes());
        System.setIn(byteArrayInputStream);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));
        new Prop2env().convertAll();
        String s1 = new String(baos.toByteArray());
        BufferedReader bufferedInputStream = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(s1.getBytes())));
        String s = bufferedInputStream.readLine();
        String s2 = bufferedInputStream.readLine();
        String s3 = bufferedInputStream.readLine();
        String s4 = bufferedInputStream.readLine();

        assertEquals("HEI_HALLO=test", s);
        assertEquals("HALLO_HEI=ta=da", s2);
        assertEquals(str3, s3);
        assertEquals(str4, s4);
    }
    @Test
    void convertLineTest() {
        Prop2env env2Prop = new Prop2env();
        String s = env2Prop.convertLine("HEI_HE1=ehe=efe");
        assertEquals("hei.he1=ehe=efe", s);
    }
}