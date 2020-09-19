package amata1219.packetized.text.translator

import org.bukkit.plugin.java.JavaPlugin

class Main extends JavaPlugin() {

  Main.INSTANCE = this

  override def onEnable(): Unit = {

  }

  override def onDisable(): Unit = {

  }

  object Main {

    var INSTANCE: Main = _

  }

}
