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

import se.odengymnasiet.ManifestInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

@ManifestInfo(master = TopicsController.class,
              name = "Admin-Topics",
              parent = DefaultAdminManifest.class,
              route = "/topics")
public class TopicsManifest extends AdminManifest<TopicsController> {

    private SortedMap<String, Topic> topics = new TreeMap<>();

    @Override
    public void enable() {
        this.installTopics();
    }

    public Topic getTopic(String path) {
        return this.topics.get(Topic.normalizePath(path));
    }

    public List<Topic> getTopics() {
        return new ArrayList<>(this.topics.values());
    }

    private void installTopics() {
        Arrays.asList(
                Topic.create("articles", "Artiklar"),
                Topic.create("groups", "Grupper"),
                Topic.create("marketings", "Marknadsföring"),
                Topic.create("menus", "Matsedel"),
                Topic.create("open-houses", "Öppet hus"),
                Topic.create("persons", "Personal"),
                Topic.create("programs", "Utbildningar"),
                Topic.create("student-services", "Externa referenser"),
                Topic.create("users", "Användare")
        ).forEach(topic -> this.topics.put(topic.getPath(), topic));
    }
}
