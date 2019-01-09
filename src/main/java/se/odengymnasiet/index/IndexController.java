package se.odengymnasiet.index;

import org.bson.types.ObjectId;
import se.odengymnasiet.Application;
import se.odengymnasiet.Attributes;
import se.odengymnasiet.Controller;
import se.odengymnasiet.article.Article;
import se.odengymnasiet.article.ArticlePaths;
import se.odengymnasiet.falafel.Falafel;
import se.odengymnasiet.openhouse.OpenHouse;
import se.odengymnasiet.program.Program;
import se.odengymnasiet.route.HttpRoute;
import spark.Request;
import spark.Response;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.ToIntFunction;

public class IndexController extends Controller<IndexManifest> {

    public IndexController(Application app,
                           IndexManifest manifest,
                           Request request,
                           Response response) {
        super(app, manifest, request, response);
    }

    @HttpRoute("/")
    public Object index() {
        IndexManifest manifest = this.getManifest();

        Collection<Marketing> collection = new ArrayList<>(
                manifest.getMarketingRepository().findAllDeployed());

        // marketing
        Marketing marketing = null;
        if (!collection.isEmpty()) {
            marketing = collection.stream()
                    .skip((long) (Math.random() * collection.size()))
                    .findFirst().get();
        }

        // programs
        List<Program> programs = new ArrayList<>(
                manifest.getProgramRepository().findAllRecommended());
        Collections.sort(programs);

        // open houses
        List<OpenHouse> openHouses = new ArrayList<>(
                manifest.getOpenHouseRepository().findAllDeployedComing());
        Collections.sort(openHouses);

        // falafels
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        int week = now.get(Falafel.WEEK_FIELD);
        List<Falafel> falafels = new ArrayList<>(
                manifest.getFalafelRepository().findAllFor(year, week));

        List<DayOfWeek> days = new ArrayList<>(
                Arrays.asList(DayOfWeek.values()));
        falafels.forEach(day -> days.remove(day.getDay()));
        days.forEach(dayOfWeek -> {
            if (dayOfWeek.equals(DayOfWeek.SATURDAY) ||
                    dayOfWeek.equals(DayOfWeek.SUNDAY)) {
                return;
            }

            Falafel defaultValue = new Falafel((ObjectId) null);
            defaultValue.setYear(year); // This is not the real year
            defaultValue.setWeek(week);
            defaultValue.setDay(dayOfWeek);
            defaultValue.setDishes(Collections.emptyList());

            falafels.add(defaultValue);
        });
        falafels.sort(Comparator.naturalOrder());

        Attributes attributes = Attributes.create()
                .add("marketing", marketing)
                .add("programs", programs)
                .add("openHouses", openHouses)
                .add("falafels", falafels);
        return this.ok("index", attributes, null);
    }

    @HttpRoute("/about")
    public Object about() {
        Article article = this.getManifest().getArticleRepository()
                .findByPath(ArticlePaths.about());
        if (article == null) {
            article = Article.NULL;
        }

        Attributes attributes = Attributes.create()
                .add("article", article);
        return this.ok("about", attributes, article.getTitle());
    }
}
