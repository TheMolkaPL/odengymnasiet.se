<section>
    <div class="list-group" style="margin-bottom: 20px;">
        <#list pages as page>
            <a class="list-group-item list-group-item-action<#if page.active> active</#if>" href="/${page.target}">${page.article.title}</a>
        </#list>
    </div>

    <#if studentServices?size gt 0>
        <div class="list-group">
            <#list studentServices as service>
                <a class="list-group-item list-group-item-action" href="${service.url}" target="_blank" rel="noopener">${service.name}</a>
            </#list>
        </div>
    </#if>
</section>
