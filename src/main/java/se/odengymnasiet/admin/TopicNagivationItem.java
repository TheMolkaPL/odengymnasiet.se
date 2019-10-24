/*
 * Copyright 2019 Aleksander Jagiełło <themolkapl@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package se.odengymnasiet.admin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    public static List<TopicNagivationItem> listHolders(
            List<TopicHolder> topics, String now) {
        List<Topic> items = topics.stream()
                .map(TopicHolder::getTopic)
                .collect(Collectors.toList());

        return list(items, now);
    }

    public static List<TopicNagivationItem> list(
            List<Topic> topics, String now) {
        List<TopicNagivationItem> items = new ArrayList<>();
        topics.forEach(topic -> {
            boolean active = topic.getPath().equals(now);

            items.add(new TopicNagivationItem(topic, active));
        });

        Collections.sort(items);
        return items;
    }
}
