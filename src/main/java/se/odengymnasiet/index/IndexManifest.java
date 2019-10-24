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

package se.odengymnasiet.index;

import se.odengymnasiet.Manifest;
import se.odengymnasiet.ManifestInfo;
import se.odengymnasiet.RepositoryContainer;
import se.odengymnasiet.article.ArticleRepository;
import se.odengymnasiet.falafel.FalafelRepository;
import se.odengymnasiet.openhouse.OpenHouseRepository;
import se.odengymnasiet.program.ProgramRepository;

@Index
@ManifestInfo(name = "Index",
              parent = IndexManifest.class,
              master = IndexController.class)
public class IndexManifest extends Manifest<IndexController> {

    private ArticleRepository articleRepository;
    private FalafelRepository falafelRepository;
    private MarketingRepository marketingRepository;
    private OpenHouseRepository openHouseRepository;
    private ProgramRepository programRepository;

    @Override
    public void installRepositories(RepositoryContainer repositories) {
        this.articleRepository = repositories.of(ArticleRepository.class);
        this.falafelRepository = repositories.of(FalafelRepository.class);
        this.marketingRepository = repositories.of(MarketingRepository.class);
        this.openHouseRepository = repositories.of(OpenHouseRepository.class);
        this.programRepository = repositories.of(ProgramRepository.class);
    }

    public ArticleRepository getArticleRepository() {
        return this.articleRepository;
    }

    public FalafelRepository getFalafelRepository() {
        return this.falafelRepository;
    }

    public MarketingRepository getMarketingRepository() {
        return this.marketingRepository;
    }

    public OpenHouseRepository getOpenHouseRepository() {
        return this.openHouseRepository;
    }

    public ProgramRepository getProgramRepository() {
        return this.programRepository;
    }
}
