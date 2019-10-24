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

import se.odengymnasiet.LocalRepository;
import se.odengymnasiet.Repository;
import se.odengymnasiet.RepositoryHandler;

import java.util.Collection;
import java.util.stream.Collectors;

public interface PersonRepository extends Repository<Person> {

    PersonRepository LOCAL = new LocalPersonRepository();

    Collection<Person> findContactable();
}

@RepositoryHandler(PersonRepository.class)
class LocalPersonRepository extends LocalRepository<Person>
                            implements PersonRepository {
    @Override
    public Collection<Person> findContactable() {
        return this.container.values().stream()
                .filter(Person::isContactable)
                .collect(Collectors.toList());
    }
}
