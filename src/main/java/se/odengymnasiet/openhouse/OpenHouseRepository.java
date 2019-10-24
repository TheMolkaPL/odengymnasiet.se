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

package se.odengymnasiet.openhouse;

import se.odengymnasiet.LocalRepository;
import se.odengymnasiet.Repository;
import se.odengymnasiet.RepositoryHandler;

import java.time.Instant;
import java.util.Collection;
import java.util.stream.Collectors;

public interface OpenHouseRepository extends Repository<OpenHouse> {

    OpenHouseRepository LOCAL = new LocalOpenHouseRepository();

    Collection<OpenHouse> findAllComing();

    Collection<OpenHouse> findAllDeployed();

    Collection<OpenHouse> findAllDeployedComing();
}

@RepositoryHandler(OpenHouseRepository.class)
class LocalOpenHouseRepository extends LocalRepository<OpenHouse>
                               implements OpenHouseRepository {

    @Override
    public Collection<OpenHouse> findAllComing() {
        Instant now = Instant.now();
        return this.container.values().stream()
                .filter(openHouse -> openHouse.getStart().isAfter(now))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<OpenHouse> findAllDeployed() {
        return this.container.values().stream()
                .filter(OpenHouse::isDeployed)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<OpenHouse> findAllDeployedComing() {
        Instant now = Instant.now();
        return this.container.values().stream()
                .filter(OpenHouse::isDeployed)
                .filter(openHouse -> openHouse.getStart().isBefore(now))
                .collect(Collectors.toList());
    }
}
