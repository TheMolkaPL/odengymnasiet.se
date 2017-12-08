package se.odengymnasiet.index;

import se.odengymnasiet.Application;
import se.odengymnasiet.Attributes;
import se.odengymnasiet.Controller;
import se.odengymnasiet.route.HttpRoute;
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
}
