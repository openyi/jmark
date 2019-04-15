package com.yifeistudio.jmark.core;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.state.PDExtendedGraphicsState;
import org.apache.pdfbox.util.Matrix;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class PDFBoxTests {

    @Test
    public void loadPDFTest() throws IOException {
        String filePath = "/home/yi/Downloads/Manning.HTTP2.in.Action.2019.3.pdf";
        try (PDDocument doc = PDDocument.load(new File(filePath))){
            System.out.println(String.format("number of pages: %d", doc.getNumberOfPages()));
            doc.setAllSecurityToBeRemoved(true);
            for (PDPage page : doc.getPages()) {
                String markText = "Some sample text";
                PDFont font = PDType1Font.HELVETICA_OBLIQUE;
                float fontSize = 50.0f;
                PDExtendedGraphicsState r0 = new PDExtendedGraphicsState();
                r0.setNonStrokingAlphaConstant(0.2f);
                r0.setAlphaSourceFlag(true);

                try (PDPageContentStream contentStream = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, true, true)) {
                    contentStream.setGraphicsStateParameters(r0);
                    contentStream.setNonStrokingColor(200, 0, 0);
                    contentStream.beginText();
                    contentStream.setFont(font, fontSize);

                    contentStream.setTextMatrix(Matrix.getRotateInstance(20, 350f, 490f));
                    contentStream.showText(markText);
                    contentStream.endText();
                }
            }
            doc.save("filepath.pdf");
        }
    }
}
