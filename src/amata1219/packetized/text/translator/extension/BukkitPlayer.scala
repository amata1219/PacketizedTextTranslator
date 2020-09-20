package amata1219.packetized.text.translator.`extension`

import amata1219.packetized.text.translator.Main
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.entity.Player

object BukkitPlayer {
  implicit class XPlayer(val player: Player) {
    private def config: FileConfiguration = Main.instance.config.config
    private val stringizedUniqueId: String = player.getUniqueId.toString
    def isUsingPacketizedTextTranslation: Boolean = config.getBoolean(stringizedUniqueId)
    def setIsUsingPacketizedTextTranslation(flag: Boolean): Unit = config.set(stringizedUniqueId, flag)
  }
}
