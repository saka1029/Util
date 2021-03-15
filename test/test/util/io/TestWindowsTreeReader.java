package test.util.io;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.junit.Test;

import util.io.WindowsTreeEntry;
import util.io.WindowsTreeReader;


public class TestWindowsTreeReader {

    @Test
    public void testRead() throws IOException {
        File file = new File("D:/git.backup/drives/TOSHIBA_EXT.txt");
        try (FileReader fr = new FileReader(file, WindowsTreeReader.DEFAULT_CHARSET);
            WindowsTreeReader reader = new WindowsTreeReader(fr);
            PrintWriter w = new PrintWriter(new FileWriter(file.getName() + ".debug"))) {
            while (true) {
                WindowsTreeEntry e = reader.read();
                if (e == null) break;
                w.println(e);
            }
        }

    }

}
