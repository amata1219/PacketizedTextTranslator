package amata1219.packetized.text.translator

import java.io.{File, InputStream, InputStreamReader, Reader}
import java.nio.charset.StandardCharsets

import org.bukkit.configuration.file.{FileConfiguration, YamlConfiguration}

class Configuration(val fileName: String) {

  private val plugin: Main = Main.instance
  private val file: File = new File(plugin.getDataFolder, fileName)
  private var value: FileConfiguration = _

  def create(): Unit = if (!file.exists()) {
    plugin.saveResource(fileName, false)
  }

  def reload(): Unit = {
    value = YamlConfiguration.loadConfiguration(file)
    plugin.getResource(fileName) match {
      case null =>
      case input =>
        val reader = new InputStreamReader(input, StandardCharsets.UTF_8)
        val default: YamlConfiguration = YamlConfiguration.loadConfiguration(reader)
        value.setDefaults(default)
    }
  }

  def config: FileConfiguration = {
    if (value == null) reload()
    value
  }

}
