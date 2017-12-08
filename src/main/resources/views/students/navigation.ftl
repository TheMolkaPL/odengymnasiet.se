<section>
    <div class="list-group" style="margin-bottom: 20px;">
        <a class="list-group-item list-group-item-action
            <#if navigation_now == "index">active</#if>" href="/students">För elever</a>
        <a class="list-group-item list-group-item-action
            <#if navigation_now == "absence">active</#if>" href="/students/absence">Frånvaroanmälan</a>
        <a class="list-group-item list-group-item-action
            <#if navigation_now == "health">active</#if>" href="/students/health">Elevhälsa</a>
        <a class="list-group-item list-group-item-action
            <#if navigation_now == "feedback">active</#if>" href="/students/feedback">Synpunkter och klagomål</a>
    </div>

    <#if student_services?size gt 0>
        <div class="list-group">
            <#list student_services as service>
                <a class="list-group-item list-group-item-action" href="${service.url}" target="_blank" rel="noopener">${service.name}</a>
            </#list>
        </div>
    </#if>
</section>
