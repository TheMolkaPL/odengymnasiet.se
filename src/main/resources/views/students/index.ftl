<div class="jumbotron jumbotron-fluid">
    <div class="container">
        <header>
            <h2>För elever</h2>
        </header>
    </div>
</div>

<div class="container">
    <article>
        <div class="row">
            <div class="col-md-9">
                <article class="text-justify">
                    <header style="margin-bottom: 16px;">
                        <h3>${article.title}</h3>
                    </header>

                    ${article.text}
                </article>
            </div>

            <div class="col-md-3">
                <aside>
                    <#assign navigation_now = "index">
                    <#include "navigation.ftl">
                </aside>
            </div>
        </div>
    </article>
</div>
