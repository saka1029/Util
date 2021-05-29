import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;

/**
 * 指定したディレクトリの下に拡張子
 * .png, .jpg, .jpegのイメージファイルがあったら、
 * それらをまとめて単一のPDFファイルに出力する。
 * 出力するファイル名は「ディレクトリのパス名.pdf」とする。
 * ディレクトリの下にディレクトリがある場合は
 * 再帰的に処理する。
 *
 * [使い方]
 * java Pdf ディレクトリ
 */
public class Pdf {

    static final String USAGE = "java Pdf DIRECTORY";
    static final FileFilter IS_DIRECTORY = f -> f.isDirectory();
    static final FileFilter IS_IMAGE_FILE = f -> f.isFile()
        && f.getName().matches("(?i).*\\.(png|jpg|jpeg)$");

    static void printPdf(File outFile, File[] imageFiles) throws FileNotFoundException, MalformedURLException {
        PdfDocument pdf = new PdfDocument(new PdfWriter(outFile));
        Rectangle rect = pdf.getDefaultPageSize();
        try (Document document = new Document(pdf, PageSize.A4)) {
            document.setMargins(0, 0, 0, 0);
            for (File imageFile : imageFiles) {
                Image image = new Image(ImageDataFactory.create(imageFile.toString()));
                float wScale = rect.getWidth() / image.getImageWidth();
                float hScale = rect.getHeight() / image.getImageHeight();
                // 拡大率の小さい方でスケールする。
                float scale = Math.min(wScale, hScale);
                image.setWidth(image.getImageWidth() * scale);
                image.setHeight(image.getImageHeight() * scale);
                document.add(image);
            }
            System.out.println(outFile.getAbsolutePath() + " " + pdf.getNumberOfPages() + "pages");
        }
    }

    static void makePdf(File dir) throws FileNotFoundException, MalformedURLException {
        File[] imageFiles = dir.listFiles(IS_IMAGE_FILE);
        if (imageFiles.length > 0)
            printPdf(new File(dir.getPath() + ".pdf"), imageFiles);
        for (File subDir : dir.listFiles(IS_DIRECTORY))
            makePdf(subDir);
    }

    public static void main(String[] args) throws FileNotFoundException, MalformedURLException {
        if (args.length != 1)
            throw new IllegalArgumentException(USAGE);
        File dir = new File(args[0]);
        if (!dir.isDirectory())
            throw new IllegalArgumentException(USAGE);
        makePdf(dir);
    }

}
