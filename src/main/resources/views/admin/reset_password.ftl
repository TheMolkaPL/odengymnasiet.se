<div class="container">
    <div class="row justify-content-center" style="padding-top: 25px; padding-bottom: 25px;">
        <div class="col-lg-6">
            <section class="card border-primary">
                <div class="card-body text-center">
                    <header>
                        <h4 class="card-title">Glömt lösenord</h4>
                    </header>

                    <form action="/admin/reset-password" method="post">
                        <div class="form-group">
                            <input type="email" id="login_email" name="login[email]" class="form-control" placeholder="E-postadress" required>
                        </div>

                        <div class="form-group">
                            <input type="submit" class="btn btn-primary btn-block" value="Skicka instruktionerna" style="cursor: pointer;">
                        </div>

                        <span>...eller <a class="text-primary" href="/admin/login">logga in</a>.</span>
                    </form>
                </div>
            </section>
        </div>
    </div>
</div>
