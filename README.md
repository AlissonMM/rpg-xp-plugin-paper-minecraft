# 🧙‍♂️ RPG XP Plugin

A lightweight Minecraft plugin that displays players' XP levels dynamically below their heads and provides simple admin controls to enable, disable, or change the color of the XP display.

Um plugin leve para Minecraft que exibe o nível de XP dos jogadores abaixo de suas cabeças, com comandos administrativos simples para ativar, desativar ou mudar a cor da exibição.



## 🥸 Overview / Visão Geral

This plugin was created to make RPG-style Minecraft servers more immersive.  
Born from a need within the **TKCraft** server — a Brazilian Minecraft world that seeks to recreate the **First Age** from the tales of **J.R.R. Tolkien**, inspired by *The Silmarillion* — this plugin brings a touch of lore and realism to every player.  
Each player’s XP level is displayed below their nickname, allowing administrators to easily enable, disable, or customize the display through intuitive commands.

Este plugin foi criado para tornar servidores de Minecraft com estilo RPG mais imersivos.  
Ele nasceu da necessidade do servidor **TKCraft**, um servidor brasileiro de Minecraft que busca recriar a **Primeira Era** dos contos de **J.R.R. Tolkien**, inspirada no livro *O Silmarillion*.  
O nível de XP de cada jogador é exibido abaixo do apelido do personagem, e os administradores podem facilmente ativar, desativar ou personalizar essa exibição através de comandos simples e diretos.

## ⚙️ Requirements / Requisitos

| Component | Version | Description |
|------------|----------|-------------|
| **Minecraft** | 1.21.4 | Compatible with PaperMC 1.21.4 |
| **PaperMC** | 1.21.4 | Plugin framework |
| **Java** | 21 | Required runtime |



## ⚙️ Installation / Instalação

1. Use the command mvn clean package to generate the .jar file
2. Download the plugin `.jar` file and place it inside your server’s `plugins/` folder.  
3. Restart or reload your Minecraft server.  
4. You should see `[RPG XP Plugin has been enabled!]` in the console.

1. Rode o comando mvn clean package para gerar o .jar
2. Baixe o arquivo `.jar` do plugin e coloque na pasta `plugins/` do seu servidor.  
3. Reinicie ou recarregue o servidor.  
4. Você verá `[RPG XP Plugin has been enabled!]` no console.



## 🧾 Commands / Comandos

| Command / Comando | Description (EN) | Descrição (PT-BR) |
|--------------------|------------------|--------------------|
| `/rpgxp-enabledisable enable <nickname>` | Enables the XP display for a player | Ativa a exibição de XP para um jogador |
| `/rpgxp-enabledisable disable <nickname>` | Disables the XP display for a player | Desativa a exibição de XP para um jogador |
| `/rpgxp-changecolor changecolor <color>` | Changes the XP display color (e.g., RED, GREEN, GOLD) | Altera a cor da exibição de XP (ex: RED, GREEN, GOLD) |

> 💡 **Tip / Dica:** Valid color names are the same as [Minecraft Chat Colors](https://minecraft.wiki/w/Formatting_codes).



## 🔒 Permissions / Permissões

| Permission | Description (EN) | Descrição (PT-BR) |
|-------------|------------------|--------------------|
| `rpgxp.admin` | Allows a player to use all `/rpgxp` admin commands | Permite ao jogador usar todos os comandos administrativos `/rpgxp` |



## 🧰 Granting Permissions / Dando Permissões

If you use **LuckPerms**, run this command in the server console or in-game (as operator):

Se você usa **LuckPerms**, execute este comando no console ou no jogo (como operador):

```bash
lp user <playername> permission set rpgxp.admin true
```




## 💻 Developer / Desenvolvedor  
**Futharkr (Alisson Mayer Medeji)**  



## 🌐 Server Inspiration  
**TKCraft** — A Brazilian RPG server inspired by Tolkien’s *The Silmarillion*  
**TKCraft** — Um servidor brasileiro de RPG inspirado em *O Silmarillion*, de Tolkien  

---

## ⚒️ Framework  
**PaperMC**
