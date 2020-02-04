package net.mamoe.mirai.api.http.dto

import kotlinx.serialization.*
import kotlinx.serialization.json.Json

interface DTO

object MiraiJson {
//    val json = Json(context = SerializersModule {
//        polymorphic(MessagePacketDTO.serializer()) {
//            GroupMessagePacketDTO::class with GroupMessagePacketDTO.serializer()
//            FriendMessagePacketDTO::class with FriendMessagePacketDTO.serializer()
//        }
//    })
    val json = Json
}

// 解析失败时直接返回null，由路由判断响应400状态
@UseExperimental(ImplicitReflectionSerializer::class)
inline fun <reified T : Any> String.jsonParseOrNull(
    serializer: DeserializationStrategy<T>? = null
): T? = try {
    if(serializer == null) MiraiJson.json.parse(this) else Json.parse(this)
} catch (e: Exception) { null }



@UseExperimental(ImplicitReflectionSerializer::class, UnstableDefault::class)
inline fun <reified T : Any> T.toJson(
    serializer: SerializationStrategy<T>? = null
): String = if (serializer == null) MiraiJson.json.stringify(this)
            else MiraiJson.json.stringify(serializer, this)



// 序列化列表时，stringify需要使用的泛型是T，而非List<T>
// 因为使用的stringify的stringify(objs: List<T>)重载
@UseExperimental(ImplicitReflectionSerializer::class, UnstableDefault::class)
inline fun <reified T : Any> List<T>.toJson(
    serializer: SerializationStrategy<List<T>>? = null
): String = if (serializer == null) MiraiJson.json.stringify(this)
else MiraiJson.json.stringify(serializer, this)