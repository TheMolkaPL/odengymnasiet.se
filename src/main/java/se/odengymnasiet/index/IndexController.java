package se.odengymnasiet.index;

import se.odengymnasiet.Application;
import se.odengymnasiet.Attributes;
import se.odengymnasiet.Controller;
import se.odengymnasiet.route.HttpRoute;
import spark.Redirect;
import spark.Request;
import spark.Response;

import java.util.Collection;

public class IndexController extends Controller<IndexManifest> {

    private final MarketingRepository marketingRepository;

    public IndexController(Application app,
                           IndexManifest manifest,
                           Request request,
                           Response response) {
        super(app, manifest, request, response);

        this.marketingRepository = manifest.getMarketingRepository();
    }

    @HttpRoute("/")
    public Object index() {
        Collection<Marketing> collection =
                this.marketingRepository.findAllDeployed();

        Marketing marketing = Marketing.NULL;
        if (!collection.isEmpty()) {
            marketing = collection.stream()
                    .skip((long) (Math.random() * collection.size()))
                    .findFirst()
                    .get();
        }

        Attributes attributes = Attributes.create()
                .add("marketing", marketing)
                .add("news", this.getManifest().getNews());
        return this.ok("index", attributes, null);
    }

    @HttpRoute("/about")
    public Object about() {
        return this.ok("about", Attributes.create(), "Om Odengymnasiet");
    }

    //
    // Redirects
    //

    @HttpRoute("/facebook")
    public Object facebook() {
        return this.redirect("https://facebook.com/odengymnasiet");
    }

    @HttpRoute("/mail")
    public Object mail() {
        return this.redirect("https://mail.aprendere.se");
    }

    @HttpRoute("/schoolsoft")
    public Object schoolSoft() {
        return this.redirect("https://sms.schoolsoft.se/aprendere");
    }

    private Object redirect(String target) {
        Redirect.Status statusCode = Redirect.Status.MOVED_PERMANENTLY;
        this.getResponse().redirect(target, statusCode.intValue());
        return null;
    }
}
