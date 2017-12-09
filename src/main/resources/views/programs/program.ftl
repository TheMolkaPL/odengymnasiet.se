<div class="jumbotron jumbotron-fluid">
    <div class="container">
        <header>
            <h2>${program.title}</h2>
        </header>
    </div>
</div>

<div class="container">
    <article>
        <#if program.marketing??>
            <div class="jumbotron" style="background-image: url('${program.marketing}');
                                          background-repeat: no-repeat;
                                          background-size: cover;
                                          background-position: 50% 50%;
                                          height: 300px;">
            </div>
        </#if>

        <div class="row">
            <div class="col-lg-8">
                <nav class="nav nav-tabs nav-justified" style="margin-bottom: 24px;">
                    <a class="nav-item nav-link<#if cur_page == "index"> active</#if>" href="/programs/${program.path}">Om utbildningen</a>
                    <a class="nav-item nav-link<#if cur_page == "elever-berattar"> active</#if>" href="/programs/${program.path}/elever-berattar">Elever berättar</a>
                    <a class="nav-item nav-link<#if cur_page == "traffa-lararna"> active</#if>" href="/programs/${program.path}/traffa-lararna">Träffa lärarna</a>
                    <a class="nav-item nav-link<#if cur_page == "efter-gymnasiet"> active</#if>" href="/programs/${program.path}/efter-gymnasiet">Efter gymnasiet</a>
                </nav>

                <article class="text-justify">
                    ${article.text}
                </article>
            </div>

            <div class="col-md-4">
                <#if program.open>
                    <a class="btn btn-success btn-lg btn-block" href="/programs/${program.path}/application">Anmälan</a>
                <#else>
                    <a class="btn btn-outline-danger btn-lg btn-block disabled" href="/programs/${program.path}">Anmälan är stängd</a>
                </#if>

                <#if program.files?size gt 0>
                    <div class="card bg-light" style="margin-top: 32px;">
                        <div class="card-header">Dokument</div>

                        <div class="list-group list-group-flush">
                            <#list program.files as file>
                                <div class="list-group-item">
                                    <small class="text-dark">${file.format?upper_case}</small>
                                    <a href="${file.url}" target="_blank" rel="noopener">${file.name}</a>
                                    <small class="text-muted">${file.size}MB</small>
                                </div>
                            </#list>
                        </div>
                    </div>
                </#if>
            </div>
        </div>
    </article>
</div>
