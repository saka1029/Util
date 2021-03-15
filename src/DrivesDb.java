import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import util.io.WindowsTreeEntry;
import util.io.WindowsTreeReader;

public class DrivesDb {

    public static void main(String[] args) throws SQLException, IOException {
        File dir = new File("d:/git.backup/drives");
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dir.getAbsolutePath() + "/drives.db")) {
            conn.setAutoCommit(false);
            try (Statement stat = conn.createStatement()) {
                stat.execute("drop table if exists drives");
                stat.execute("create table drives (volume string, type string, path string)");
            }
            try (PreparedStatement stat = conn.prepareStatement("insert into drives values (?, ?, ?)")) {
                for (File file : dir.listFiles(f -> f.getName().toLowerCase().endsWith(".txt"))) {
                    if (file.isDirectory()) continue;
                    try (FileReader fr = new FileReader(file, WindowsTreeReader.DEFAULT_CHARSET);
                        WindowsTreeReader reader = new WindowsTreeReader(fr);) {
                        int i = 0;
                        WindowsTreeEntry e;
                        while ((e = reader.read()) != null) {
                            stat.setString(1, e.volume);
                            stat.setString(2, e.isFile ? "F" : "D");
                            stat.setString(3, e.path());
                            stat.addBatch();
                            if (++i % 1000 == 0)
                                stat.executeBatch();
                        }
                        stat.executeBatch();
                    }
                }
            }
            conn.commit();
        }
    }

}
