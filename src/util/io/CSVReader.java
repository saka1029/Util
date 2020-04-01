package util.io;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * CSVファイルを読み込むためのReader。
 * RFC4180で定義された形式のCSVファイルを読み込む。
 * 改行コードとしてはCR, LF, CR+LFの３種類を許容する。
 * 同一ファイル内でこれらが混在していてもよい。
 * ファイル末尾のレコードの終端には、改行はあってもなくてもよい。
 * スペースはフィールドの一部とみなし無視することはない。
 * フィールドは２重引用符で囲まれていてもよい。
 * この場合はフィールドの値としてカンマや２重引用符を含めることができる。
 * ２重引用符を含める場合は２つ連続して指定する必要がある。
 * フィールドが２重引用符で囲まれていて、末尾の２重引用符と
 * その後のカンマ（または改行またはファイル終端）の間にある文字は
 * 空白も含めてすべて有効なデータとみなす。
 */
public class CSVReader implements Closeable {

	private static final int DEFAULT_BUFFER_SIZE = 8192;
	private static final int EOF = -1;

	int prevCh = 0;
	final Reader input;

	public CSVReader(BufferedReader bufferedReader) {
		input = bufferedReader;
	}

	public CSVReader(Reader reader) {
		this(new BufferedReader(reader, DEFAULT_BUFFER_SIZE));
	}

	public CSVReader(InputStream inputStream, String encoding)
		throws UnsupportedEncodingException {
		this(new InputStreamReader(inputStream, encoding));
	}

	public CSVReader(String path)
		throws FileNotFoundException {
		this(new FileReader(path));
	}

	public CSVReader(String path, String encoding)
		throws UnsupportedEncodingException, FileNotFoundException {
		this(new FileInputStream(path), encoding);
	}

	public CSVReader(File file)
		throws FileNotFoundException {
		this(new FileReader(file));
	}

	public CSVReader(File file, String encoding)
		throws UnsupportedEncodingException, FileNotFoundException {
		this(new FileInputStream(file), encoding);
	}

	@Override
	public void close() throws IOException {
		input.close();
	}

	private int read() throws IOException {
	    return input.read();
	}

	/**
	 * １行分のデータを読み込み、カンマで区切って文字列のリストとして返す。
	 * @return 読み取ったフィールドのリスト
	 * @throws IOException
	 */
	public List<String> readFields() throws IOException {
		if (prevCh == EOF) return null;
		List<String> fields = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		boolean inQuote = false;
		while (true) {
			int ch = read();
			switch (ch) {
			case EOF: // EOF
				prevCh = ch;
				if (fields.size() == 0 && sb.length() == 0)
					return null;	// 行の始めでEOFの場合
				fields.add(sb.toString());
				return fields;
			case '\r':
				if (inQuote)
					sb.append((char)ch);
				else {
					fields.add(sb.toString());
					sb.setLength(0);
					prevCh = ch;
					return fields;
				}
				break;
			case '\n':
				if (inQuote)
					sb.append((char)ch);
				else if (prevCh == '\r')
					/* '\r'の後の'\n'をスキップする。 */;
				else {
					fields.add(sb.toString());
					sb.setLength(0);
					prevCh = ch;
					return fields;
				}
				break;
			case ',':
				if (inQuote)
					sb.append((char)ch);
				else {
					fields.add(sb.toString());
					sb.setLength(0);
				}
				break;
			case '"':
				if (!inQuote && prevCh == '"')  // 連続した'"'
					sb.append((char)ch);
				inQuote = !inQuote;
				break;
			default:
				sb.append((char)ch);
				break;
			}
			prevCh = ch;
		}
	}

}
