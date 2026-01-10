# BetterRcon
BetterRcon is a Minecraft mod for servers that provides a encrypted RCON server.
Messages between client and server are encrypted using AES-256.
The AES-256 key is send using RSA so no one between client and server can read messages.

## Config
 - Server port
 - Server password
 - Required permission level to use the `/betterrcon` command
 - Virtual RCON player
   - Permission level of the RCON player
   - Name of the RCON player

## Commands
 - `/betterrcon info`
   - Status of the server
   - Server port
   - RCON player name
 - `/betterrcon start`
   - Starts the server
 - `/betterrcon stop`
   - Stops the server