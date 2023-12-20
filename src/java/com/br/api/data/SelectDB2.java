/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.api.data;

import com.br.api.connect.ConnectPG;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jettison.json.JSONArray;

/**
 *
 * @author Wattana
 */
public class SelectDB2 {

    DecimalFormat df = new DecimalFormat("#0.00");

    public static JSONArray ListStaff() throws Exception {

        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectPG.ConnectionPG();

        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
                String query = "SELECT st_cono, st_code, st_ename, st_elname, st_costc, st_costn, st_position, st_type, st_count\n"
                        + "from employee_all";

                ResultSet mRes = stmt.executeQuery(query);

                while (mRes.next()) {
                    Map<String, Object> mMap = new HashMap<>();
                    mMap.put("st_cono", mRes.getString(1).trim());
                    mMap.put("st_code", mRes.getString(2).trim());
                    mMap.put("st_ename", mRes.getString(3).trim());
                    mMap.put("st_elname", mRes.getString(4).trim());
                    mMap.put("st_costc", mRes.getString(5).trim());
                    mMap.put("st_costn", mRes.getString(6).trim());
                    mMap.put("st_position", mRes.getString(7).trim());
                    mMap.put("st_type", mRes.getString(8).trim());
                    mMap.put("st_count", mRes.getString(9).trim());
                    mJSonArr.put(mMap);
                }
            } else {
                System.out.println("Server can't connect.");
            }
        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                conn.close();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return mJSonArr;
    }

    public static JSONArray ListStaffC999() throws Exception {

        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectPG.ConnectionPG();

        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
                String query = "SELECT st_cono, st_code, st_ename, st_elname, st_costc, st_costn, st_position, st_type, st_count\n"
                        + "from employee_all\n"
                        + "where st_costc like  '%C999%' ";

                ResultSet mRes = stmt.executeQuery(query);

                while (mRes.next()) {
                    Map<String, Object> mMap = new HashMap<>();
                    mMap.put("st_cono", mRes.getString(1).trim());
                    mMap.put("st_code", mRes.getString(2).trim());
                    mMap.put("st_ename", mRes.getString(3).trim());
                    mMap.put("st_elname", mRes.getString(4).trim());
                    mMap.put("st_costc", mRes.getString(5).trim());
                    mMap.put("st_costn", mRes.getString(6).trim());
                    mMap.put("st_position", mRes.getString(7).trim());
                    mMap.put("st_type", mRes.getString(8).trim());
                    mMap.put("st_count", mRes.getString(9).trim());
                    mJSonArr.put(mMap);
                }
            } else {
                System.out.println("Server can't connect.");
            }
        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                conn.close();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return mJSonArr;
    }

    public static JSONArray ListCostCenter() throws Exception {

        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectPG.ConnectionPG();

        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
                String query = "select st_costc,st_costn\n"
                        + "from cost_center_all\n"
                        + "order by st_costc asc";

                ResultSet mRes = stmt.executeQuery(query);

                while (mRes.next()) {
                    Map<String, Object> mMap = new HashMap<>();
                    mMap.put("st_costc", mRes.getString(1).trim());
                    mMap.put("st_costn", mRes.getString(2).trim());

                    mJSonArr.put(mMap);
                }
            } else {
                System.out.println("Server can't connect.");
            }
        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                conn.close();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return mJSonArr;
    }

    public static int checkdata(String staffcode, String date) {
        int checkdata = 0;
        try {

            Connection conn = ConnectPG.ConnectionPG();
            String sql = "select count(*) as checkdata\n"
                    + "from check_qrtine\n"
                    + "where cq_code = '" + staffcode.trim() + "'\n"
                    + "and cq_date = '" + date.trim() + "'";
            Statement stmt = conn.createStatement();
            ResultSet mRes = stmt.executeQuery(sql);
            while (mRes.next()) {
                checkdata = mRes.getInt("checkdata");
            }

        } catch (Exception ex) {
            Logger.getLogger(SelectDB2.class.getName()).log(Level.SEVERE, null, ex);
        }
        return checkdata;

    }

    public static JSONArray Grid(String today) throws Exception {

        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectPG.ConnectionPG();

        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
                String query = "SELECT cq_code, st_ename,st_elname,st_costc,cq_date, to_char(cq_intm, 'HH24:MI:SS') as cq_intm,cq_inloc,trim(st_ename) || ' ' || trim(st_elname) as ename\n"
                        + ",case when coalesce(cq_type,'') = 'OWK' then 'Out working' when coalesce(cq_type,'') = 'WFH' then 'Work from home'\n"
                        + "when coalesce(cq_type,'') = 'QRT' then 'Quarantine' \n"
                        + "when coalesce(cq_type,'') = 'VSC' then 'Visit customer'  else coalesce(cq_type,'') end as cq_type\n"
                        + "from check_qrtine,employee_all\n"
                        + "where cq_code = st_code\n"
                        + "and date(cq_date)= '" + today.trim() + "'\n"
                        //                        + "--and cq_code = '01'\n"
                        + "order by cq_intm,cq_code";
//                System.out.println(query);
                ResultSet mRes = stmt.executeQuery(query);

                while (mRes.next()) {
                    Map<String, Object> mMap = new HashMap<>();
                    mMap.put("cq_code", mRes.getString(1).trim());
                    mMap.put("st_ename", mRes.getString(2).trim());
                    mMap.put("st_elname", mRes.getString(3).trim());
                    mMap.put("st_costc", mRes.getString(4).trim());
                    mMap.put("cq_date", mRes.getString(5).trim());
                    mMap.put("cq_intm", mRes.getString(6).trim());
                    mMap.put("cq_inloc", mRes.getString(7).trim());
                    mMap.put("ename", mRes.getString(8).trim());
                    mMap.put("cq_type", mRes.getString(9).trim());

                    mJSonArr.put(mMap);

                }

            } else {
                System.out.println("Server can't connect.");
            }

        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                conn.close();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return mJSonArr;

    }

    public static JSONArray check_auth(String st_code, String costc, String password) throws Exception {

        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectPG.ConnectionDBAS400();

        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
                String query = "SELECT CTL_CONO, CTL_CODE, CTL_GRP, CTL_SEQ, CTL_STS, CTL_UID\n"
                        + "FROM BRLDTA0100.APPCTL1\n"
                        + "WHERE CTL_CODE = 'SAL_CHECKIN'\n"
                        + "AND CTL_GRP = '" + costc.trim() + "'\n"
                        + "AND CTL_CONO = '10'\n"
                        + "AND CTL_UID = '" + st_code.trim() + "' \n"
                        + "AND CTL_REM = '" + password.trim() + "'";
                System.out.println(query);
                ResultSet mRes = stmt.executeQuery(query);

                while (mRes.next()) {
                    Map<String, Object> mMap = new HashMap<>();
                    mMap.put("CTL_CONO", mRes.getString(1).trim());
                    mMap.put("CTL_CODE", mRes.getString(2).trim());
                    mMap.put("CTL_GRP", mRes.getString(3).trim());
                    mMap.put("CTL_SEQ", mRes.getString(4).trim());
                    mMap.put("CTL_STS", mRes.getString(5).trim());
                    mMap.put("CTL_UID", mRes.getString(6).trim());

                    mJSonArr.put(mMap);
                }

            } else {
                System.out.println("Server can't connect.");
            }

        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                conn.close();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return mJSonArr;

    }

