# 🧙‍♂️ RPG XP Plugin

A lightweight Minecraft plugin that displays players' XP levels dynamically bellow their heads and provides simple admin controls to enable, disable, or change the color of the XP display.

Um plugin leve para Minecraft que exibe o nível de XP dos jogadores abaixo de suas cabeças, com comandos administrativos simples para ativar, desativar ou mudar a cor da exibição.

---

## 🌍 Overview / Visão Geral

This plugin was created to make RPG-style servers more immersive.  
Each player’s XP level is shown above their character, and administrators can easily toggle or customize it via commands.

Este plugin foi criado para tornar servidores com estilo RPG mais imersivos.  
O nível de XP de cada jogador é exibido acima do personagem, e administradores podem ativar, desativar ou personalizar via comandos.

---

## ⚙️ Installation / Instalação

1. Download the plugin `.jar` file and place it inside your server’s `plugins/` folder.  
2. Restart or reload your Minecraft server.  
3. You should see `[RPG XP Plugin has been enabled!]` in the console.

1. Baixe o arquivo `.jar` do plugin e coloque na pasta `plugins/` do seu servidor.  
2. Reinicie ou recarregue o servidor.  
3. Você verá `[RPG XP Plugin has been enabled!]` no console.

---

## 🧾 Commands / Comandos

| Command / Comando | Description (EN) | Descrição (PT-BR) |
|--------------------|------------------|--------------------|
| `/rpgxp-enabledisable enable <nickname>` | Enables the XP display for a player | Ativa a exibição de XP para um jogador |
| `/rpgxp-enabledisable disable <nickname>` | Disables the XP display for a player | Desativa a exibição de XP para um jogador |
| `/rpgxp-changecolor changecolor <color>` | Changes the XP display color (e.g., RED, GREEN, GOLD) | Altera a cor da exibição de XP (ex: RED, GREEN, GOLD) |

> 💡 **Tip / Dica:** Valid color names are the same as [Minecraft Chat Colors](https://minecraft.wiki/w/Formatting_codes).

---

## 🔒 Permissions / Permissões

| Permission | Description (EN) | Descrição (PT-BR) |
|-------------|------------------|--------------------|
| `rpgxp.admin` | Allows a player to use all `/rpgxp` admin commands | Permite ao jogador usar todos os comandos administrativos `/rpgxp` |

---

## 🧰 Granting Permissions / Dando Permissões

If you use **LuckPerms**, run this command in the server console or in-game (as operator):

Se você usa **LuckPerms**, execute este comando no console ou no jogo (como operador):

```bash
lp user <playername> permission set rpgxp.admin true
