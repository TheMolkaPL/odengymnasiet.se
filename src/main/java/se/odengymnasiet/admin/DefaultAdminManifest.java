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
import se.odengymnasiet.RepositoryContainer;
import se.odengymnasiet.index.IndexManifest;
import se.odengymnasiet.user.UserRepository;

@ManifestInfo(name = "Admin",
              parent = IndexManifest.class,
              master = DefaultAdminController.class,
              route = "/admin")
public class DefaultAdminManifest
        extends AdminManifest<DefaultAdminController> {

    private UserRepository userRepository;

    @Override
    public void installRepositories(RepositoryContainer repositories) {
        this.userRepository = repositories.of(UserRepository.class);
    }

    public UserRepository getUserRepository() {
        return this.userRepository;
    }
}
