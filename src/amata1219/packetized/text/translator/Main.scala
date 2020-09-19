package amata1219.packetized.text.translator

import org.bukkit.event.HandlerList
import org.bukkit.plugin.java.JavaPlugin

class Main extends JavaPlugin() {

  Main.instance = this

  val config = new Configuration("config.yml")
  val translation = new Configuration("translation.yml")

  config.create()
  translation.create()

  override def onEnable(): Unit = {
    getServer.getPluginManager.registerEvents(PlayerListener, this)
  }

  override def onDisable(): Unit = {
    HandlerList.unregisterAll(this)
  }

}

object Main {

  var instance: Main = _

}
