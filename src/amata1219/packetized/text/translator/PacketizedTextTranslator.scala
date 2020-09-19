package amata1219.packetized.text.translator

import java.lang.reflect.{InvocationHandler, Method, Proxy}

import amata1219.xeflection.{AnyReflected, NetMinecraftServer, Reflect}

sealed trait PacketizedTextTranslator {
  def text(packet: AnyReflected): String
  def apply(packet: AnyReflected, translator: String => String): Unit
}

case object ChatPacketizedTextTranslator extends PacketizedTextTranslator {
  def text(packet: AnyReflected): String = {
    val component = packet.get[Any]("a")
    Reflect.on(component)
      .call("getText")
      .as[String]
  }

  def apply(packet: AnyReflected, translated: String): Unit = {
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