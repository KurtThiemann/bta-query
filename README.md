# BTA Query Mod

A mod that adds support for the query protocol to Better than Adventure.

This implementation encodes strings as UTF-8 instead of ASCII to avoid [MC-231035](https://bugs.mojang.com/browse/MC-231035).
Additionally, null-bytes are stripped from encoded strings to avoid [MC-221987](https://bugs.mojang.com/browse/MC-221987).

## Usage
After installing the mod on a [Babric](https://bta.miraheze.org/wiki/Modding) server,
you can enable the query protocol in the `server.properties` file.
```properties
enable-query=true
query.port=25565
```
