//package io.github.inggameteam.inggame.component.delegate
//
//import org.bson.BsonReader
//import org.bson.BsonWriter
//import org.bson.codecs.Codec
//import org.bson.codecs.DecoderContext
//import org.bson.codecs.EncoderContext
//import java.lang.Boolean
//
//class WrapperCodec : Codec<Wrapper> {
//    override fun encode(writer: BsonWriter, value: Wrapper?, encoderContext: EncoderContext?) {
//        if (value != null) {
//            writer.writeString(value.nameSpace.toString())
//        }
//    }
//
//    override fun decode(reader: BsonReader, decoderContext: DecoderContext?): Wrapper? {
//        return if (reader.readBoolean()) Wrapper.ON else Wrapper.OFF
//        return if(reader.readString())
//    }
//
//    override fun getEncoderClass(): Class<Wrapper?>? {
//        return Wrapper::class.java
//    }
//}