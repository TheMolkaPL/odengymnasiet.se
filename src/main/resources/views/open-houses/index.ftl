<div class="text-center">
    <header class="jumbotron jumbotron-fluid">
        <h2 class="display-4">Välkommen till våra öppet hus på</h2>
        <h1 class="display-1" style="text-shadow: 0 1px 10px rgba(0,0,0,.5), 0 0 30px rgba(0,0,0,.1);">Odengymnasiet</h1>
    </header>

    <div class="container" style="margin-bottom: 32px;">
        <div class="row justify-content-center">
            <#list openHouses as openHouse>
                <div class="col-lg-4">
                    <#include "open_house_card.ftl">
                </div>
            </#list>
        </div>
    </div>
</div>
