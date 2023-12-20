/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.api.data;

import com.br.api.connect.ConnectPG;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Jilasak
 */
@WebServlet(name = "CheckIn", urlPatterns = {"/CheckIn"})
@MultipartConfig
public class CheckIn extends HttpServlet {

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
            out.println("<title>Servlet CheckIn</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CheckIn at " + request.getContextPath() + "</h1>");
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
        processRequest(request, response);
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
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        Timestamp TIME = new Timestamp(System.currentTimeMillis());
        String StaffCode = request.getParameter("StaffCode").trim();
        String Location = request.getParameter("LOCATION").trim();
        String Type = request.getParameter("Type").trim();
        LocalDate date = LocalDate.now();
        String DATE_NOW = String.valueOf(date).trim();
        Connection conn = null;
        Connection connectDB2 = null;

        if (Type.equals("VSC")) {
            try {
                String Customer = request.getParameter("vCustomer").trim();
                String SC_REMK = request.getParameter("remark").trim();
                connectDB2 = ConnectPG.ConnectionDB2();
                String TOM = new SimpleDateFormat("HH:mm:ss").format(TIME);
                int NO = Checkdata(StaffCode, Customer, DATE_NOW);
                if (NO == 0 || Customer.equalsIgnoreCase("Other") || Customer.equalsIgnoreCase("TH01010719")) {
                    Statement stmtAS400 = connectDB2.createStatement();
                    NO++;
                    String sql = "INSERT INTO BRLDTA0100.SALE_CHECKIN\n"
                            + "(SC_CONO, SC_DIVI, SC_CODE, SC_CUNO, SC_DATE, SC_INTM, SC_OTTM, SC_LOCA, SC_REMK1, SC_REMK2, SC_LINE)\n"
                            + "VALUES('10', '101', '" + StaffCode.trim() + "', '" + Customer.trim() + "', '" + DATE_NOW.trim() + "',"
                            + " '" + TOM + "', '" + TOM + "', '" + Location.trim() + "', '" + SC_REMK.trim() + "', '', '" + NO + "')";
                    stmtAS400.executeUpdate(sql);
                    System.out.println(sql);
                    stmtAS400.close();

                } else {
                    Statement stmtAS400 = connectDB2.createStatement();
                    String sql = "UPDATE BRLDTA0100.SALE_CHECKIN\n"
                            + "SET SC_OTTM = '" + TOM + "' , SC_REMK2 = '" + SC_REMK.trim() + "'\n"
                            + "WHERE SC_CONO = '10' AND SC_DIVI = '101' AND SC_CODE = '" + StaffCode.trim() + "' AND SC_CUNO = '" + Customer.trim() + "' AND SC_DATE = '" + DATE_NOW.trim() + "'";
                    stmtAS400.executeUpdate(sql);
                    stmtAS400.close();
                }
            } catch (Exception ex) {
                Logger.getLogger(CheckIn.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        try {
            conn = ConnectPG.ConnectionPG();
            Statement stmt = conn.createStatement();
            String query = "INSERT INTO check_qrtine\n"
                    + "(cq_code, cq_date, cq_inloc, cq_intm, cq_type)\n"
                    + "VALUES('" + StaffCode.trim() + "', '" + DATE_NOW.trim() + "', '" + Location.trim() + "', '" + TIME + "' , '" + Type.trim() + "')";
            stmt.execute(query);

        } catch (Exception ex) {
            Logger.getLogger(CheckIn.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
//                    connectDB2.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CheckIn.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    public int Checkdata(String staffcode, String customer, String date) {
        int Count = 0;
        Connection connectDB2 = null;
        try {
            connectDB2 = ConnectPG.ConnectionDBAS400();
            Statement stmtAS400 = connectDB2.createStatement();
            String sql = "SELECT count(*) AS count \n"
                    + "FROM BRLDTA0100.sale_checkin\n"
                    + "WHERE SC_CONO = '10'\n"
                    + "AND SC_DIVI = '101'\n"
                    + "AND SC_CODE = '" + staffcode.trim() + "'\n"
                    + "AND SC_CUNO = '" + customer.trim() + "'\n"
                    + "AND SC_DATE = '" + date.trim() + "'";
            ResultSet rsl = stmtAS400.executeQuery(sql);
            while (rsl.next()) {
                Count = rsl.getInt("count");
            }
            connectDB2.close();
            stmtAS400.close();
        } catch (Exception ex) {
            Logger.getLogger(CheckIn.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Count;
    }
}
