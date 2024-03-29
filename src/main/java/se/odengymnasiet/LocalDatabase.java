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

package se.odengymnasiet;

import org.slf4j.Logger;
import se.odengymnasiet.article.ArticleRepository;
import se.odengymnasiet.contact.GroupRepository;
import se.odengymnasiet.contact.PersonRepository;
import se.odengymnasiet.falafel.FalafelRepository;
import se.odengymnasiet.index.MarketingRepository;
import se.odengymnasiet.openhouse.OpenHouseRepository;
import se.odengymnasiet.program.ProgramRepository;
import se.odengymnasiet.student.StudentServiceRepository;
import se.odengymnasiet.user.UserRepository;

import java.util.Arrays;
import java.util.function.Consumer;

/**
 * This is the simplest implementation of the {@link Database} object. It just
 * saves the information in-memory which is lost after the request is handled.
 */
public class LocalDatabase extends Database {

    public LocalDatabase(Application app, Logger logger) {
        super(app, logger);
    }

    @Override
    public void connect() throws Exception {
    }

    @Override
    public void disconnect() throws Exception {
    }

    @Override
    public void installDefaultRepositories(Consumer<Repository> consumer) {
        Arrays.asList(
                ArticleRepository.LOCAL,
                FalafelRepository.LOCAL,
                GroupRepository.LOCAL,
                MarketingRepository.LOCAL,
                OpenHouseRepository.LOCAL,
                PersonRepository.LOCAL,
                ProgramRepository.LOCAL,
                StudentServiceRepository.LOCAL,
                UserRepository.LOCAL
        ).forEach(consumer::accept);
    }
}
