package amata1219.packetized.text.translator

import amata1219.xeflection.{AnyReflected, Reflect}
import io.netty.channel.{ChannelDuplexHandler, ChannelHandlerContext, ChannelPromise}
import org.bukkit.entity.Player

class SendingPacketHandler(val player: Player) extends ChannelDuplexHandler {

  override def write(context: ChannelHandlerContext, packet: Any, promise: ChannelPromise): Unit = {
    if (player.isUsingAutomaticTranslation) {
      val packetName = Reflect.on(packet)
        .call("getClass")
        .call("getSimpleName")
        .as[String]

      val translator = packetName match {
        case "PacketPlayOutChat" => ChatPacketizedTextTranslator
        case _ => ChatPacketizedTextTranslator
      }

      val reflected: AnyReflected = Reflect.on(packet)

      translator.apply(reflected, SendingPacketHandler.translate)
    }

    super.write(context, packet, promise)
  }

  implicit class XPlayer(val player: Player) {
    def isUsingAutomaticTranslation: Boolean = true
  }

  object SendingPacketHandler {
    def translate(text: String): String = text
  }

}
