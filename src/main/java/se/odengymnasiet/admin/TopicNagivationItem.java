package se.odengymnasiet.admin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TopicNagivationItem implements Comparable<TopicNagivationItem> {

    private final Topic topic;
    private final boolean active;

    public TopicNagivationItem(Topic topic) {
        this(topic, false);
    }

    public TopicNagivationItem(Topic topic, boolean active) {
        this.topic = topic;
        this.active = active;
    }

    @Override
    public int compareTo(TopicNagivationItem o) {
        return this.getTopic().compareTo(o.getTopic());
    }

    public Topic getTopic() {
        return this.topic;
    }

    public boolean isActive() {
        return this.active;
    }

    public static List<TopicNagivationItem> list(List<Topic> topics,
                                                 String now) {
        List<TopicNagivationItem> items = new ArrayList<>();
        topics.forEach(topic -> {
            boolean active = topic.getPath().equals(now);

            items.add(new TopicNagivationItem(topic, active));
        });

        Collections.sort(items);
        return items;
    }
}
