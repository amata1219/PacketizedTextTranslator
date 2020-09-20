package amata1219.packetized.text.translator.configuration

import java.io.{File, IOException, InputStreamReader}
import java.nio.charset.StandardCharsets

import amata1219.packetized.text.translator.Main
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

  def save(): Unit = if (value != null) {
    try {
      value.save(file)
    } catch {
      case ex: IOException =>
        println(s"Could not save config to $fileName")
        ex.printStackTrace()
    }
  }

  def config: FileConfiguration = {
    if (value == null) reload()
    value
  }

}
