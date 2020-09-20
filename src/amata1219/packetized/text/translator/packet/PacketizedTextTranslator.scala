package amata1219.packetized.text.translator.packet

import java.lang.reflect.{InvocationHandler, Method, Proxy}

import amata1219.xeflection.{AnyReflected, NetMinecraftServer, Reflect}

sealed trait PacketizedTextTranslator {
  def text(packet: AnyReflected): String
  def apply(packet: AnyReflected, translated: String): Unit
}
object PacketizedTextTranslator {
  case object ChatPacketizedTextTranslator extends PacketizedTextTranslator {
    override def text(packet: AnyReflected): String = {
      val component = packet.get[Any]("a")
      Reflect.on(component)
        .call("getText")
        .as[String]
    }

    override def apply(packet: AnyReflected, translated: String): Unit = {
      val component = packet.get[Any]("a")
      val handler = new InvocationHandler {
        override def invoke(proxy: Any, method: Method, args: Array[AnyRef]): AnyRef = {
          if (method.getName == "getText") return translated
          method.invoke(component, args)
        }
      }

      val clazz: Class[_] = Class.forName(s"$NetMinecraftServer.IChatBaseComponent")
      val translatedComponent: Any = Proxy.newProxyInstance(
        clazz.getClassLoader,
        Array(clazz),
        handler
      )

      packet.set("a", translatedComponent)
    }
  }
}