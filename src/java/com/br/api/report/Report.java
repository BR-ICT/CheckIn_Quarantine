/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.api.report;

import com.br.api.connect.ConnectPG;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

/**
 *
 * @author Wattana
 */
@WebServlet(name = "Report", urlPatterns = {"/Report"})
@MultipartConfig
public class Report extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Report</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Report at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(true);

        if (request.getParameter("vReport").equalsIgnoreCase("RP04_EXCEL")) {
            try {
                SimpleDateFormat formatter1 = new SimpleDateFormat("");
                String path = getServletContext().getRealPath("/Report/");

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String fdate = request.getParameter("fromdate_ex").toString().trim();
                String tdate = request.getParameter("todate_ex").toString().trim();
                Date parsed = format.parse(fdate);

//                java.sql.Date sql = new java.sql.Date(parsed.getTime());

                Map parameterss = new HashMap();

                parameterss.put("frmdate", fdate);
                parameterss.put("todate", tdate);
                
                

                JasperDesign jasperDesign;
                try {
                    jasperDesign = JRXmlLoader.load(path + "SALE_OT.jrxml");
                    JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
                    Connection connaaa = null;
                    try {
                        connaaa = ConnectPG.ConnectionPG();
                    } catch (Exception ex) {
                        Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    JasperPrint jasp = JasperFillManager.fillReport(jasperReport, parameterss, connaaa);
                    response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                    response.setHeader("Content-Disposition", "attachment; filename=\"" + "SALE_OT.xlsx" + "\"");
                    JRXlsxExporter exporterXls = new JRXlsxExporter();
                    ServletOutputStream ouputStream = response.getOutputStream();
                    exporterXls.setParameter(JRExporterParameter.JASPER_PRINT, jasp);
                    exporterXls.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
                    exporterXls.exportReport();
                    ouputStream.flush();
                    ouputStream.close();
                } catch (Exception ex) {
                    Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);
                }

            } catch (ParseException ex) {
                Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        processRequest(request, response);
        HttpSession session = request.getSession(true);

    }

}
