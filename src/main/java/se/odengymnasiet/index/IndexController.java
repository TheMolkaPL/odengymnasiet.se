package se.odengymnasiet.index;

import se.odengymnasiet.Application;
import se.odengymnasiet.Attributes;
import se.odengymnasiet.Controller;
import se.odengymnasiet.route.Route;
import spark.Redirect;
import spark.Request;
import spark.Response;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class IndexController extends Controller {

    private final MarketingRepository marketings;
    private final List<Long> news = Arrays.asList(1362168337242891L,
                                                  1360244227435302L,
                                                  1352889234837468L);

    public IndexController(Application application,
                           Request request,
                           Response response) {
        super(application, request, response);

        this.marketings = application.getRepository(MarketingRepository.class);
    }

    @Route("/")
    public Object index() {
        Collection<Marketing> collection = this.marketings.findAllDeployed();

        Marketing marketing = Marketing.NULL;
        if (!collection.isEmpty()) {
            marketing = collection.stream()
                    .skip((long) (Math.random() * collection.size()))
                    .findFirst()
                    .get();
        }

        Attributes attributes = Attributes.create()
                .add("marketing", marketing)
                .add("news", this.news);
        return this.ok("index", attributes, null);
    }

    @Route("/about")
    public Object about() {
        return this.ok("about", Attributes.create(), "Om Odengymnasiet");
    }

    //
    // Redirects
    //

    @Route("/facebook")
    public Object facebook() {
        return this.redirect("https://facebook.com/odengymnasiet");
    }

    @Route("/mail")
    public Object mail() {
        return this.redirect("https://mail.aprendere.se");
    }

    @Route("/schoolsoft")
    public Object schoolSoft() {
        return this.redirect("https://sms.schoolsoft.se/aprendere");
    }

    private Object redirect(String target) {
        Redirect.Status statusCode = Redirect.Status.MOVED_PERMANENTLY;
        this.getResponse().redirect(target, statusCode.intValue());
        return null;
    }
}
