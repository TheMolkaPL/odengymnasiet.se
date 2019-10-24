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

import se.odengymnasiet.LocalRepository;
import se.odengymnasiet.Repository;
import se.odengymnasiet.RepositoryHandler;

import java.util.Collection;
import java.util.stream.Collectors;

public interface ArticleRepository extends Repository<Article> {

    ArticleRepository LOCAL = new LocalArticleRepository();

    Collection<Article> findAllByStartingPath(String startingPath);

    Article findByPath(String path);
}

@RepositoryHandler(ArticleRepository.class)
class LocalArticleRepository extends LocalRepository<Article>
                             implements ArticleRepository {

    @Override
    public Collection<Article> findAllByStartingPath(String startingPath) {
        return this.container.values().stream()
                .filter(article -> article.getPath().startsWith(startingPath))
                .collect(Collectors.toList());
    }

    @Override
    public Article findByPath(String path) {
        return this.container.values().stream()
                .filter(article -> article.getPath().equals(path))
                .findFirst().get();
    }
}
