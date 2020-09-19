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
      val text: String = translator.text(reflected)

      Main.instance.translation.config.getString(text) match {
        case null =>
        case translated => translator.apply(reflected, translated)
      }
    }

    super.write(context, packet, promise)
  }

  implicit class XPlayer(val player: Player) {
    def isUsingAutomaticTranslation: Boolean = Main.instance.config.config.getBoolean(player.getUniqueId.toString)
  }

}
