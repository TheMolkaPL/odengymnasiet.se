<#assign archived = openHouse.archived>
<div class="text-center">
    <header class="jumbotron jumbotron-fluid">
        <h2 class="display-4">Välkommen till öppet hus på</h2>
        <h1 class="display-1" style="text-shadow: 0 1px 10px rgba(0,0,0,.5), 0 0 30px rgba(0,0,0,.1);">Odengymnasiet</h1>
    </header>

    <div class="container" style="margin-bottom: 32px;">
        <article>
            <header style="margin-bottom: 32px;">
                <h5>Du är varmt välkommen till vårt öppet hus på Odengymnasiet!</h5>
            </header>

            ${openHouse.description}
        </article>
    </div>

    <section class="jumbotron" style="margin-bottom: 0px; padding-top: 32px;">
        <header style="margin-bottom: 32px;">
            <h3>Tid och plats</h3>
        </header>

        <div class="row justify-content-center">
            <div class="col-md-4">
                <div class="card border-light" style="height: 128px;">
                    <div class="card-body">
                        <#assign startTime = openHouse.startTime>
                        <h4>${startTime.dayOfMonth}/${startTime.monthValue} ${startTime.year?c}</h4>

                        <#if openHouse.notEnding>
                            <h5>börjar <time>${startTime.hour}:${startTime.minute}</time></h5>
                        <#else>
                            <#assign endTime = openHouse.endTime>
                            <h5><time>${startTime.hour}:${startTime.minute}</time> - ${endTime.hour}:${endTime.minute}</h5>
                        </#if>
                    </div>
                </div>
            </div>

            <div class="col-md-4">
                <div class="card border-light" style="height: 128px;">
                    <div class="card-body">
                        <address>
                            <strong>Odenplansgymnasiets Skol If</strong><br>
                            Instrumentvägen 31<br>
                            126 53 Hägersten
                        </address>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <section>
        <iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d2036.5901936816147!2d17.98010021593009!3d59.30640121990299!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x465f764f7366ab45%3A0x79377432597bb637!2sInstrumentv%C3%A4gen+31%2C+126+53+H%C3%A4gersten!5e0!3m2!1spl!2sse!4v1510519754597"
                width="1100" height="300" frameborder="0" style="border:0; margin: auto; width: 100%; margin-bottom: 32px;" allowfullscreen></iframe>
    </section>

    <div class="container" style="margin-bottom: 32px;">
        <article>
            <header style="margin-bottom: 32px;">
                <h3>Besök följande utbildningarna</h3>
            </header>

            <div class="row justify-content-center">
                <#assign card_size_icon = true>
                <#list programs as program>
                    <div class="col-md-4">
                        <#include "../programs/program_card.ftl">
                    </div>
                </#list>
            </div>
        </article>
    </div>

    <#if contact??>
        <section class="jumbotron" style="margin-bottom: 0px; padding-top: 32px;">
            <div class="container">
                <article>
                    <header>
                        <h3>Har du frågor?</h3>
                    </header>

                    ${contact.text}
                </article>
            </div>
        </section>
    </#if>
</div>
