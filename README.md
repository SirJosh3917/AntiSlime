# AntiSlime
AntiSlime is a stupid, dead simple plugin designed only to eliminate slimes from your world, with 0 hassle.

## Usage

### /antislime
Toggles on/off whether or not slime spawning should be prevented (enabled) or allowed (disabled).

### /antislime <true/false>
Sets whether slime spawning should be prevented (true) or allowed (false)

## Building
```shell
gradle build
```

## Configuration
AntiSlime is configurable. An example of a configuration may look like the following:

```yaml
settings:
  permission_name: antislime
worlds:
  world: true
  nether: false
```

Under settings, contains global configuration information. `permission_name` refers to the name of the permission in Bukkit/Spigot/Paper that you must give to those who wish to use the `/antislime` command.

Under `worlds`, contains the configuration information for each world. If a given world is set to `true`, that means AntiSlime is **enabled** (slime spawning will be prevented). If agiven world is set to `false`, that means AntiSlime is **disabled** (slime spawning will be allowed).

## Roadmap
[ ] - Allow the console to toggle antislime in any designated world
[ ] - Configurable spawn rates of slimes, so that they don't dissapear completely.