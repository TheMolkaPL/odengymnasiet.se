<#if card_size_icon>
    <#assign sizeTag = "h5">
<#else>
    <#assign sizeTag = "h4">
</#if>

<article class="card" style="margin-top: 15px; margin-bottom: 15px;">
    <div class="card-body">
        <a href="/programs/${program.path}" title="Mer om ${program.title?lower_case}">
            <${sizeTag} class="card-title" <#if !program.subtitle??>style="margin-bottom: 0px;"</#if>>${program.title}</${sizeTag}>
        </a>

        <#if program.subtitle??>
            <h6 class="card-subtitle text-muted" title="${program.title} är ett högskoleförberedande program som förbereder dig för fortsatta studier på högskola och universitet.">${program.subtitle?lower_case}</h6>
        </#if>
    </div>

    <a href="/programs/${program.path}" title="Mer om ${program.title?lower_case}">
        <img class="card-img-bottom" src="${program.icon}" alt="${program.title}">
    </a>
</article>
