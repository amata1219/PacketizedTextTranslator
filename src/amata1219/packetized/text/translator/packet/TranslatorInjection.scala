package amata1219.packetized.text.translator.packet

import amata1219.xeflection.Reflect
import io.netty.channel.{Channel, ChannelPipeline}
import net.minecraft.server.v1_16_R2.EntityPlayer
import org.bukkit.entity.Player

object TranslatorInjection {

  val identifier: String = "PacketizedTextTranslator"

  def applyTo(player: Player): Unit = {
    val pipeline: ChannelPipeline = player.pipeline
    if (pipeline.get(identifier) == null) pipeline.addBefore("packet_handler", identifier, new SendingPacketHandler(player))
  }

  def unapplyTo(player: Player): Unit = {
    val pipeline: ChannelPipeline = player.pipeline
    if (pipeline.get(identifier) != null) pipeline.remove(identifier)
  }

  implicit class XPlayer(player: Player) {
    def pipeline: ChannelPipeline = Reflect.on(player)
      .call("getHandle")
      .field("playerConnection")
      .field("networkManager")
      .field("channel")
      .call("pipeline")
      .as[ChannelPipeline]
  }

}
