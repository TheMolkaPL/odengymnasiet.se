<div class="jumbotron jumbotron-fluid">
    <div class="container">
            <header>
                <h2>Ansökan</h2>
            </header>
        </div>
    </div>
</div>

<article>
    <div class="container">
        <div class="row">
            <div class="col-md-9">
                <form action="" method="post">
                    <section>
                        <header>
                            <h3>Uppgifter elev</h3>
                        </header>

                        <div class="form-group row">
                            <div class="col-md-6">
                                <input type="text" class="form-control" placeholder="Förnamn">
                            </div>
                            <div class="col-md-6">
                                <input type="text" class="form-control" placeholder="Efternamn">
                            </div>
                        </div>

                        <div class="form-group row">
                            <div class="col-md-5">
                                <input type="text" class="form-control" placeholder="Personnummer">
                            </div>
                            <div class="col-md-7">
                                <input type="email" class="form-control" placeholder="E-post">
                            </div>
                        </div>
                    </section>

                    <section>
                        <header>
                            <h3>Uppgifter vårdnadshavare</h3>
                        </header>
                    </section>

                    <input type="submit" class="btn btn-primary" value="Skicka">
                </form>
            </div>

            <div class="col-md-3">
                <aside>
                    <#assign navigation_now = "application">
                    <#include "navigation.ftl">
                </aside>
            </div>
        </div>
    </div>
</article>

