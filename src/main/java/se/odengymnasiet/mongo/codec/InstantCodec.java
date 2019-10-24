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

package se.odengymnasiet.mongo.codec;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DateCodec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import java.time.Instant;
import java.util.Date;

/**
 * Converting {@link Date} (which the official MongoDB's Java Driver) uses to
 * more usable and friendly new Java 8's {@link Instant}s.
 */
public class InstantCodec implements Codec<Instant> {

    private final DateCodec dateCodec;

    public InstantCodec() {
        this(new DateCodec());
    }

    public InstantCodec(DateCodec dateCodec) {
        this.dateCodec = dateCodec;
    }

    @Override
    public Instant decode(BsonReader reader,
                          DecoderContext context) {
        return this.dateCodec.decode(reader, context).toInstant();
    }

    @Override
    public void encode(BsonWriter writer,
                       Instant instant,
                       EncoderContext context) {
        this.dateCodec.encode(writer, Date.from(instant), context);
    }

    @Override
    public Class<Instant> getEncoderClass() {
        return Instant.class;
    }
}
