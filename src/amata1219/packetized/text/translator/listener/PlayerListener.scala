package amata1219.packetized.text.translator.listener

import amata1219.packetized.text.translator.packet.TranslatorInjection
import org.bukkit.event.player.{PlayerJoinEvent, PlayerQuitEvent}
import org.bukkit.event.{EventHandler, Listener}

object PlayerListener extends Listener {

  @EventHandler
  def on(event: PlayerJoinEvent): Unit = TranslatorInjection.applyTo(event.getPlayer)

  @EventHandler
  def on(event: PlayerQuitEvent): Unit = TranslatorInjection.unapplyTo(event.getPlayer)

}
