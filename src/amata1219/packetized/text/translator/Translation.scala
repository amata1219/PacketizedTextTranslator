package amata1219.packetized.text.translator

import org.bukkit.configuration.file.FileConfiguration

class Translation(val file: FileConfiguration) {

  def translate(text: String): String = file.getString(text)

}
