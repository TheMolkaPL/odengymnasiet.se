<#assign startTime = openHouse.startTime>
<#if !openHouse.notEnding>
    <#assign endTime = openHouse.endTime>
</#if>

<style>
.open-house-card-link:hover {
    text-decoration: none;
}
</style>

<article class="card border-primary" style="margin-top: 15px; margin-bottom: 15px;">
    <a class="card-header open-house-card-link" href="/open-house/${openHouse.id}">
        <h3 class="card-title" style="margin-bottom: 0px;">${startTime.dayOfMonth}/${startTime.monthValue} ${startTime.year?c}</h3>
    </a>
    <div class="card-body">
        <#if openHouse.notEnding>
            <h5>börjar <time>${startTime.hour}:${startTime.minute}</time></h5>
        <#else>
            <h5><time>${startTime.hour}:${startTime.minute}</time> - ${endTime.hour}:${endTime.minute}</h5>
        </#if>
    </div>
</article>
