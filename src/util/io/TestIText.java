package util.io;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

public class TestIText {

    @Test
    public void testTextFile() throws IOException {
        String text = "public class TestIText {\r\n"
            + "    @Test\r\n"
            + "    public void testParagraph() throws FileNotFoundException {\r\n"
            + "         System.out.println(\"日本語テキスト\");\r\n"
            + "    }\r\n"
            + "}";
//        PdfFont normal = PdfFontFactory.createFont(FontConstants.COURIER);
//        PdfFont normal = PdfFontFactory.createFont("HeiseiKakuGo-W5", "UniJIS-UCS2-H");
        PdfFont normal = PdfFontFactory.createFont("c:/windows/fonts/msgothic.ttc,0", "Identity-H");
        Paragraph para = new Paragraph();
        para.setFont(normal);
        File dest = new File("data/test.pdf");
        PdfDocument pdf = new PdfDocument(new PdfWriter(dest));
        try (Document document = new Document(pdf)) {
            para.add(text.replace(' ', '\u00a0'));
//            para.add(text);
            document.add(para);
        }
    }

}
