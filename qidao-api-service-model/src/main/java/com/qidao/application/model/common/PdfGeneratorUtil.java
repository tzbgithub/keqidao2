package com.qidao.application.model.common;

import cn.hutool.core.io.FileUtil;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.output.ByteArrayOutputStream;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class PdfGeneratorUtil {

    @SneakyThrows
    public static InputStream generatorInputStream(String html) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Document document = new Document(PageSize.A4);
            PdfWriter mPdfWriter = PdfWriter.getInstance(document, outputStream);
            document.open();
            ByteArrayInputStream bin = new ByteArrayInputStream(html.getBytes());
            XMLWorkerHelper.getInstance().parseXHtml(mPdfWriter, document, bin, StandardCharsets.UTF_8, new ChinaFontProvide());
            document.close();
            mPdfWriter.close();

            return new ByteArrayInputStream(outputStream.toByteArray());
    }

    private static class ChinaFontProvide implements FontProvider {
        private final Font front;
        @Override
        public boolean isRegistered(String s) {
            return false;
        }

        @Override
        public Font getFont(String arg0, String arg1, boolean arg2, float arg3,
                            int arg4, BaseColor arg5) {
            return front;
        }

        @SneakyThrows
        public ChinaFontProvide(){
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            this.front = new Font(bfChinese, 12, Font.NORMAL);
        }
    }

}

