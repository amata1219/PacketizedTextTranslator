package amata1219.packetized.text.translator.command

import amata1219.packetized.text.translator.`extension`.BukkitPlayer.XPlayer
import org.bukkit.ChatColor
import org.bukkit.command.{Command, CommandExecutor, CommandSender}
import org.bukkit.entity.Player

object TranslationCommand extends CommandExecutor {
  override def onCommand(sender: CommandSender, command: Command, label: String, args: Array[String]): Boolean = {
    sender match {
      case player: Player => execute(player)
      case _ => sender.sendMessage(s"${ChatColor.RED}ここでは実行出来ません。")
    }
    true
  }

  def execute(player: Player): Unit = {
    val newFlag: Boolean = !player.isUsingPacketizedTextTranslation
    player.setIsUsingPacketizedTextTranslation(newFlag)
    if (newFlag) player.sendMessage(s"${ChatColor.AQUA}翻訳を有効化しました！")
    else player.sendMessage(s"${ChatColor.RED}翻訳を無効化しました。")
  }
}
