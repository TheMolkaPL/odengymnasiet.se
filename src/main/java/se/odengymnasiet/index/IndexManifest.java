package se.odengymnasiet.index;

import se.odengymnasiet.Manifest;
import se.odengymnasiet.ManifestInfo;
import se.odengymnasiet.RepositoryContainer;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Index
@ManifestInfo(name = "Index",
              parent = IndexManifest.class,
              master = IndexController.class)
public class IndexManifest extends Manifest<IndexController> {

    private MarketingRepository marketingRepository;

    private final List<Long> news = new ArrayList<>();
    private Instant newsCooldown;

    @Override
    public void installRepositories(RepositoryContainer repositories) {
        this.marketingRepository = repositories.of(MarketingRepository.class);
    }

    @Override
    public void enable() {
        // TODO fetch posts from Facebook
        this.news.addAll(Arrays.asList(
                1362168337242891L,
                1360244227435302L,
                1352889234837468L));
        this.newsCooldown = Instant.now();
    }

    public MarketingRepository getMarketingRepository() {
        return this.marketingRepository;
    }

    public List<Long> getNews() {
        return this.news;
    }

    private boolean newsTimedOut() {
        return Instant.now()
                .minusMillis(TimeUnit.MINUTES.toMillis(5L))
                .isAfter(this.newsCooldown);
    }
}