//    public static JSONArray check_auth(String st_code) throws Exception {
//
//        JSONArray mJSonArr = new JSONArray();
//        Connection conn = ConnectPG.ConnectionPG();
//
//        try {
//            if (conn != null) {
//
//                Statement stmt = conn.createStatement();
//                String query = "select st_cono, st_code, st_ename, st_elname, st_costc, st_costn, st_position, st_type, st_count, st_level \n"
//                        + "from employee_all \n"
//                        + "where st_code = '" + st_code.trim() + "'";
////                System.out.println(query);
//                ResultSet mRes = stmt.executeQuery(query);
//
//                while (mRes.next()) {
//                    Map<String, Object> mMap = new HashMap<>();
//                    mMap.put("st_cono", mRes.getString(1).trim());
//                    mMap.put("st_code", mRes.getString(2).trim());
//                    mMap.put("st_ename", mRes.getString(3).trim());
//                    mMap.put("st_elname", mRes.getString(4).trim());
//                    mMap.put("st_costc", mRes.getString(5).trim());
//                    mMap.put("st_costn", mRes.getString(6).trim());
//                    mMap.put("st_position", mRes.getString(7).trim());
//                    mMap.put("st_type", mRes.getString(8).trim());
//                    mMap.put("st_count", mRes.getString(9).trim());
//                    mMap.put("st_level", mRes.getString(10).trim());
//                    mJSonArr.put(mMap);
//
//                }
//
//            } else {
//                System.out.println("Server can't connect.");
//            }
//
//        } catch (SQLException sqle) {
//            throw sqle;
//        } catch (Exception e) {
//            e.printStackTrace();
//            if (conn != null) {
//                conn.close();
//            }
//            throw e;
//        } finally {
//            if (conn != null) {
//                conn.close();
//            }
//        }
//
//        return mJSonArr;
//
//    }
    public static JSONArray GridSearch(String fromdate, String todate, String Costc) throws Exception {

        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectPG.ConnectionPG();

        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
               
                String query = "SELECT cq_code, st_ename,st_elname,st_costc,cq_date, to_char(cq_intm, 'HH24:MI:SS') as cq_intm,cq_inloc,trim(st_ename) || ' ' || trim(st_elname) as ename"
                        + ",case when coalesce(cq_type,'') = 'OWK' then 'Out working' when coalesce(cq_type,'') = 'WFH' then 'Work from home'\n"
                        + "when coalesce(cq_type,'') = 'QRT' then 'Quarantine' when coalesce(cq_type,'') = 'VSC' then 'Visit customer'  else coalesce(cq_type,'') end as cq_type\n"
                        + "from check_qrtine,employee_all\n"
                        + "where cq_code = st_code\n"
                        + "and date(cq_date) between '" + fromdate.trim() + "' AND '" + todate.trim() + "'\n"
                        + "and st_costc like '" + Costc.trim() + "%'\n"
                        + "order by cq_intm,cq_code";
                System.out.println(query);
                ResultSet mRes = stmt.executeQuery(query);

                while (mRes.next()) {
                    Map<String, Object> mMap = new HashMap<>();
                    mMap.put("cq_code", mRes.getString(1).trim());
                    mMap.put("st_ename", mRes.getString(2).trim());
                    mMap.put("st_elname", mRes.getString(3).trim());
                    mMap.put("st_costc", mRes.getString(4).trim());
                    mMap.put("cq_date", mRes.getString(5).trim());
                    mMap.put("cq_intm", mRes.getString(6).trim());
                    mMap.put("cq_inloc", mRes.getString(7).trim());
                    mMap.put("ename", mRes.getString(8).trim());
                    mMap.put("cq_type", mRes.getString(9).trim());

                    mJSonArr.put(mMap);

                }

            } else {
                System.out.println("Server can't connect.");
            }

        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                conn.close();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return mJSonArr;

    }

    public static JSONArray Customer(String StaffCode) throws Exception {

        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectPG.ConnectionDB2();

        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
                String query = "SELECT ROW_NUMBER() OVER(ORDER BY OKCONO,OKCUNO) AS ID,COALESCE(JUUSID,'-') AS JUUSID,OKCONO,OKCUNO,OKCUNM\n"
                        + "    ,OKCUNO || ' : ' || OKCUNM AS CUSTOMER\n"
                        + "    FROM  M3FDBPRD.CMNUSR \n"
                        + "    LEFT JOIN M3FDBPRD.OCUSMA ON OKCONO = '10'  AND OKSTAT = '20' AND OKSMCD = JUUSID\n"
                        + "    WHERE JULSID = '" + StaffCode.trim() + "'\n"
                        + "    ORDER BY OKCUNO";
//                System.out.println(query);
                ResultSet mRes = stmt.executeQuery(query);

                while (mRes.next()) {
                    Map<String, Object> mMap = new HashMap<>();
                    mMap.put("ID", mRes.getString(1).trim());
                    mMap.put("JUUSID", mRes.getString(2).trim());
                    mMap.put("OKCONO", mRes.getString(3).trim());
                    mMap.put("OKCUNO", mRes.getString(4).trim());
                    mMap.put("OKCUNM", mRes.getString(5).trim());
                    mMap.put("CUSTOMER", mRes.getString(6).trim());

                    mJSonArr.put(mMap);

                }

            } else {
                System.out.println("Server can't connect.");
            }

        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                conn.close();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return mJSonArr;

    }

}
