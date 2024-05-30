package jobtes.kadua4.services;

import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;

import jakarta.servlet.http.HttpServletResponse;
import jobtes.kadua4.model.MemberMDL;

@Service
public class ExportService {
    @Autowired
    private final MemberService memberService;

    @Autowired
    public ExportService(MemberService memberService) {
        this.memberService = memberService;
    }

    public void exportToPDF(HttpServletResponse response) throws IOException {
        List<MemberMDL> members = memberService.getAllMembers();
        
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=members.pdf");
        
        PdfWriter writer = new PdfWriter(response.getOutputStream());
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        Table table = new Table(UnitValue.createPercentArray(new float[]{2, 2, 2, 2, 2})).useAllAvailableWidth();

        table.addHeaderCell("Name");
        table.addHeaderCell("No KTP");
        table.addHeaderCell("Tanggal Lahir");
        table.addHeaderCell("Email");
        table.addHeaderCell("Jenis Kelamin");

        for (MemberMDL member : members) {
            table.addCell(member.getName());
            table.addCell(member.getNo_ktp());
            table.addCell(member.getTanggal_lahir().toString());
            table.addCell(member.getEmail());
            table.addCell(member.getJenis_kelamin());
        }

        document.add(table);
        document.close();
    }

    public void exportToExcel(HttpServletResponse response) throws IOException {
        List<MemberMDL> members = memberService.getAllMembers();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Members");

        Row headerRow = sheet.createRow(0);
        String[] headers = {"Name", "No KTP", "Tanggal Lahir", "Email", "Jenis Kelamin"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        int rowNum = 1;
        for (MemberMDL member : members) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(member.getName());
            row.createCell(1).setCellValue(member.getNo_ktp());
            row.createCell(2).setCellValue(member.getTanggal_lahir().toString());
            row.createCell(3).setCellValue(member.getEmail());
            row.createCell(4).setCellValue(member.getJenis_kelamin());
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=members.xlsx");

        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
