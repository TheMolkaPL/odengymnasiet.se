<#assign toTime = DateTimeUtils.numberToTime>

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
                <#if pages?size gt 1>
                    <nav class="nav nav-tabs nav-justified" style="margin-bottom: 24px;">
                        <#list pages as page>
                            <a class="nav-item nav-link<#if page.active> active</#if>" href="/${page.target}">${page.article.title}</a>
                        </#list>
                    </nav>
                </#if>

                <article class="text-justify" id="article-content">
                    ${article.text}
                </article>
            </div>

            <div class="col-md-4">
                <aside>
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

                    <#if openHouses?size gt 0>
                        <div class="card bg-light border-success" style="margin-top: 32px;">
                            <div class="card-header">Kommande öppet hus</div>

                            <div class="list-group list-group-flush">
                                <#list openHouses as openHouse>
                                    <#assign startTime = openHouse.startTime>
                                    <#if openHouse.notEnding>
                                        <#assign time = "börjar " + toTime(startTime.hour) + ":" + toTime(startTime.minute)>
                                    <#else>
                                        <#assign endTime = openHouse.endTime>
                                        <#assign time = toTime(startTime.hour) + ":" + toTime(startTime.minute) + " - " + toTime(endTime.hour) + ":" + toTime(endTime.minute)>
                                    </#if>

                                    <a class="list-group-item list-group-item-action" href="/open-house/${openHouse.id}">
                                        <span class="text-dark">${startTime.dayOfMonth}/${startTime.monthValue} ${startTime.year?c}</span>
                                        <span class="float-right text-muted">${time}</span>
                                    </a>
                                </#list>
                            </div>
                        </div>
                    </#if>
                </aside>
            </div>
        </div>
    </article>
</div>
