package se.odengymnasiet.index;

import org.bson.types.ObjectId;
import se.odengymnasiet.Application;
import se.odengymnasiet.Attributes;
import se.odengymnasiet.Controller;
import se.odengymnasiet.route.HttpRoute;
import se.odengymnasiet.student.Falafel;
import se.odengymnasiet.student.FalafelRepository;
import spark.Request;
import spark.Response;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class IndexController extends Controller<IndexManifest> {

    private final ArticleRepository articleRepository;
    private final FalafelRepository falafelRepository;
    private final MarketingRepository marketingRepository;

    public IndexController(Application app,
                           IndexManifest manifest,
                           Request request,
                           Response response) {
        super(app, manifest, request, response);

        this.articleRepository = manifest.getArticleRepository();
        this.falafelRepository = manifest.getFalafelRepository();
        this.marketingRepository = manifest.getMarketingRepository();
    }

    @HttpRoute("/")
    public Object index() {
        Collection<Marketing> collection =
                this.marketingRepository.findAllDeployed();

        Marketing marketing = null;
        if (!collection.isEmpty()) {
            marketing = collection.stream()
                    .skip((long) (Math.random() * collection.size()))
                    .findFirst().get();
        }

        LocalDate now = LocalDate.now();
        int year = now.getYear();
        int week = now.get(WeekFields.ISO.weekOfWeekBasedYear());
        List<Falafel> falafels = new ArrayList<>(this.falafelRepository
                .findFor(year, week));

        List<DayOfWeek> days = Arrays.asList(DayOfWeek.values());
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

        Collections.sort(falafels);

        Attributes attributes = Attributes.create()
                .add("marketing", marketing)
                .add("falafels", falafels);
        return this.ok("index", attributes, null);
    }

    @HttpRoute("/about")
    public Object about() {
        Article article = this.articleRepository.findByPath("about");
        if (article == null) {
            article = Article.NULL;
        }

        Attributes attributes = Attributes.create()
                .add("article", article);
        return this.ok("about", attributes, article.getTitle());
    }
}
