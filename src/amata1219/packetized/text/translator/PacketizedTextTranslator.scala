package amata1219.packetized.text.translator

import java.lang.reflect.{InvocationHandler, Method, Proxy}

import amata1219.xeflection.{AnyReflected, NetMinecraftServer, Reflect}

sealed trait PacketizedTextTranslator {
  def apply(packet: AnyReflected, translator: String => String): Unit
}

case object ChatPacketizedTextTranslator extends PacketizedTextTranslator {
  override def apply(packet: AnyReflected, translator: String => String): Unit = {
    val component = packet.get[Any]("a")

    val text = Reflect.on(component).call("getText").as[String]
    val translated: String = translator(text)

    val handler = new InvocationHandler {
      override def invoke(proxy: Any, method: Method, args: Array[AnyRef]): AnyRef = {
        if (method.getName == "getText") return translated
        method.invoke(component, args)
      }
    }

    val IChatBaseComponentClass: Class[_] = Class.forName(s"$NetMinecraftServer.IChatBaseComponent")
    val translatedComponent: Any = Proxy.newProxyInstance(
      IChatBaseComponentClass.getClassLoader,
      Array(IChatBaseComponentClass),
      handler
    )

    packet.set("a", translatedComponent)
  }
}