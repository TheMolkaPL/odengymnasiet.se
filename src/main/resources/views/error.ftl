<!DOCTYPE html>
<html lang="sv" dir="ltr">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">

        <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css" rel="stylesheet">
        <link href="//fonts.googleapis.com/css?family=Nunito:400|Roboto:300" rel="stylesheet">
        <link href="/stylesheets/application.css" rel="stylesheet">

        <title>${error.title}</title>
    </head>

    <body>
        <header>
            <nav class="navbar navbar-expand-lg navbar-light bg-ligt">
                <div class="container">
                    <a class="navbar-brand" href="/" style="color: #254A9F;">
                        <object type="image/svg+xml" data="/logos/svg/color/odengymnasiet-icon-color.svg" width="30" height="30" class="d-inline-block align-top"></object>
                        Odengymnasiet
                    </a>
                </div>
            </nav>
        </header>

        <main>
            <div class="jumbotron jumbotron-fluid">
                <div class="container">
                    <h1 style="font-size: 125px;">${error.code}</h1>
                    <h3>${error.message}</h3>
                </div>
            </div>
        </main>

        <footer style="margin-top: 36px;">
            <div class="container">
                <hr>
                <ul class="nav float-left" style="margin-bottom: 16px;">
                    <li class="nav-item">
                        <a class="nav-link" href="http://themolka.pl" target="_blank" rel="noopener">Copyright &copy; 2017 Aleksander Jagiełło</a>
                    </li>
                </ul>
            </div>
        </footer>
    </body>
</html>
