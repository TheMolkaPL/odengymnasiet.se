<div class="jumbotron jumbotron-fluid">
    <div class="container">
        <header>
            <h2>Personal</h2>
        </header>
    </div>
</div>

<div class="container">
    <div class="row">
        <div class="col-md-9">
            <article>
                <#list groups as group>
                    <section>
                        <#if group.group.name??>
                            <header style="margin-top: 40px; margin-bottom: 20px;">
                                <h3>${group.group.name}</h3>
                            </header>
                        </#if>

                        <table class="table table-striped table-hover">
                            <thead>
                                <tr>
                                    <td scope="col">Namn</td>
                                    <td scope="col">${group.group.roleName}</td>
                                    <td scope="col">E-postadress</td>
                                    <td scope="col" style="width: 140px;">Telefonnummer</td>
                                </tr>
                            </thead>

                            <tbody>
                                <#list group.persons as person>
                                    <tr>
                                        <#assign personName = person.person.firstName + " " + person.person.lastName>
                                        <td<#if person.personGroup.focused> style="color: #51ADE5;"</#if>>${personName}</td>
                                        <td>${person.personGroup.role}</td>
                                        <td><a href="mailto:${person.person.email}">${person.person.email}</a></td>
                                        <td><a href="tel:${person.person.telephone}">${person.person.telephonePretty}</a></td>
                                    </tr>
                                </#list>
                            </tbody>
                        </table>
                    </section>
                </#list>
            </article>
        </div>

        <div class="col-md-3">
            <aside>
                <#assign navigation_now = "staff">
                <#include "navigation.ftl">
            </aside>
        </div>
    </div>
</div>
