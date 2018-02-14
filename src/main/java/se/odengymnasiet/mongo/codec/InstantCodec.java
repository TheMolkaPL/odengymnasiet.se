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
