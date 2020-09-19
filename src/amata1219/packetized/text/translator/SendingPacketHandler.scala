package amata1219.packetized.text.translator

import amata1219.xeflection.Reflect
import io.netty.channel.{ChannelDuplexHandler, ChannelHandlerContext, ChannelPromise}
import org.bukkit.entity.Player

abstract class SendingPacketHandler(val player: Player) extends ChannelDuplexHandler {

  override def write(context: ChannelHandlerContext, packet: Any, promise: ChannelPromise): Unit = {
    val packetName = Reflect.on(packet)
      .call("getClass")
      .call("getSimpleName")
      .as[String]

    translate(packet, packetName)

    super.write(context, packet, promise)
  }

  def translate(packet: Any, packetName: String): Unit

}
