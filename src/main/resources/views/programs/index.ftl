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
            <#assign card_size_icon = false>
            <#list programs as program>
                <div class="col-md-6">
                    <#include "program_card.ftl">
                </div>
            </#list>
        </div>
    </div>
</article>
