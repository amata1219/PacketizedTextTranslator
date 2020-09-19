package amata1219.packetized.text.translator

import org.bukkit.plugin.java.JavaPlugin

class Main extends JavaPlugin() {

  Main.instance = this

  val config = new Configuration("config.yml")
  val translation = new Configuration("translation.yml")

  override def onEnable(): Unit = {

  }

  override def onDisable(): Unit = {

  }

}

object Main {

  var instance: Main = _

}
