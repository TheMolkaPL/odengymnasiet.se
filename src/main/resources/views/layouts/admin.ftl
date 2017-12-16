<!DOCTYPE html>
<html lang="sv" dir="ltr">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">

        <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css" rel="stylesheet">
        <link href="//fonts.googleapis.com/css?family=Nunito:400|Roboto:300" rel="stylesheet">
        <link href="/stylesheets/application.css" rel="stylesheet">

        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js"></script>
        <script src="/javascripts/application.js"></script>

        <title>
            <#if title??>
                ${title} - Odengymnasiet Admin
            <#else>
                Odengymnasiet Admin
            </#if>
        </title>
    </head>

    <body>
        <header>
            <nav class="navbar navbar-expand-lg navbar-light bg-ligt">
                <div class="container">
                    <a class="navbar-brand" href="/admin/dashboard">
                        <object type="image/svg+xml" data="/logos/svg/color/odengymnasiet-icon-color.svg" width="30" height="30" class="d-inline-block align-top"></object>
                        <span style="color: #254A9F;">Odengymnasiet</span>
                        <span class="text-danger">administration</span>
                    </a>

                    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>

                    <div class="collapse navbar-collapse" id="navbarSupportedContent">
                        <ul class="navbar-nav mr-auto">
                            <li class="nav-item">
                                <a class="nav-link" href="/logout">Logga ut</a>
                            </li>
                        </ul>

                        <div class="pull-right">
                            <a class="btn btn-outline-success" href="/" target="_blank" rel="noopener">Visa webbsidan</a>
                        </div>
                    </div>
                </div>
            </nav>
        </header>

        <main>
            ${body}
        </main>

        <footer style="margin-top: 36px;">
            <div class="container">
                <hr>
                <ul class="nav float-left" style="margin-bottom: 16px;">
                    <li class="nav-item">
                        <a class="nav-link" href="http://themolka.pl" target="_blank" rel="noopener">Copyright &copy; 2017 Aleksander Jagiełło</a>
                    </li>
                </ul>
                <ul class="nav float-right">
                    <li class="nav-item">
                        <a class="nav-link" href="https://aprendereskolor.se" target="_blank" rel="noopener">Aprendere Skolor</a>
                    </li>
                </ul>
            </div>
        </footer>
    </body>
</html>
