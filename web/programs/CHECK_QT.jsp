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
    <style>
        .ui-widget *, .ui-widget input, .ui-widget select, .ui-widget button {
            font-family: 'Helvetica Neue Light', 'Open Sans', Helvetica;
            font-size: 14px;
            font-weight: 300 !important;
        }
        .modal {
            display: none; /* Hidden by default */
            position: fixed; /* Stay in place */
            z-index: 1; /* Sit on top */
            padding-top: 100px; /* Location of the box */
            left: 0;
            top: 0;
            width: 100%; /* Full width */
            height: 100%; /* Full height */
            overflow: auto; /* Enable scroll if needed */
            background-color: rgb(0,0,0); /* Fallback color */
            background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
        }

        /* Modal Content */
        .modal-content {
            background-color: #fefefe;
            margin: auto;
            padding: 20px;
            border: 1px solid #888;
            width: auto;
        }

        /* The Close Button */
        .close {
            color: #000;
            float: right;
            font-size: 28px;
            font-weight: bold;
        }

        .close:hover,
        .close:focus {
            color: #000;
            text-decoration: none;
            cursor: pointer;
        }

    </style>
    <body style="background-color: white">
        <div class="collapse navbar-collapse" id="navcol-1">
            <ul class="nav navbar-nav navbar-right">
                <li role="presentation"></li>
                <li class="dropdown"><button id="btn_group" class="btn-md btn-danger form-control">Report</button></li>
            </ul>
        </div>
        <div class="container-fluid col-lg-12 col-md-12 col-sm-12">
            <div class="row col-lg-12 col-md-12">
                <label style="font-weight: bold;color: red">Employee ID :</label>
                <input type="text" id="st_code" name="st_code" value="">
            </div>
            <div class="row col-lg-12 col-md-12">
                <label style="font-weight: bold;color: red">Secure Code :</label>
                <input type="password" id="st_pass" name="st_pass" value="">
            </div>
            <div class="row col-lg-12 col-md-12">
                <div class="col-lg-3 col-md-3 col-sm-7">
                    <label style="font-weight: bold;color: red">From Date :</label>
                    <input type="date" id="fromdate" name="fromdate">
                </div>
            </div>
            <div class="row col-lg-12 col-md-12">
                <div class="col-lg-3 col-md-4 col-sm-6">
                    <label style="font-weight: bold;color: red">To Date :</label>
                    <input type="date" id="todate" name="todate"> 
                </div>
            </div>
            <div class="row col-lg-4 col-md-4 col-sm-12">
                <label style="font-weight: bold;color: red">Cost Center :</label>
                <select name="costc" id="costc" style="width: 250px">
                    <option value="" selected="selected">Select Cost Center</option>
                </select>                    
                <button class="btn-success btn-sm" onclick="check_auth()" type="button">Search</button>
            </div>
            <div id="jsGrid"></div>
            <form  method="GET"  id="MyForm" action="./Report" >
                <div id="HeadGroup" class="modal container">
                    <div class="modal-content">
                        <div class="panel panel-default container">
                            <div class="panel-heading" style="background-color: #85cdf4">
                                <span class="close">&times;</span>
                                <h3 class="panel-title" > <font color="#FFFFFF">Report Route Check in : Out Working</font></h3>
                            </div>
                            <div class="container panel-body col-lg-12 col-md-12 col-sm-12" >
                                <div class="row col-lg-12 col-md-12 col-sm-12">
                                    <div class="col-md-4">
                                        <lable>From date :</lable>
                                        <input type="date" id="fromdate_ex" name="fromdate_ex" class="form-control">
                                    </div>
                                    <div class="col-md-4">
                                        <lable>To date :</lable>
                                        <input type="date" id="todate_ex" name="todate_ex" class="form-control">
                                    </div>
                                    <div class="col-md-2" style="display: flex;justify-content: center;align-items: center; padding-top: 23px">
                                        <button class="btn-info btn-sm" id="vReport" name="vReport" type="submit" value="RP04_EXCEL">GET EXCEL</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </div>        
    </body>
    <script>
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

            var modal = document.getElementById("HeadGroup");
            var btn = document.getElementsByClassName("btn-md")[0];
            var span = document.getElementsByClassName("close")[0];

            btn.onclick = function () {
                modal.style.display = "block";
            };
            span.onclick = function () {
                modal.style.display = "none";
            };

            window.onclick = function (event) {
                if (event.target === modal) {
                    modal.style.display = "none";
                }
            };

        });


        function check_auth() {
            var auth = "";
            $.ajax({
                url: './Sync',
                type: 'GET',
                dataType: 'json',
                data: {
                    page: "check_auth",
                    st_code: $("#st_code").val(),
                    st_costc: $("#costc").val(),
                    st_pass: $("#st_pass").val()
                },
                async: false
            }).done(function (response) {
                $.each(response, function (i, obj) {
                    auth = obj.CTL_UID;
                });
            });

            if (auth !== "") {
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
                pageButtonCount: 5, pagerContainer: null,
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
                    {title: "CostC.", name: "st_costc", type: "text", align: "center", width: 35},
                    {title: "Date", name: "cq_date", type: "text", align: "center"},
                    {title: "Time", name: "cq_intm", type: "text", align: "center"},
                    {title: "Location", name: "cq_inloc", type: "text", align: "left", width: 175}
                ],

                rowClick: function (args) {
                    //            console.log(args.item);
                    showDetailsDialog1("GET", args.item);
                }
            });
        }




        var showDetailsDialog1 = function (dialogType, client) {

            window.open('https://www.google.com/maps/search/' + client.cq_inloc);

        };
    </script>
</html>
