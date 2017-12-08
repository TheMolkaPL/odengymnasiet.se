<div id="fb-root"></div>
<script>(function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) return;
  js = d.createElement(s); js.id = id;
  js.src = 'https://connect.facebook.net/sv_SE/sdk.js#xfbml=1&version=v2.11&appId=753088918232029';
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));</script>

<div class="jumbotron jumbotron-fluid brand-jumbotron" style="background-image: url('${marketing.image}');
                                                              background-repeat: no-repeat;
                                                              background-size: cover;
                                                              <#if marketing.fixed>background-attachment: fixed;</#if>
                                                              background-position: 50% ${marketing.position * 100}%;">
    <div class="container">
        <h1 class="display-1" style="text-shadow: 0 1px 10px rgba(0,0,0,.5), 0 0 30px rgba(0,0,0,.1);">Odengymnasiet</h1>
        <h3 style="text-shadow: 0 1px 10px rgba(0,0,0,.5), 0 0 30px rgba(0,0,0,.1); color: #51ADE5;">Skolan i nuet med sikte på framtiden</h3>

        <div style="margin-top: 50px;">
            <a class="btn btn-primary btn-lg" href="/programs" style="padding-left: 50px;
                                                                      padding-right: 50px;
                                                                      font-family: 'Roboto', sans-serif;
                                                                      font-size: 25px;
                                                                      font-weight: 300;
                                                                      background-color: #254A9F;
                                                                      border-color: #254A9F;">Våra utbildningar</a>
        </div>
    </div>
</div>

<article>
    <div class="container">
        <div class="row">
            <div class="col-lg-6">
                <section>
                    <header>
                        <h3>Aktuellt</h3>
                    </header>

                    <noscript><a href="//www.facebook.com/odengymnasiet/posts"
                                 class="btn btn-outline-dark btn-lg btn-block"
                                 target="_blank"
                                 rel="noopener"
                                 style="border: 1px solid;
                                        border-color: #e5e6e9 #dfe0e4 #d0d1d5;
                                        border-radius: 3px;
                                        margin-top: 15px;
                                        margin-bottom: 15px;">Visa aktuella inlägg</a></noscript>

                    <div class="fb-page"
                         data-href="https://www.facebook.com/odengymnasiet"
                         data-width="500"
                         data-height="1024"
                         data-tabs="timeline"
                         data-hide-cover="true"
                         data-show-facepile="false"
                         data-hide-cta="false"
                         data-small-header="true"
                         data-adapt-container-width="false"></div>

                    <a id="more-posts-button"
                       href="//www.facebook.com/odengymnasiet/posts"
                       class="btn btn-outline-dark btn-lg"
                       target="_blank"
                       rel="noopener"
                       style="border: 1px solid;
                              border-color: #e5e6e9 #dfe0e4 #d0d1d5;
                              border-radius: 3px;
                              margin-top: 15px;
                              width: 500px;
                              display: none;">Visa fler inlägg</a>
                </section>
            </div>

            <div class="col-lg-6">
                <section>
                </section>
            </div>
        </div>
    </div>
</article>

<script>$('#more-posts-button').css('display', 'block');</script>
