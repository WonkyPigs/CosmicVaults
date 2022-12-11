package dev.wonkypigs.cosmicvaults.Listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import dev.wonkypigs.cosmicvaults.Commands.VaultsCommand;
import dev.wonkypigs.cosmicvaults.Handlers.VaultHandler;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class VaultMenuListener implements Listener {

    @EventHandler
    public void onMenuClick(InventoryClickEvent e) {
        if (e.getView().getTitle().contains("&d&lY&5&lo&d&lu&5&lr &d&lV&5&la&d&lu&5&ll&d&lt&5&ls&r | Page ".replace("&", "§"))) {
            e.setCancelled(true);
            if (e.getCurrentItem() != null) {
                String title = e.getView().getTitle();
                int currpage = Integer.parseInt(title.substring(title.indexOf("Page ") + 5));
                Player player = (Player) e.getWhoClicked();

                if (e.getCurrentItem().getType() == Material.GREEN_WOOL) {
                    currpage++;
                    VaultsCommand.vaultMenuFiller(currpage, player);
                }
                else if (e.getCurrentItem().getType() == Material.RED_WOOL) {
                    currpage--;
                    VaultsCommand.vaultMenuFiller(currpage, player);
                }
                else if (e.getCurrentItem().getType() == Material.DIAMOND) {
                    VaultHandler.createNewVault(player, currpage);
                }
                else if (e.getCurrentItem().getType() == Material.ENDER_CHEST) {
                    currpage--;
                    VaultHandler.openVault(player, (e.getSlot()-8) + currpage*27);
                }
            }
        }
        else if (e.getView().getTitle().contains("Vault ")) {
            if (e.getCurrentItem() != null) {
                Player player = (Player) e.getWhoClicked();
                if (e.getCurrentItem().getType() == Material.BARRIER) {
                    e.setCancelled(true);
                    VaultsCommand.vaultMenuFiller(1, player);
                } else if (e.getCurrentItem().getType() == Material.GRAY_STAINED_GLASS_PANE) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onMenuClose(InventoryCloseEvent e) {
        if (e.getView().getTitle().contains("Vault ")) {
            Inventory inv = e.getInventory();
            int id = Integer.parseInt(e.getView().getTitle().substring(e.getView().getTitle().indexOf("Vault ") + 6));
            VaultHandler.saveVault((Player) e.getPlayer(), inv, id);
        }
    }
}
