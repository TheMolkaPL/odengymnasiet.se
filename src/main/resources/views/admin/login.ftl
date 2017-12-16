<div class="container">
    <div class="row justify-content-center" style="padding-top: 25px; padding-bottom: 25px;">
        <div class="col-lg-4">
            <section class="card border-primary">
                <div class="card-body text-center">
                    <header>
                        <h4 class="card-title">Logga in</h4>
                    </header>

                    <form action="/admin/login" method="post">
                        <div class="form-group">
                            <input type="email" id="login_email" name="login[email]" class="form-control" placeholder="E-postadress" required>
                        </div>

                        <div class="form-group">
                            <input type="password" id="login_password" name="login[password]" class="form-control" placeholder="Lösenord" required>
                        </div>

                        <div class="form-group">
                            <input type="submit" class="btn btn-primary btn-block" value="Logga in" style="cursor: pointer;">
                        </div>

                        <span>Har du glömt ditt lösenord? <a class="text-primary" href="/admin/reset-password">Klicka här!</a></span>
                    </form>
                </div>
            </section>
        </div>
    </div>
</div>
