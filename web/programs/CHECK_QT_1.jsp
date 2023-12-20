<%-- 
    Document   : index
    Created on : Jun 13, 2019, 4:29:51 PM
    Author     : Wattana
--%>

<%@page import="com.br.api.data.Utility"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1"> 
    </head>
    <body style="background-color: white">
        <div class="container-fluid col-lg-12 col-md-12 col-sm-12">
            <br><br><br><br>    
            <div class="row col-lg-12 col-md-12">
                <label style="font-weight: bold;color: red">Employee ID :</label>
                <input type="text" id="st_code" name="st_code" value="">
            </div>
            <div class="row col-lg-12 col-md-12">
                <div class="col-lg-3 col-md-3 col-sm-7">
                    <label style="font-weight: bold;color: red">From Date :</label>
                    <input type="date" id="fromdate" name="fromdate">
                </div>

            </div>
            <div class="row col-lg-12 col-md-12">
                <div class="col-lg-3 col-md-3 col-sm-6">
                    <label style="font-weight: bold;color: red">To Date :</label>
                    <input type="date" id="todate" name="todate"> 
                </div>
            </div>
            <div class="row col-lg-4 col-md-4 col-sm-12">
                <label style="font-weight: bold;color: red">Cost Center :</label>
                <select name="costc" id="costc" style="width: 250px">
                    <option value="" selected="selected">Select Cost Center</option>
                </select>
                <button class="btn-success btn-sm" onclick="check_auth()">Search</button>
            </div>
            <div id="jsGrid"></div>
        </div>
    </body>
</html>

<script>

    function check_auth() {
        var auth = "";
        $.ajax({
            url: './Sync',
            type: 'GET',
            dataType: 'json',
            data: {
                page: "check_auth",
                st_code: $("#st_code").val()
            },
            async: false
        }).done(function (response) {
            $.each(response, function (i, obj) {
                auth = obj.st_level;
            });
        });

        if (parseFloat(auth) >= 32) {
            gridsearch();
            $("#jsGrid").show();
        } else {
            $("#jsGrid").hide();
            alert("Not authorization");
        }
    }

    function gridsearch() {
        $("#jsGrid").jsGrid({
            width: "100%",
            height: "auto",
            sorting: true,
            paging: true,
            autoload: true,
            selecting: true,
            pageSize: 14,
            pageButtonCount: 5,pagerContainer: null,
            controller: {
                loadData: function (filter) {
                    return   $.ajax({
                        type: "GET",
                        url: "./Sync",
                        dataType: 'json',
                        data: {
                            page: "Grid",
                            fromdate: $("#fromdate").val(),
                            todate: $("#todate").val(),
                            costc: $("#costc").val()

                        }
                    }).done(function (response) {
                    });
                    return data.promise();
                }
            },
            fields: [
                {title: "Employee ID", name: "cq_code", type: "text", align: "center"},
                {title: "Name", name: "ename", type: "text", align: "left"},
                {title: "Type", name: "cq_type", type: "text", align: "center"},
                {title: "CostC.", name: "st_costc", type: "text", align: "center",width: 35},
                {title: "Date", name: "cq_date", type: "text", align: "center"},
                {title: "Time", name: "cq_intm", type: "text", align: "center"},
                {title: "Location", name: "cq_inloc", type: "text", align: "left",width: 175}
            ],
            
            
            rowClick: function (args) {
//            console.log(args.item);
                showDetailsDialog1("GET", args.item);
            }
        });
    }



    $(document).ready(function () {
        var today = new Date();
        var dd = today.getDate();
        var mm = today.getMonth() + 1; //January is 0!
        var yyyy = today.getFullYear();
        if (dd < 10) {
            dd = '0' + dd;
        }
        if (mm < 10) {
            mm = '0' + mm;
        }
        today = yyyy + '-' + mm + '-' + dd;
        $("#costc").select2();
        $.ajax({
            url: "./Sync",
            type: "GET",
            dataType: "json",
            data: {
                page: "ListCostCenter"
            },
            success: function (getdata) {
                $.each(getdata.data, function (i, obj) {
                    var div_data = "<option value=" + obj.st_costc + ">" + obj.st_costc + " : " + obj.st_costn + "</option>";
                    //console.log(div_data)
                    $(div_data).appendTo('#costc');
                });
            }
        });
        document.getElementById("fromdate").value = today;
        document.getElementById("todate").value = today;

        $("#jsGrid").jsGrid({
            width: "100%",
            height: "550",
            sorting: true,
            paging: true,
            autoload: true,
            selecting: true,
            pageSize: 14,
            pageButtonCount: 5,
            controller: {
                loadData: function (filter) {
                    return   $.ajax({
                        type: "GET",
                        url: "./Sync",
                        dataType: 'json',
                        data: {
                            page: "Grid",
                            date: today,
                            costc: ""

                        }
                    }).done(function (response) {
                    });
                    return data.promise();
                }
            },
            fields: [
                {title: "Employee ID", name: "cq_code", type: "text", align: "center"},
                {title: "Name", name: "ename", type: "text", align: "left"},
                {title: "Type", name: "cq_type", type: "text", align: "center"},
                {title: "CostC.", name: "st_costc", type: "text", align: "center", width: 45},
                {title: "Date", name: "cq_date", type: "text", align: "center", width: 45},
                {title: "Time", name: "cq_intm", type: "text", align: "center", width: 45},
                {title: "Location", name: "cq_inloc", type: "text", align: "left", width: 150}
            ],
            rowClick: function (args) {
//            console.log(args.item);
                showDetailsDialog1("GET", args.item);
            }
        });
    });

    var showDetailsDialog1 = function (dialogType, client) {

        window.open('https://www.google.com/maps/search/' + client.cq_inloc);

    };
</script>
