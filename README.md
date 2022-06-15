# NyoomCarts
NyoomCarts is a Spigot plugin that brings speed modification and more to minecarts.

## Usage
NyoomCarts uses signs in order to affect nearby minecarts. It currently supports two types of signs: speed signs and launcher signs.

Players must have the permission `nyoomcarts.sign` in order to place NyoomCarts signs.
All NyoomCarts signs start with the first line: `[nyoom]`
### Speed signs
Speed signs change the maximum speed of the minecart. Speed signs use a multiplier of the vanilla minecart speed (i.e. `1` is normal speed, `2` is 2x the normal speed, etc.)
Speed signs must be formatted like the following:
```
[nyoom]
(speed multiplier)


```
Speed signs need to be placed beside or below the rail (green wool marks where signs can be placed):
[![](https://i.imgur.com/rOpHVEK.png)](https://i.imgur.com/rOpHVEK.png)
### Launcher signs
Launcher signs launch the minecart in a certain cardinal direction when the sign is powered with a redstone signal.
Launcher signs must be formatted like the following:
```
[nyoom]
launch
(cardinal direction)

```
Launcher signs need to be powered towards the sign face,  like so:
[![](https://i.imgur.com/lo5Rrwu.png)](https://i.imgur.com/lo5Rrwu.png)

If the sign is placed correctly, the first line will turn bold and green to signify that the sign is valid.

### Configuration
Configuration is very simple for the time being.
`max-speed-multiplier` sets the maximum speed. If the speed is greater on the sign, the cart will be clamped to the speed specified in the config file.
```yml
speed:
  max-speed-multiplier: 3
# Do not edit!
file-version: 1
```

## Acknowledgments
NyoomCarts uses BoostedYAML and bStats. See the [NOTICE](https://github.com/0b10000/NyoomCarts/blob/main/NOTICE) for more details.
NyoomCarts is licensed under the [GNU GPL v3](https://github.com/0b10000/NyoomCarts/blob/main/LICENSE).
Inspired by [MinecartSpeedPlus](https://www.spigotmc.org/resources/minecart-speed-plus.69639/)

![Plugin stats](https://bstats.org/signatures/bukkit/NyoomCarts.svg)
