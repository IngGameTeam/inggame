package io.github.inggameteam.inggame.component.wrapper

import org.bson.BsonReader
import org.bson.BsonWriter
import org.bson.codecs.Codec
import org.bson.codecs.DecoderContext
import org.bson.codecs.EncoderContext

class WrapperCodec : Codec<WrapperModel> {
    override fun encode(writer: BsonWriter?, value: WrapperModel?, encoderContext: EncoderContext?) {
        if (value != null && writer != null) {
            writer.writeString(WrapperModel::componentService.name, value.componentService)
            writer.writeString(WrapperModel::nameSpace.name, value.nameSpace)
            writer.writeString(WrapperModel::wrapperClass.name, value.wrapperClass)
        }
    }

    override fun getEncoderClass() = WrapperModel::class.java

    override fun decode(reader: BsonReader?, decoderContext: DecoderContext?): WrapperModel {
        if (reader != null) {
            return WrapperModel(
                reader.readString(WrapperModel::componentService.name),
                reader.readString(WrapperModel::nameSpace.name),
                reader.readString(WrapperModel::wrapperClass.name)
            )
        }
        throw AssertionError("an error occurred while decoding WrapperModel")
    }
}