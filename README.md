# CustomRankJoinMessagesNK

Mensajes personalizados de entrada y salida, además de sonidos por rango para **NukkitPetteriM1Edition**, según el grupo principal del jugador en **LuckPerms**.

Desarrollado por **BrunoJaegerK**.

## ✨ Características

- Mensajes personalizados al entrar y salir, configurados por grupo principal de LuckPerms.
- Sonido de entrada y sonido de salida configurables para cada rango.
- Los sonidos los escuchan todos los demás jugadores conectados, incluso si están en otros mundos. El jugador que entra o sale no escucha su propio sonido.
- Activación o desactivación independiente de los mensajes de entrada y salida.
- Activación o desactivación independiente de cada sonido por rango y evento.
- Placeholder `{name}` para mostrar el nombre del jugador.
- Comparación de nombres de grupos sin distinguir mayúsculas y minúsculas.
- LuckPerms es opcional: si no está disponible, se utilizan las configuraciones `default`.
- Los grupos que no tengan una configuración propia utilizan los valores de `default`.

## 📋 Requisitos

- NukkitPetteriM1Edition.
- Java 17.
- LuckPerms es opcional y solo se necesita para utilizar configuraciones específicas por rango.

## 📥 Instalación

1. Descarga el JAR más reciente desde los artefactos de **Actions** o desde **Releases** del repositorio.
2. Coloca el archivo JAR en la carpeta `plugins` de tu servidor.
3. Inicia el servidor una vez para generar `plugins/CustomRankJoinMessagesNK/config.yml`.
4. Edita la configuración a tu gusto y reinicia el servidor.

## ⚙️ Configuración

Ejemplo de configuración:

~~~yaml
ModifyJoinMessages: true
ModifyLeaveMessages: true

joinMessages:
  owner: "§l§7[§a+§7] [§cOwner§7] {name}"
  default: "§l§7[§a+§7] {name}"

leaveMessages:
  owner: "§l§7[§c-§7] [§cOwner§7] {name}"
  default: "§l§7[§c-§7] {name}"

joinSounds:
  owner:
    enabled: true
    sound: "random.levelup"
  vip:
    enabled: true
    sound: "note.pling"
  default:
    enabled: true
    sound: "random.pop"

leaveSounds:
  owner:
    enabled: true
    sound: "random.totem"
  vip:
    enabled: true
    sound: "random.pop2"
  default:
    enabled: true
    sound: "random.pop"
~~~

### 🔊 Configuración de sonidos

Cada rango dentro de `joinSounds` y `leaveSounds` tiene dos opciones:

- `enabled`: acepta `true` o `false`. Usa `false` para desactivar el sonido de ese rango en ese evento.
- `sound`: identificador del sonido. Debe escribirse exactamente como aparece en la lista de sonidos.

Puedes asignar identificadores diferentes a cada rango y evento. Si un rango no tiene una entrada configurada, se utilizará el sonido de `default`.

El plugin envía el sonido en la posición de cada jugador que lo escucha. Así, todos los demás jugadores conectados pueden oírlo sin importar la distancia o el mundo en el que estén. Se excluye al jugador que provoca el evento de entrada o salida.

### 🎵 Identificadores de sonidos disponibles

Estos son algunos ejemplos de identificadores incluidos en la enumeración `cn.nukkit.level.Sound` de la versión **release de NukkitPetteriM1Edition**. Escríbelos exactamente igual, respetando puntos y guiones bajos.

**Notificaciones y efectos cortos**

~~~yaml
random.levelup
random.orb
random.pop
random.pop2
random.totem
random.toast
random.click
random.fizz
random.break
random.burp
random.splash
random.potion.brewed
~~~

**Sonidos musicales y notas**

~~~yaml
note.pling
note.bell
note.cow_bell
note.enderdragon
~~~

**Fuegos artificiales, portales y efectos especiales**

~~~yaml
firework.blast
firework.large_blast
firework.launch
firework.shoot
firework.twinkle
portal.portal
portal.travel
portal.trigger
raid.horn
~~~

**Bloques y ambiente**

~~~yaml
block.bell.hit
block.enchanting_table.use
block.end_portal.spawn
block.end_portal_frame.fill
beacon.activate
beacon.deactivate
beacon.power
conduit.activate
ambient.cave
ambient.weather.lightning.impact
~~~

**Mobs y criaturas**

~~~yaml
mob.allay.hurt
mob.blaze.shoot
mob.cat.meow
mob.creeper.say
mob.enderdragon.growl
mob.endermen.portal
mob.ghast.scream
mob.warden.roar
mob.wither.spawn
mob.villager.yes
mob.villager.no
mob.zombie.say
mob.piglin.angry
mob.ravager.roar
mob.wolf.growl
~~~

**Objetos y combate**

~~~yaml
item.trident.throw
item.trident.hit
item.trident.thunder
item.trident.riptide_1
item.trident.riptide_2
item.trident.riptide_3
crossbow.shoot
crossbow.loading.start
crossbow.loading.end
~~~

Esta lista es una selección de ejemplos, no el catálogo completo. La clase `Sound.java` de PM1E contiene muchos más identificadores, incluidos sonidos de bloques, criaturas, objetos, ambiente, música e interfaz.

➡️ [Consultar el catálogo completo de sonidos de NukkitPetteriM1Edition](https://github.com/PetteriM1/NukkitPetteriM1Edition/blob/release/src/main/java/cn/nukkit/level/Sound.java).

**Importante sobre compatibilidad:** el identificador debe existir en la versión del cliente Bedrock y en el paquete de recursos que utiliza cada jugador. Aunque el identificador sea válido en el servidor, el cliente podría no reproducirlo si no reconoce ese evento de sonido. Este plugin no registra automáticamente sonidos personalizados de un resource pack.

### 📝 Notas generales de configuración

- Las claves de los grupos deben coincidir con los nombres de los grupos principales configurados en LuckPerms.
- La comparación de los nombres de grupo no distingue entre mayúsculas y minúsculas.
- `{name}` se reemplaza por el nombre del jugador.
- Si LuckPerms no está instalado, el usuario todavía no se ha cargado o el grupo no tiene una entrada configurada, se utilizará `default`.
- Cambia `ModifyJoinMessages` o `ModifyLeaveMessages` a `false` para conservar el mensaje predeterminado del servidor en ese evento. La configuración de sonidos funciona independientemente de estas opciones.
- Puedes dejar vacío el mensaje de un grupo si quieres que ese rango no muestre ningún mensaje personalizado.

## 🛠️ Compilación

Necesitas Maven y Java 17. Ejecuta:

~~~bash
mvn clean package
~~~

El archivo JAR se generará en:

~~~text
target/CustomRankJoinMessagesNK-1.0.0.jar
~~~

## 🐛 Soporte y reportes

Si encuentras un error o quieres solicitar una función, crea un reporte en [GitHub Issues](https://github.com/BrunoJaegerK/CustomRankJoinMessagesNK/issues).

## 📄 Licencia

Este proyecto está distribuido bajo la **Licencia Pública General de GNU, versión 3 o cualquier versión posterior (GNU GPL-3.0-or-later)**.

Puedes usar, estudiar, modificar y redistribuir el proyecto de acuerdo con los términos de esta licencia. Las versiones modificadas que distribuyas deben conservar la licencia GPL y poner a disposición el código fuente correspondiente.

Consulta el archivo [LICENSE](LICENSE) para leer el texto completo de la licencia.
