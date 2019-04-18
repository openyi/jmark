package com.yifeistudio.jmark.core.impl;

import com.yifeistudio.jmark.core.DefaultMarker;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.state.PDExtendedGraphicsState;
import org.apache.pdfbox.util.Matrix;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class DefaultMarkerImpl implements DefaultMarker {

    private String filePath;
    private PDFont markFont;
    private Float fontSize;
    private Float transparent;
    private String outputPath;
    private int r = 200;
    private int g;
    private int b;

    private PDDocument document;

    public DefaultMarkerImpl(String filePath) {
        this.filePath = filePath;
        try {
            document = PDDocument.load(new File(filePath));
            document.setAllSecurityToBeRemoved(true);
        } catch (IOException e) {
            throw new IllegalArgumentException(String.format("illegal file path: %s", e.getMessage()));
        }
    }

    @Override
    public void mark(String text) {
        PDExtendedGraphicsState r0 = new PDExtendedGraphicsState();
        r0.setNonStrokingAlphaConstant(transparent);
        r0.setAlphaSourceFlag(true);
        for (PDPage page : document.getPages()) {
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true)) {
                contentStream.setGraphicsStateParameters(r0);
                contentStream.setNonStrokingColor(r, g, b);
                contentStream.beginText();
                contentStream.setFont(Optional.ofNullable(markFont).orElse(PDType1Font.HELVETICA_OBLIQUE), Optional.ofNullable(fontSize).orElse(50.0f));
                contentStream.setTextMatrix(Matrix.getRotateInstance(20, 350f, 490f));
                contentStream.showText(text);
                contentStream.endText();
            } catch (IOException e) {
                throw new RuntimeException(String.format("mark failed: %s", e.getMessage()));
            }
        }
        save();
    }

    private void save(){
        try {
            document.save(Optional.ofNullable(this.outputPath).orElse(filePath));
            document.close();
        } catch (IOException e) {
            throw new RuntimeException(String.format("save file error: %s", e.getMessage()));
        }
    }

    public static class MarkBuilder {

        private String filePath;
        private PDFont markFont;
        private Float fontSize;
        private Float transparent;
        private String outputPath;
        private int red;
        private int green;
        private int blue;

        public MarkBuilder filePath(String filePath) {
            this.filePath = filePath;
            return this;
        }

        public MarkBuilder markFont(PDFont markFont) {
            this.markFont = markFont;
            return this;
        }

        public MarkBuilder fontSize(float fontSize) {
            this.fontSize = fontSize;
            return this;
        }

        public MarkBuilder transparent(float transparent) {
            this.transparent = transparent;
            return this;
        }

        public MarkBuilder rgb(int r, int g, int b) {
            this.red = r;
            this.green = g;
            this.blue = b;
            return this;
        }

        public MarkBuilder outputPath(String outputPath) {
            this.outputPath = outputPath;
            return this;
        }

        public DefaultMarkerImpl build() {
            DefaultMarkerImpl defaultMarker = new DefaultMarkerImpl(filePath);
            defaultMarker.outputPath = this.outputPath;
            defaultMarker.markFont = this.markFont;
            defaultMarker.fontSize = this.fontSize;
            defaultMarker.transparent = this.transparent;
            defaultMarker.r = this.red;
            defaultMarker.g = this.green;
            defaultMarker.b = this.blue;
            return defaultMarker;
        }
    }
}
