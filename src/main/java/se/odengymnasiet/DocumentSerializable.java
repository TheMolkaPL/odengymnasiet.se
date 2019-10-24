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

import org.bson.Document;

/**
 * Something that can be serialized into a {@link Document}. Note that the
 * {@link Document} class is provided by the MongoDB Java Driver, but it's
 * global over the application. All the implemented databases (inherit
 * {@link Database}) are forced to use the {@link Document} class objects.
 */
public interface DocumentSerializable {

    default Document serialize() {
        return this.serialize(new Document());
    }

    Document serialize(Document data);
}
