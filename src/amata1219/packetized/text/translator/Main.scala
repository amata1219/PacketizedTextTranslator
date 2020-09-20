package amata1219.packetized.text.translator

import amata1219.packetized.text.translator.command.TranslationCommand
import amata1219.packetized.text.translator.configuration.Configuration
import amata1219.packetized.text.translator.listener.PlayerListener
import org.bukkit.event.HandlerList
import org.bukkit.plugin.java.JavaPlugin

class Main extends JavaPlugin() {

  Main.instance = this

  val playerDataConfig = new Configuration("player-data.yml")
  val parallelTranslationConfig = new Configuration("parallel-translation.yml")

  playerDataConfig.create()
  parallelTranslationConfig.create()

  override def onEnable(): Unit = {
    getCommand("translation").setExecutor(TranslationCommand)
    getServer.getPluginManager.registerEvents(PlayerListener, this)
  }

  override def onDisable(): Unit = {
    HandlerList.unregisterAll(this)
    playerDataConfig.save()
  }

}

object Main {
  var instance: Main = _
}
