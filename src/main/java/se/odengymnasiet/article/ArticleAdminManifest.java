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

package se.odengymnasiet.article;

import se.odengymnasiet.ManifestInfo;
import se.odengymnasiet.RepositoryContainer;
import se.odengymnasiet.admin.AdminManifest;
import se.odengymnasiet.admin.Topic;
import se.odengymnasiet.admin.TopicHolder;
import se.odengymnasiet.admin.TopicsManifest;

@ManifestInfo(name = "Admin-Articles",
              parent = TopicsManifest.class,
              master = ArticleAdminController.class,
              route = "articles")
public class ArticleAdminManifest extends AdminManifest<ArticleAdminController>
                                  implements TopicHolder {

    public static final Topic TOPIC = Topic.create("articles", "Artiklar");

    private ArticleRepository articleRepository;

    @Override
    public void installRepositories(RepositoryContainer repositories) {
        this.articleRepository = repositories.of(ArticleRepository.class);
    }

    @Override
    public Topic getTopic() {
        return TOPIC;
    }

    public ArticleRepository getArticleRepository() {
        return this.articleRepository;
    }
}
