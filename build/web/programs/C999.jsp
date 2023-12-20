<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
        <title>WeatherPy Viz</title>

        <!-- Bootstrap -->
        <link href="css/bootstrap.min.css" rel="stylesheet">

        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

        <!-- Optional theme -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">



        <script
            src="https://code.jquery.com/jquery-3.2.1.min.js"
            integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4="
        crossorigin="anonymous"></script>

        <!-- Latest compiled and minified JavaScript -->
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>

        <link rel="stylesheet" type="text/css" href="weather.css">

    </head>
    <body style="background-color: lightgray">
        <nav class="navbar navbar-default">
            <div class="container-fluid">
                <!-- Brand and toggle get grouped for better mobile display -->
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <!-- emply col to move over text -->
                    <div class="col-md-1"></div>
                    <div class="col-md-1"></div>
                    <div class="col-md-1"></div>
                    <a class="navbar-brand" href="index.html" style="color: white; background-color: navy;">
                        <span class="nav-color">Latitude</span>
                    </a>
                </div>
                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">

                    <ul class="nav navbar-nav navbar-right">
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Plots <span class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <li><a href="temperature.html">Temperature</a></li>
                                <li><a href="humidity.html">Humidity</a></li>
                                <li><a href="cloud_cover.html">Cloud Cover</a></li>
                                <li><a href="wind_speed.html">Wind Speed</a></li>
                            </ul>
                        </li>
                        <li><a href="comparisons.html">Comparisons</a></li>
                        <li><a href="data.html">Data</a></li>

                    </ul>
                </div><!-- /.navbar-collapse -->
            </div><!-- /.container-fluid -->
        </nav>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-1"></div>
                <div class="col-md-7">

                    <div class="panel panel-default">
                        <div class="panel-body">
                            <h2>Average Temperature</h2>
                            <hr>
                            <img class = "img-responsive" src="Images/output_35_2.png" style="width:100%; margin: 5px;" alt="first image" >
                            <p> This graph shows the relationship between temperature and latitude.  Unsurprisingly, we can observe that average forecasted temperature does increase as you approach the equator 
                                from the north and south.  The temperature seems to reach maximum temperature between -20 and 20 degrees latitude.  This is within the 
                                Tropics of Cancer and Capricorn.  

                            </p>
                        </div>
                    </div>

                </div>
                <div class="col-md-3">

                    <div class="panel panel-default">
                        <div class="panel-body">

                            <h2>Visualizations</h2>
                            <hr>
                            <div class="row" style = "padding-bottom: 10px">
                                <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6"><a href="temperature.html"><img class = "img img-responsive current-tag" src="Images/output_35_2.png" style="width:100%" alt=""></a></div>
                                <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6"><a href="humidity.html"><img class = "img img-responsive" src="Images/output_36_2.png" style="width:100%" alt=""></a></div>
                            </div>
                            <div class="row">
                                <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6"><a href="cloud_cover.html"><img class = "img img-responsive" src="Images/output_37_2.png" style="width:100%" alt=""></a></div>
                                <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6"><a href="wind_speed.html"><img class = "img img-responsive" src="Images/output_38_2.png" style="width:100%" alt=""></a></div>
                            </div>

                        </div>

                    </div>
                </div>
                <div class="col-md-1"></div>


            </div>
        </div>

        <footer>
            <p style = "text-align: center"><i class="glyphicon glyphicon-copyright-mark"> Copyright Coding Bootcamp 2017</i></p>

        </footer>








    </body>
    <script>
        $(document).ready(function () {
//            fetch('https://covid19.ddc.moph.go.th/api/Cases/today-cases-all')
            fetch('http://34.126.81.163:4000/readtemp/DEV001/1')
                    .then(result => result.json())
                    .then((output) => {
                        console.log('Output: ', output);

                    }).catch(err => console.error(err));
        });
    </script>
</html>