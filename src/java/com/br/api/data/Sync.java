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
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.codehaus.jettison.json.JSONException;

/**
 *
 * @author Wattana
 */
public class Sync extends HttpServlet {

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
            out.println("<title>Servlet Sync</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Sync at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession(true);
//        if(session.getAttribute("cono") == null){
//            response.sendRedirect("./login.jsp");
//        }

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
//        System.out.println("page = " + request.getParameter("page"));

        try {
            if (request.getParameter("page").equals("ListStaff")) {
                try {
                    out.print(Utility.ListStaff());
                    out.flush();
                } catch (JSONException ex) {
                    Logger.getLogger(Sync.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (request.getParameter("page").equals("ListCostCenter")) {
                try {
                    out.print(Utility.ListCostCenter());
                    out.flush();
                } catch (JSONException ex) {
                    Logger.getLogger(Sync.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (request.getParameter("page").equals("Grid")) {
                try {
                    if (request.getParameter("costc").equalsIgnoreCase("")) {
                        out.print(SelectDB2.Grid(request.getParameter("date")));
                        out.flush();
                    } else {
                        out.print(SelectDB2.GridSearch(request.getParameter("fromdate"), request.getParameter("todate"), request.getParameter("costc")));
                        out.flush();
                    }

                } catch (JSONException ex) {
                    Logger.getLogger(Sync.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (request.getParameter("page").equals("ListStaffC9999")) {
                try {
                    out.print(Utility.ListStaffC999());
                    out.flush();
                } catch (JSONException ex) {
                    Logger.getLogger(Sync.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (request.getParameter("page").equals("check_auth")) {
                try {
                    out.print(SelectDB2.check_auth(request.getParameter("st_code"), request.getParameter("st_costc"), request.getParameter("st_pass")));
                    out.flush();
                } catch (JSONException ex) {
                    Logger.getLogger(Sync.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (request.getParameter("page").equals("Customer")) {
                try {
                    out.print(Utility.Customer(request.getParameter("StaffCode")));
                    out.flush();
                } catch (JSONException ex) {
                    Logger.getLogger(Sync.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(Sync.class.getName()).log(Level.SEVERE, null, ex);
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
        HttpSession session = request.getSession(true);
        if (session.getAttribute("cono") == null) {
            response.sendRedirect("./login.jsp");
        }

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
//        System.out.println("page = " + request.getParameter("page"));

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

}
