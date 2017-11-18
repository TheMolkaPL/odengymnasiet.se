<div class="jumbotron jumbotron-fluid">
    <div class="container">
        <header>
            <h2>Våra utbildningar</h2>
        </header>
    </div>
</div>

<article>
    <div class="container">
        <div class="row">
            <#list programs as program>
                <div class="col-md-6" style="padding-top: 15px; padding-bottom: 15px;">
                    <article class="card">
                        <div class="card-body">
                            <a href="/programs/${program.path}" title="Mer om ${program.title?lower_case}">
                                <h4 class="card-title" <#if !program.subtitle??>style="margin-bottom: 0px;"</#if>>${program.title}</h4>
                            </a>

                            <#if program.subtitle??>
                                <h6 class="card-subtitle text-muted" title="${program.title} är ett högskoleförberedande program som förbereder dig för fortsatta studier på högskola och universitet.">${program.subtitle?lower_case}</h6>
                            </#if>
                        </div>

                        <a href="/programs/${program.path}" title="Mer om ${program.title?lower_case}">
                            <img class="card-img-bottom" src="${program.icon}" alt="${program.title}">
                        </a>
                    </article>
                </div>
            </#list>
        </div>
    </div>
</article>
