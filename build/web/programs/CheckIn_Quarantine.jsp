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

    <body>
        <div class="container-login100">
            <div class="wrap-login100 p-l-55 p-r-55 p-t-30 p-b-30">
                <span class="login100-form-title p-b-37" style="font-size: 20px;font-weight: bold">
                    Check In
                </span>
                <div class="m-b-20 p-t-30">
                    <img src="images/map.png" class="center-block" width="100" height="100"/>
                </div>
                <form  method="POST" enctype="multipart/form-data" acceptcharset="UTF-8" id="myForm">
                    <div class="wrap-input100 validate-input m-b-25">
                        <select class="form-control form-control-user" name="StaffCode" id="StaffCode" onchange="Remark(this.value)">
                            <option value="" selected="selected">Select Staff Code</option>
                        </select>
                    </div>

                    <div class="wrap-input100 validate-input m-b-25">
                        <select class="form-control form-control-user" name="Type" id="Type" onchange="Remark(this.value)">
                            <option value="" selected="selected">Select type</option>
                            <option value="QRT">Quarantine</option>
                            <option value="WFH">Work from home</option>
                            <option value="OWK">Out working</option>
                            <option value="VSC">Visit customer</option>
                        </select>
                    </div>
                    <div class="wrap-input100 validate-input m-b-25">
                        <select class="form-control form-control-user" name="vCustomer" id="vCustomer" >
                            <option value="" selected="selected">Select Customer</option>
                        </select>
                    </div>
                    <div class="wrap-input100 validate-input m-b-25">
                        <textarea class="form-control form-control-user" placeholder="Remark or any information" id="remark" name="remark"></textarea>
                    </div>
                    <div class="container-login100-form-btn">
                        <button id="btn" class="bg-info btn-sm"  onclick="getLocation()" type="button">
                            CHECK IN/OUT
                        </button>
                    </div>
                    <br>

                    <p id="demo" style="color: red"></p>
                    <input name="LOCATION" id="LOCATION" value="" class="input100">
                    <br><br>
                    <div class="container-login100-form-btn">
                        <button class="login100-form-btn" type="button" name="button" id="button">Submit</button>
                    </div>
                    <div hidden="true">
                        <button class="login100-form-btn" type="submit" name="Checkin" id="Checkin" value="Checkin" >Submit</button>
                    </div>
                    <br>
                    <br>
                    <div class="wrap-input100 m-b-5" id="Report">
                        <!--<a href="?page=./CHECK_QT">REPORT</a>-->
                        <a onclick="report()">REPORT</a>
                    </div>
                </form>
                <br>
            </div>
        </div>
    </body>
</html>

<script>
    $("#StaffCode").select2();
    $("#LOCATION").hide();
    $("#remark").hide();
    $("#vCustomer").select2();



    function report() {
        window.open('/CheckIn_Quarantine/?page=CHECK_QT');
    }
    $(document).ready(function () {
        $('#vCustomer').next(".select2-container").hide();
        $.ajax({
            url: "./Sync",
            type: "GET",
            dataType: "json",
            data: {
                page: "ListStaff"
            },
            success: function (getdata) {
                $.each(getdata.data, function (i, obj) {
                    var div_data = "<option value=" + obj.st_code + ">" + obj.st_code + " : " + obj.st_ename + " " + obj.st_elname + "</option>";
//                    console.log(div_data);
                    $(div_data).appendTo('#StaffCode');
                });
            }
        });
    });

    function getLocation() {
        if (navigator.geolocation) {
            console.log("PASS");
            navigator.geolocation.getCurrentPosition(showPosition);
//            showPosition("few");
        } else {
            console.log("NOT");
            alert("Geolocation is not supported by this browser.");
        }

    }

    function showPosition(position) {
        var x = document.getElementById("demo");

        x.innerHTML = "Latitude:  " + position.coords.latitude +
                "<br>Longitude:  " + position.coords.longitude;

        document.getElementById("LOCATION").value = position.coords.latitude +
                "," + position.coords.longitude;

    }

    $("#button").click(function (e) {
        var form = $('#myForm')[0];
        // Create an FormData object 
        var data = new FormData(form);
        var StaffCode = $("#StaffCode").val();

        var locations = $("#LOCATION").val();
        var Type = $("#Type").val();
//
        if (StaffCode === "" || Type === "") {
            alert("กรุณากรอกข้อมูลให้ครบถ้วน");
        } else if (locations === "") {
            alert("กรุณากดปุ่มเช็คอิน");
        } else if (Type === "VSC" && $("#vCustomer").val() === "") {
            alert("กรุณาเลือกลูกค้า");
        } else {
            $.ajax({
                type: "POST",
                enctype: 'multipart/form-data',
                url: "./CheckIn",
                data: data,
                processData: false,
                contentType: false,
                cache: false,
//                timeout: 600000,
                async: false,
                success: function (data) {
                    alert("สำเร็จ");
                    location.reload(0);
                },
                error: function (e) {
                    alert("ล้มเหลว");
                }
            });

        }
    });


    function Customer() {
        var SALE_CODE = "<%out.print(session.getAttribute("SALE_CODE"));%>";
        $('#vCustomer').find('option').remove().end();
        $('#vCustomer').append('<option value="" selected="selected">Select Customer</option>');


        $.ajax({
            url: "./Sync",
            type: "GET",
            dataType: "json",
            data: {
                page: "Customer",
                StaffCode: $("#StaffCode").val()
            },
            success: function (getdata) {

                $.each(getdata.data, function (i, obj) {
                    var div_data = "<option value=" + obj.OKCUNO + ">" + obj.CUSTOMER + "</option>";
                    //console.log(div_data)
                    $(div_data).appendTo('#vCustomer');
                });

            }, async: false
        });
        $('#vCustomer').append('<option value="Other">Other : Other</option>');

    }

    function Remark(val) {
        $("#remark").val("");
        val = $("#Type").val();
        if (val === "VSC") {
            $("#remark").show();
            $("#vCustomer").show();
            $('#vCustomer').next(".select2-container").show();

            Customer();
        } else {
            $("#remark").hide();
            $("#vCustomer").hide();
            $('#vCustomer').next(".select2-container").hide();

        }
    }


</script>
