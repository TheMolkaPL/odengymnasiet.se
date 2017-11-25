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

    <div class="list-group">
        <a class="list-group-item list-group-item-action" href="//mail.aprendere.se">E-post</a>
        <a class="list-group-item list-group-item-action" href="//facebook.com/odengymnasiet">Facebook</a>
        <a class="list-group-item list-group-item-action" href="//sms.schoolsoft.se/aprendere">SchoolSoft</a>
    </div>
</section>
