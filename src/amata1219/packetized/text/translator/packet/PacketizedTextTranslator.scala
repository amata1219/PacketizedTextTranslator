package amata1219.packetized.text.translator.packet


import amata1219.xeflection.{AnyReflected, NetMinecraftServer, Reflect}

sealed trait PacketizedTextTranslator {
  def text(packet: AnyReflected): String
  def apply(packet: AnyReflected, translated: String): Unit
}
object PacketizedTextTranslator {
  case object ChatPacketizedTextTranslator extends PacketizedTextTranslator {
    val IChatBaseComponent: Class[_] = Class.forName(s"$NetMinecraftServer.IChatBaseComponent")

    override def text(packet: AnyReflected): String = {
      val component = packet.get[Any]("a")
      Reflect.on(IChatBaseComponent, component)
        .call("getString")
        .as[String]
    }

    override def apply(packet: AnyReflected, translated: String): Unit = {
      val component = Reflect.on(s"$NetMinecraftServer.ChatMessage")
        .create(translated)
        .as[Any]
      packet.set("a", component)
    }
  }
}