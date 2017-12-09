<div class="jumbotron jumbotron-fluid">
    <div class="container">
        <header>
            <h2>Anmälan till <a href="/programs/${program.path}">${program.title}</a></h2>
        </header>
    </div>
</div>

<div class="container">
    <div class="col-lg-9">
        <article>
            <form action="/programs/${program.path}/application" method="post">
                <section style="margin-bottom: 32px;">
                    <header style="margin-bottom: 16px;">
                        <h3>Uppgifter elev</h3>
                    </header>

                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <label for="student_first_name">Förnamn</label>
                            <input type="text" id="student_first_name" name="student[first_name]" class="form-control" placeholder="Förnamn" required>
                        </div>
                        <div class="form-group col-md-6">
                            <label for="student_last_name">Efternamn</label>
                            <input type="text" id="student_last_name" name="student[last_name]" class="form-control" placeholder="Efternamn" required>
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <label for="student_person_number">Personnummer</label>
                            <input type="text" id="student_person_number" name="student[person_number]" class="form-control" placeholder="ÅÅÅÅMMDD-XXXX" required>
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group col-md-12">
                            <label for="student_address">Adress</label>
                            <input type="text" id="student_address" name="student[address]" class="form-control" placeholder="Gata, nummer" required>
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group col-md-4">
                            <label for="student_zip">Postnummer</label>
                            <input type="text" id="student_zip" name="student[zip]" class="form-control" placeholder="000 00" required>
                        </div>
                        <div class="form-group col-md-8">
                            <label for="student_city">Stad</label>
                            <input type="text" id="student_city" name="student[city]" class="form-control" placeholder="Stad" required>
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group col-md-12">
                            <label for="student_email">E-postadress</label>
                            <input type="email" id="student_email" name="student[email]" class="form-control" placeholder="E-postadress" required>
                        </div>
                    </div>
                </section>

                <section style="margin-bottom: 32px;">
                    <header style="margin-bottom: 16px;">
                        <h3>Uppgifter vårdnadshavare</h3>
                    </header>

                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <label for="guardian_first_name">Förnamn</label>
                            <input type="text" id="guardian_first_name" name="guardian[first_name]" class="form-control" placeholder="Förnamn" required>
                        </div>
                        <div class="form-group col-md-6">
                            <label for="guardian_last_name">Efternamn</label>
                            <input type="text" id="guardian_last_name" name="guardian[last_name]" class="form-control" placeholder="Efternamn" required>
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group col-md-12">
                            <label for="guardian_address">Adress</label>
                            <input type="text" id="guardian_address" name="guardian[address]" class="form-control" placeholder="Gata, nummer" required>
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group col-md-4">
                            <label for="guardian_zip">Postnummer</label>
                            <input type="text" id="guardian_zip" name="guardian[zip]" class="form-control" placeholder="000 00" required>
                        </div>
                        <div class="form-group col-md-8">
                            <label for="guardian_city">Stad</label>
                            <input type="text" id="guardian_city" name="guardian[city]" class="form-control" placeholder="Stad" required>
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group col-md-12">
                            <label for="guardian_email">E-postadress</label>
                            <input type="email" id="guardian_email" name="guardian[email]" class="form-control" placeholder="E-postadress" required>
                        </div>
                    </div>
                </section>

                <section style="margin-bottom: 32px;">
                    <header style="margin-bottom: 16px;">
                        <h3>Kontaktuppgifter till senaste skola</h3>
                    </header>

                    <div class="form-row">
                        <div class="form-group col-md-12">
                            <label for="school_name">Namn på nuvarande skola</label>
                            <input type="text" id="school_name" name="school[name]" class="form-control" placeholder="Namn på nuvarande skola" required>
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <label for="school_email">E-postadress</label>
                            <input type="email" id="school_email" name="school[email]" class="form-control" placeholder="E-postadress" required>
                        </div>
                        <div class="form-group col-md-6">
                            <label for="school_telephone">Telefonnummer</label>
                            <input type="tel" id="school_telephone" name="school[telephone]" class="form-control" placeholder="Telefonnummer" required>
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="from-group col-md-6">
                            <label for="school_mentor_first_name">Fönamn på mentor</label>
                            <input type="text" id="school_mentor_first_name" name="school[mentor_first_name]" class="form-control" placeholder="Fönamn på mentor" required>
                        </div>
                        <div class="form-group col-md-6">
                            <label for="school_mentor_last_name">Efternamn på mentor</label>
                            <input type="text" id="school_mentor_last_name" name="school[mentor_last_name]" class="form-control" placeholder="Efternamn på mentor" required>
                        </div>
                    </div>
                </section>

                <section style="margin-bottom: 32px;">
                    <header style="margin-bottom: 16px;">
                        <h3>Information om dina betyg</h3>
                    </header>

                    <div class="form-row">
                        <div class="form-group col-md-4">
                            <label for="grades_merit">Nuvarande meritvärde</label>
                            <input type="number" id="grades_merit" name="grades[merit]" class="form-control" placeholder="Nuvarande meritvärde" step="0.1" required>
                        </div>
                    </div>

                    <div>
                        <label>Saknas godkänt betyg?</label>
                        <div>
                            <div class="form-check">
                                <label class="form-check-label">
                                    <input type="radio" id="grades_missing_true" name="grades[missing]" class="form-check-input" value="true"
                                           onfocus="document.getElementById('grades_missing_list').focus();">
                                    Ja
                                </label>
                            </div>

                            <div class="form-group col-md-6">
                                <input type="text" id="grades_missing_list" name="grades[missing_list]" class="form-control form-control-sm" placeholder="I vilka ämnen?">
                            </div>
                        </div>

                        <div class="form-check">
                            <label class="form-check-label">
                                <input type="radio" id="grades_missing_false" name="grades[missing]" class="form-check-input" value="false">
                                Nej
                            </label>
                        </div>
                    </div>
                </section>

                <section style="margin-bottom: 32px;">
                    <header style="margin-bottom: 16px;">
                        <h3>Beskriv dig själv</h3>
                    </header>

                    <div class="form-row">
                        <div class="form-group col-md-12">
                            <label for="person_about">Vi är jätteglada för ditt intresse och vill gärna veta lite mer. Beskriv kort varför du söker till oss:</label>
                            <textarea id="person_about" name="person[about]" class="form-control" rows="6" style="width: 100%; resize: vertical;"></textarea>
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group col-md-12">
                            <label for="person_extra">Är det något annat som är bra för oss att veta?</label>
                            <textarea id="person_extra" name="person[extra]" class="form-control" rows="6" style="width: 100%; resize: vertical;"></textarea>
                        </div>
                    </div>
                </section>

                <section style="margin-bottom: 32px;">
                    <header style="margin-bottom: 16px;">
                        <h3>Hur hörde du talas om Odengymnasiet?</h3>
                    </header>

                    <div>
                        <div class="form-check">
                            <label class="form-check-label">
                                <input type="radio" id="feedback_hear_friends" name="feedback[hear]" class="form-check-input" value="Vänner">
                                Vänner
                            </label>
                        </div>
                        <div class="form-check">
                            <label class="form-check-label">
                                <input type="radio" id="feedback_hear_facebook" name="feedback[hear]" class="form-check-input" value="Facebook">
                                Facebook
                            </label>
                        </div>
                        <div class="form-check">
                            <label class="form-check-label">
                                <input type="radio" id="feedback_hear_gymnasiumse" name="feedback[hear]" class="form-check-input" value="gymnasium.se">
                                gymnasium.se
                            </label>
                        </div>
                        <div class="form-check">
                            <label class="form-check-label">
                                <input type="radio" id="feedback_hear_gymnasieguiden" name="feedback[hear]" class="form-check-input" value="Gymnasieguiden">
                                Gymnasieguiden
                            </label>
                        </div>
                        <div class="form-check">
                            <label class="form-check-label">
                                <input type="radio" id="feedback_hear_gymnasiemassan" name="feedback[hear]" class="form-check-input" value="Gymnasiemässan">
                                Gymnasiemässan
                            </label>
                        </div>
                        <div class="form-check">
                            <label class="form-check-label">
                                <input type="radio" id="feedback_hear_other" name="feedback[hear]" class="form-check-input" value="Annat"
                                       onfocus="document.getElementById('feedback_hear_other_value').focus();">
                                Annat
                            </label>
                        </div>

                        <div class="form-group col-md-6">
                            <input type="text" id="feedback_hear_other_value" name="feedback[hear_value]" class="form-control form-control-sm" placeholder="Vad?">
                        </div>
                    </div>
                </section>

                <input type="submit" class="btn btn-success" value="Skicka anmälan" style="cursor: pointer;">
                <input type="reset" class="btn btn-link text-danger" value="Rensa" style="cursor: pointer;">
            </form>
        </article>
    </div>
</div>
