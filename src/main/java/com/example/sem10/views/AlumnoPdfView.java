package com.example.sem10.views;

import com.example.sem10.domain.entities.Alumno;
import com.lowagie.text.Document;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import java.awt.*;
import java.util.List;
import java.util.Map;

@Component("alumno/ver")
public class AlumnoPdfView extends AbstractPdfView {

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
                                    HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<Alumno> alumnos = (List<Alumno>) model.get("alumnos");

        PdfPTable tabla = new PdfPTable(1);
        tabla.setSpacingAfter(20);

        PdfPCell cell = new PdfPCell(new Phrase("Lista de Alumnos"));
        cell.setBackgroundColor(new Color(184, 218, 255));
        cell.setPadding(8f);
        tabla.addCell(cell);

        PdfPTable tabla2 = new PdfPTable(5); // Definimos 5 columnas para cada atributo de Alumno
        tabla2.addCell("Id");
        tabla2.addCell("Nombre");
        tabla2.addCell("Apellido");
        tabla2.addCell("Edad");
        tabla2.addCell("Correo");

        for (Alumno alumno : alumnos) {
            tabla2.addCell(String.valueOf(alumno.getId()));
            tabla2.addCell(alumno.getNombre());
            tabla2.addCell(alumno.getApellido());
            tabla2.addCell(String.valueOf(alumno.getEdad()));
            tabla2.addCell(alumno.getCorreo());
        }

        document.add(tabla);
        document.add(tabla2);
    }
}
