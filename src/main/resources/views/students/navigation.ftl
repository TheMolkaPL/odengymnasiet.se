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
        <a class="list-group-item list-group-item-action" href="https://facebook.com/odengymnaiset">Facebook</a>
        <a class="list-group-item list-group-item-action" href="https://sms.schoolsoft.se/aprendere">SchoolSoft</a>
        <a class="list-group-item list-group-item-action" href="https://mail.aprendere.se">E-post</a>
    </div>
</section>
