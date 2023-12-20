/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.api.data;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author Wattana
 */
public class Utility {

   
    public static String ListStaff() throws JSONException {

        JSONObject mJsonObj = new JSONObject();
        JSONArray mJsonArr = new JSONArray();

        try {
            mJsonArr = SelectDB2.ListStaff();
            //System.out.println(mJsonArr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (mJsonArr.length() > 0) {
            mJsonObj.put("data", mJsonArr);
            return mJsonObj.toString();
        } else {
            mJsonObj.put("status", "404");
            mJsonObj.put("message", "Data not found.");
            return mJsonObj.toString();
        }

    }
    
    
    public static String Customer(String staffcode) throws JSONException {

        JSONObject mJsonObj = new JSONObject();
        JSONArray mJsonArr = new JSONArray();

        try {
            mJsonArr = SelectDB2.Customer(staffcode);
            //System.out.println(mJsonArr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (mJsonArr.length() > 0) {
            mJsonObj.put("data", mJsonArr);
            return mJsonObj.toString();
        } else {
            mJsonObj.put("status", "404");
            mJsonObj.put("message", "Data not found.");
            return mJsonObj.toString();
        }

    }
    
     public static String ListStaffC999() throws JSONException {

        JSONObject mJsonObj = new JSONObject();
        JSONArray mJsonArr = new JSONArray();

        try {
            mJsonArr = SelectDB2.ListStaffC999();
            //System.out.println(mJsonArr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (mJsonArr.length() > 0) {
            mJsonObj.put("data", mJsonArr);
            return mJsonObj.toString();
        } else {
            mJsonObj.put("status", "404");
            mJsonObj.put("message", "Data not found.");
            return mJsonObj.toString();
        }

    }
    
    public static String ListCostCenter() throws JSONException {

        JSONObject mJsonObj = new JSONObject();
        JSONArray mJsonArr = new JSONArray();

        try {
            mJsonArr = SelectDB2.ListCostCenter();
            //System.out.println(mJsonArr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (mJsonArr.length() > 0) {
            mJsonObj.put("data", mJsonArr);
            return mJsonObj.toString();
        } else {
            mJsonObj.put("status", "404");
            mJsonObj.put("message", "Data not found.");
            return mJsonObj.toString();
        }

    }
}
