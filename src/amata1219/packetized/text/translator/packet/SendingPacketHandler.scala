package amata1219.packetized.text.translator.packet

import amata1219.packetized.text.translator.Main
import amata1219.packetized.text.translator.`extension`.BukkitPlayer.XPlayer
import amata1219.packetized.text.translator.packet.PacketizedTextTranslator.ChatPacketizedTextTranslator
import amata1219.xeflection.{AnyReflected, Reflect}
import io.netty.channel.{ChannelDuplexHandler, ChannelHandlerContext, ChannelPromise}
import org.bukkit.entity.Player

class SendingPacketHandler(val player: Player) extends ChannelDuplexHandler {

  override def write(context: ChannelHandlerContext, packet: Any, promise: ChannelPromise): Unit = {
    if (player.isUsingPacketizedTextTranslation) translate(packet)
    super.write(context, packet, promise)
  }

  private def translate(packet: Any): Unit = {
    val translator = packet.getClass.getSimpleName match {
      case "PacketPlayOutChat" => ChatPacketizedTextTranslator
      case _ => return
    }

    val reflected: AnyReflected = Reflect.on(packet)
    val text: String = translator.text(reflected)

    Main.instance.parallelTranslationConfig.config.getString(text) match {
      case null =>
      case translated => translator.apply(reflected, translated)
    }
  }

}
