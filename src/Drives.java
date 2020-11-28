

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.regex.Pattern;

import util.io.WindowsTreeEntry;
import util.io.WindowsTreeReader;

/**
 * ドライブごとのtreeコマンド実行結果（/drivesにあるファイル）
 * についてgrepします。
 * usage:
 * java Drives [-f|-d|-a] 検索文字列
 * -f ファイルのみ検索
 * -d ファイルのみ検索
 * -a すべてを検索
 * 検索文字列 以下のワイルドカード使用可能です。
 *            *: 0文字以上の任意の文字列
 *            ?: 任意の1文字
 *
 */
public class Drives {

    static final File TOP_DIRECTORY = new File("/git.backup/drives");

    enum Target {
        FILE, DIRECTORY, ANY;

        public static Target parse(String s) {
            if (s == null || s.length() != 1) return null;
            switch (s.toLowerCase()) {
            case "f": return FILE;
            case "d": return DIRECTORY;
            case "a": return ANY;
            default: return null;
            }
        }
    }

    final Target target;
    final Pattern pattern;

    static Pattern pattern(String pat) {
        pat = pat.replace(".", "\\\\.")
            .replace("\\", "/")
            .replace("?", ".")
            .replace("*", ".*");
        return Pattern.compile(pat, Pattern.CASE_INSENSITIVE);
    }

    Drives(Target target, String pattern) {
        this.target = target;
        this.pattern = pattern(pattern);
    }

    boolean isTarget(boolean isFile) {
        return target == Target.ANY
            || target == Target.FILE && isFile
            || target == Target.DIRECTORY && !isFile;
    }

    void run(File file) throws IOException {
        if (file.isDirectory())
            for (File child : file.listFiles())
                run(child);
        else
            try (Reader r = new FileReader(file, WindowsTreeReader.DEFAULT_CHARSET);
                WindowsTreeReader reader = new WindowsTreeReader(r)) {
                WindowsTreeEntry e;
                while ((e = reader.read()) != null)
                    if (isTarget(e.isFile) && pattern.matcher(e.path()).find())
                        System.out.println(e);
            }
    }

    static void usage() {
        throw new IllegalArgumentException("Usage: java Drives [-a|-f|-d] PATTERN");
    }

    public static void main(String[] args) throws IOException {
        Target target = Target.ANY;
        String pattern = null;
        for (int i = 0, size = args.length; i < size; ++i)
            if (args[i].length() >= 2 && args[i].charAt(0) == '-') {
                target = Target.parse(args[i].substring(1));
                if (target == null) usage();
            } else if (pattern != null)
                usage();
            else
                pattern = args[i];
        if (pattern == null) usage();
        new Drives(target, pattern).run(TOP_DIRECTORY);
    }

}
