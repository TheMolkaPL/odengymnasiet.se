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

package se.odengymnasiet.falafel;

import se.odengymnasiet.LocalRepository;
import se.odengymnasiet.Repository;
import se.odengymnasiet.RepositoryHandler;

import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Collectors;

public interface FalafelRepository extends Repository<Falafel> {

    FalafelRepository LOCAL = new LocalFalafelRepository();

    @Deprecated
    default Collection<Falafel> findAllFor(int week) {
        return this.findAllFor(LocalDate.now().getYear(), week);
    }

    Collection<Falafel> findAllFor(int year, int week);

    default Collection<Falafel> findAllForNow() {
        LocalDate now = LocalDate.now();
        return this.findAllFor(now.getYear(), now.get(Falafel.WEEK_FIELD));
    }
}

@RepositoryHandler(FalafelRepository.class)
class LocalFalafelRepository extends LocalRepository<Falafel>
                             implements FalafelRepository {

    @Override
    public Collection<Falafel> findAllFor(int year, int week) {
        return this.container.values().stream()
                .filter(falafel -> falafel.getYear() == year)
                .filter(falafel -> falafel.getWeek() == week)
                .collect(Collectors.toList());
    }
}
