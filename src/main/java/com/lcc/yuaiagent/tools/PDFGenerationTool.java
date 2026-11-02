package com.lcc.yuaiagent.tools;

import cn.hutool.core.io.FileUtil;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.lcc.yuaiagent.Constant.FileConstant;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component // 👈 建议加上
public class PDFGenerationTool {

    @Tool(description = "Generate a PDF file with given content")
    public String generatePDF(
            @ToolParam(description = "Name of the file to save the generated PDF, must end with .pdf") String fileName,
            @ToolParam(description = "Content to be included in the PDF") String content) {

        // 容错处理：如果模型没加 .pdf 后缀，帮它加上
        if (!fileName.toLowerCase().endsWith(".pdf")) {
            fileName += ".pdf";
        }

        String fileDir = FileConstant.FILE_SAVE_DIR + "/pdf";
        String filePath = fileDir + "/" + fileName;

        try {
            // 创建目录
            FileUtil.mkdir(fileDir);

            // 创建 PdfWriter 和 PdfDocument 对象
            try (PdfWriter writer = new PdfWriter(filePath);
                 PdfDocument pdf = new PdfDocument(writer);
                 Document document = new Document(pdf)) {

                // ==========================================
                // 👇 重点修改：使用 ClassPathResource 读取字体流 👇
                // ==========================================
                // 这种方式无论打不打包成 JAR，都能准确找到字体文件
                ClassPathResource fontResource = new ClassPathResource("fonts/msyh.ttf");

                // 使用流加载字体，并指定以字节流形式读取，设置为优先嵌入字体
                PdfFont font = PdfFontFactory.createFont(
                        fontResource.getInputStream().readAllBytes(),
                        PdfFontFactory.EmbeddingStrategy.PREFER_EMBEDDED
                );

                // 设置全局字体
                document.setFont(font);

                // 创建段落
                Paragraph paragraph = new Paragraph(content);
                // 添加段落
                document.add(paragraph);
            }
            return "PDF generated successfully to: " + filePath;

        } catch (IOException e) {
            return "Error generating PDF: " + e.getMessage();
        }
    }
}