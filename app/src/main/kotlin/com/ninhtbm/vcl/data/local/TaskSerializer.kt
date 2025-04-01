package com.ninhtbm.vcl.data.local

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.ninhtbm.vcl.proto.TaskOrderProto
import java.io.InputStream
import java.io.OutputStream

object TaskSerializer: Serializer<TaskOrderProto> {
    override val defaultValue: TaskOrderProto = TaskOrderProto.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): TaskOrderProto {
        try {
            return TaskOrderProto.parseFrom(input)
        } catch (exception: Exception) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(
        t: TaskOrderProto,
        output: OutputStream) = t.writeTo(output)
}

val Context.settingsDataStore: DataStore<TaskOrderProto> by dataStore(
    fileName = "settings.pb",
    serializer = TaskSerializer
)