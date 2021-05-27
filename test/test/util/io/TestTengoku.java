package test.util.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.junit.Test;

public class TestTengoku {

    @Test
    public void test() throws IOException {
        File dir = new File("D:\\Downloads\\天国大魔境\\Tengoku Daimakyou v05s");
        for (File c : dir.listFiles()) {
            String h = c.getName();
            System.out.println(c.getName());
            for (File cc : c.listFiles()) {
                File dest = new File(dir, h + "_" + cc.getName());
                Files.copy(cc.toPath(), dest.toPath());
                System.out.println("  " + dest);
            }
        }
    }

}
