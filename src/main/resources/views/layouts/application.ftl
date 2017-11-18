<!DOCTYPE html>
<html lang="sv" dir="ltr">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Nunito:400|Roboto:300" rel="stylesheet">
        <link href="/stylesheets/application.css" rel="stylesheet">
        <title>
            <#if title??>
                ${title} - Odengymnasiet
            <#else>
                Odengymnasiet
            </#if>
        </title>
    </head>
    <body>
        <header>
            <nav class="navbar navbar-expand-lg navbar-light bg-ligt">
                <div class="container">
                    <a class="navbar-brand" href="/">Odengymnasiet</a>
                    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>

                    <div class="collapse navbar-collapse" id="navbarSupportedContent">
                        <ul class="navbar-nav mr-auto">
                            <li class="nav-item">
                                <a class="nav-link <#if app_nav == "about">active</#if>" href="/about">Om skolan</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link <#if app_nav == "programs">active</#if>" href="/programs">Våra utbildningar</a>
                            </li>
                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle <#if app_nav == "students">active</#if>" href="/students" id="navbarStudentsDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">För elever</a>
                                <div class="dropdown-menu" aria-labelledby="navbarStudentsDropdown">
                                    <a class="dropdown-item" href="https://facebook.com/odengymnasiet" target="_blank" rel="noopener">Facebook</a>
                                    <a class="dropdown-item" href="https://sms.schoolsoft.se/aprendere" target="_blank" rel="noopener">SchoolSoft</a>
                                    <a class="dropdown-item" href="https://mail.aprendere.se" target="_blank" rel="noopener">E-post</a>
                                    <div class="dropdown-divider"></div>
                                    <a class="dropdown-item" href="/students">Mer...</a>
                                </div>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link <#if app_nav == "contact">active</#if>" href="/contact">Kontakt</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
        </header>

        <main>
            ${body}
        </main>

        <footer style="margin-top: 36px;">
            <div class="container" style="margin-bottom: 16px;">
                <hr>
                <ul class="nav float-left">
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

        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js"></script>
        <script src="/javascripts/application.js"></script>
    </body>
</html>
