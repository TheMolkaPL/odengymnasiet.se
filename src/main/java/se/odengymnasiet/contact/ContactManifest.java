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

package se.odengymnasiet.contact;

import se.odengymnasiet.Manifest;
import se.odengymnasiet.ManifestInfo;
import se.odengymnasiet.RepositoryContainer;
import se.odengymnasiet.article.ArticleRepository;
import se.odengymnasiet.index.IndexManifest;

@ManifestInfo(name = "Contact",
              parent = IndexManifest.class,
              master = ContactController.class,
              route = "/contact")
public class ContactManifest extends Manifest<ContactController> {

    private ArticleRepository articleRepository;
    private GroupRepository groupRepository;
    private PersonRepository personRepository;

    @Override
    public void installRepositories(RepositoryContainer repositories) {
        this.articleRepository = repositories.of(ArticleRepository.class);
        this.groupRepository = repositories.of(GroupRepository.class);
        this.personRepository = repositories.of(PersonRepository.class);
    }

    public ArticleRepository getArticleRepository() {
        return this.articleRepository;
    }

    public GroupRepository getGroupRepository() {
        return this.groupRepository;
    }

    public PersonRepository getPersonRepository() {
        return this.personRepository;
    }
}
