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
import org.bson.types.ObjectId;

import java.time.Instant;

public abstract class Model implements DocumentSerializable {

    public static final String FIELD_ID = "_id";
    public static final String FIELD_UPDATED_AT = "updated_at";

    private ObjectId id;
    private Instant updatedAt;

    public Model() {
        this(ObjectId.get());
    }

    public Model(ObjectId id) {
        this.id = id;
    }

    public Model(Document data) {
        this(data.getObjectId(FIELD_ID));
        this.setUpdatedAt(data.get(FIELD_UPDATED_AT, Instant.class));
    }

    public ObjectId getId() {
        return this.id;
    }

    public Instant getCreatedAt() {
        ObjectId id = this.getId();
        if (id != null) {
            return this.getId().getDate().toInstant();
        }

        return null;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public final Document serialize() {
        return this.serialize(new Document());
    }

    @Override
    public Document serialize(Document data) {
        if (this.getId() == null) {
            this.setId(ObjectId.get());
        }

        if (this.getUpdatedAt() == null) {
            this.setUpdatedAt(this.getId().getDate().toInstant());
        }

        data.put(FIELD_ID, this.getId());
        data.put(FIELD_UPDATED_AT, this.getUpdatedAt());
        return data;
    }

    public static Document filterUpdate(Document update) {
        update.remove(FIELD_ID); // _id is unique and cannot be updated
        update.put(FIELD_UPDATED_AT, Instant.now());
        return update;
    }
}
