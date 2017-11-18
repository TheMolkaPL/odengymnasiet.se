package se.odengymnasiet.index;

import org.apache.commons.lang3.RandomUtils;
import se.odengymnasiet.Application;
import se.odengymnasiet.Attributes;
import se.odengymnasiet.Controller;
import se.odengymnasiet.route.Route;
import spark.Request;
import spark.Response;

import java.util.Collection;

public class IndexController extends Controller {
    private final MarketingRepository marketings;

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
            marketing = collection.toArray(new Marketing[collection
                    .size()])[RandomUtils.nextInt(0, collection.size())];
        }

        Attributes attributes = Attributes.create()
                .add("marketing", marketing);
        return this.ok("index", attributes, null);
    }

    @Route("/about")
    public Object about() {
        return this.ok("about", Attributes.create(), "Om Odengymnasiet");
    }
}
