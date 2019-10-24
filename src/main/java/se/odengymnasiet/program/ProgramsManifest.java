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

package se.odengymnasiet.program;

import se.odengymnasiet.Manifest;
import se.odengymnasiet.ManifestInfo;
import se.odengymnasiet.RepositoryContainer;
import se.odengymnasiet.article.ArticleRepository;
import se.odengymnasiet.index.IndexManifest;
import se.odengymnasiet.openhouse.OpenHouseRepository;

@ManifestInfo(name = "Programs",
              parent = IndexManifest.class,
              master = ProgramsController.class,
              route = "/programs")
public class ProgramsManifest extends Manifest<ProgramsController> {

    private ArticleRepository articleRepository;
    private OpenHouseRepository openHouseRepository;
    private ProgramRepository programRepository;

    @Override
    public void installRepositories(RepositoryContainer repositories) {
        this.articleRepository = repositories.of(ArticleRepository.class);
        this.openHouseRepository = repositories.of(OpenHouseRepository.class);
        this.programRepository = repositories.of(ProgramRepository.class);
    }

    public ArticleRepository getArticleRepository() {
        return this.articleRepository;
    }

    public OpenHouseRepository getOpenHouseRepository() {
        return this.openHouseRepository;
    }

    public ProgramRepository getProgramRepository() {
        return this.programRepository;
    }
}
