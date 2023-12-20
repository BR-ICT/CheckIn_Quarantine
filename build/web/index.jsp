<%-- 
    Document   : index
    Created on : Jun 14, 2019, 9:32:52 AM
    Author     : Wattana
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

   

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" type="text/css" href="assets/Bootstrap-3.3.7/css/bootstrap.min.css"/>
        <link rel="stylesheet" type="text/css" href="fonts/font-awesome-4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" type="text/css" href="fonts/iconic/css/material-design-iconic-font.min.css">
        <link rel="stylesheet" type="text/css" href="assets/animate/animate.css">
        <link rel="stylesheet" type="text/css" href="assets/css-hamburgers/hamburgers.min.css">
        <link rel="stylesheet" type="text/css" href="assets/animsition/css/animsition.min.css">
        <link rel="stylesheet" type="text/css" href="assets/util.css">
        <link rel="stylesheet" type="text/css" href="assets/main.css">
        <script type="text/javascript" src="assets/jQuery-3.3.1/jquery-3.3.1.min.js"></script>
        <script type="text/javascript" src="assets/Bootstrap-3.3.7/js/bootstrap.min.js"></script>
        <script src="assets/animsition/js/animsition.min.js"></script>
        <script src="assets/Bootstrap-3.3.7/js/popper.js"></script>
        <script src="assets/Bootstrap-3.3.7/js/bootstrap.min.js"></script>

       <link rel="stylesheet" type="text/css" href="assets/jsgrid-1.5.3/dist/jsgrid.min.css" />
        <link rel="stylesheet" type="text/css" href="assets/jsgrid-1.5.3/dist/jsgrid-theme.min.css">
        <script type="text/javascript" src="assets/jsgrid-1.5.3/dist/jsgrid.min.js"></script>
        <script src="assets/jsgrid-1.5.3/src/jsgrid.core.js"></script>
        <script src="assets/jsgrid-1.5.3/src/jsgrid.validation.js"></script>
        <script src="assets/jsgrid-1.5.3/src/jsgrid.load-indicator.js"></script>
        <script src="assets/jsgrid-1.5.3/src/jsgrid.load-strategies.js"></script>
        <script src="assets/jsgrid-1.5.3/src/jsgrid.sort-strategies.js"></script>
        <script src="assets/jsgrid-1.5.3/src/jsgrid.field.js"></script>
        <script src="assets/jsgrid-1.5.3/src/fields/jsgrid.field.text.js"></script>
        <script src="assets/jsgrid-1.5.3/src/fields/jsgrid.field.number.js"></script>
        <script src="assets/jsgrid-1.5.3/src/fields/jsgrid.field.select.js"></script>
        <script src="assets/jsgrid-1.5.3/src/fields/jsgrid.field.checkbox.js"></script>
        <script src="assets/jsgrid-1.5.3/src/fields/jsgrid.field.control.js"></script>
        <script src="assets/countdowntime/countdowntime.js"></script>
        <script src="assets/main.js"></script>
        <title>Web App Check In Quarantine</title>
        <link rel="icon" type="image/ico" href="images/duck.jpg" />
        <script src='assets/select2/select2.min.js' type='text/javascript'></script>
        <link href='assets/select2/select2.min.css' rel='stylesheet' type='text/css'>
        
    </head>
    <body style="background-color: #cccccc">
        <section id="programs">
            <div class='centerDiv' id="vSection"></div>
        </section>
    </body>
</html>

<script type="text/javascript">
    <% if (request.getParameter("page") != null) {%>
    $("#vSection").load("./programs/<%=request.getParameter("page").toString()%>.jsp");
    <% } else {  %>
    $("#vSection").load("./programs/CheckIn_Quarantine.jsp");
    <% }%>



</script>