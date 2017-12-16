<section>
    <div class="list-group">
        <#list topics as item>
            <a class="list-group-item list-group-item-action<#if item.active> active</#if>" href="/admin/topics/${item.topic.path}">${item.topic.name}</a>
        </#list>
    </div>
</section>
