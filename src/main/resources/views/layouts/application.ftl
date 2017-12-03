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
                    <a class="navbar-brand" href="/" style="color: #254A9F;">
                        <object type="image/svg+xml" data="/odengymnasiet-logo-classic.svg" width="30" height="30" class="d-inline-block align-top"></object>
                        Odengymnasiet
                    </a>

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
                                    <a class="dropdown-item" href="//mail.aprendere.se" target="_blank" rel="noopener">E-post</a>
                                    <a class="dropdown-item" href="//facebook.com/odengymnasiet" target="_blank" rel="noopener">Facebook</a>
                                    <a class="dropdown-item" href="//sms.schoolsoft.se/aprendere" target="_blank" rel="noopener">SchoolSoft</a>
                                    <div class="dropdown-divider"></div>
                                    <a class="dropdown-item" href="/students">Mer...</a>
                                </div>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link <#if app_nav == "contact">active</#if>" href="/contact">Kontakt</a>
                            </li>
                        </ul>

                        <#if admin>
                            <div class="pull-right">
                                <a class="btn btn-outline-danger" href="/admin/dashboard" target="_blank" rel="noopener">Hantera webbsidan</a>
                            </div>
                        </#if>
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
