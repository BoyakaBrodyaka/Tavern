# Tavern - Шпаргалка по координатам и настройкам

## NPC (очередь, следование, уход)

| Что | Где | Сейчас |
|-----|-----|--------|
| Координаты спавна NPC | NpcRegistrationCoordinate -> SPAWN_X/Y/Z | -3.0, 61.0, 21.0 |
| Координаты цели в очереди | NpcRegistrationCoordinate -> TARGET_X/Y/Z | -2.532, 61.0, 17.395 |
| Зона клика по NPC | NpcRegistrationCoordinate -> ZONE_MIN/MAX | -4.178, 60.0, 17.7 / -1.2, 63.0, 17.81 |
| Координаты ухода | NpcDepartureCoordinate -> X/Y/Z | -2.0, 61.0, 23.0 |
| Точка исчезновения | NpcDepartureCoordinate -> DISAPPEAR_X/Y/Z | 0.0, -100.0, 0.0 |
| Время до ухода | NpcDepartureCoordinate -> LEAVE_TIMEOUT_MS | 20000L |
| Расстояние "дошёл" до ухода | NpcDepartureCoordinate -> ARRIVE_DISTANCE | 0.15 |
| Скорость ходьбы | NpcRegistrationSettings -> walkSpeed | 0.04 |
| Скорость следования | NpcRegistrationSettings -> followSpeed | 0.22 |
| Дистанция следования | NpcRegistrationSettings -> followDistance | 1.5 |
| Радиус "дошёл" | NpcRegistrationSettings -> reachDistance | 0.15 |
| Максимум NPC в очереди | NpcRegistrationQueue.getMax() | 5 |
| Шаг очереди | NpcRegistrationQueue -> STEP_X/Y/Z | 0.0, 0.0, 1.0 |
| Направление "север" | NpcRegistrationFacing -> NORTH_YAW | 180.0F |
| Скины NPC | NpcSkinRegistry | - |
| Имена NPC | NpcNameRegistry | - |
| Типы NPC | NpcTypeRegistry | witch, orc, goblin |
| Интервал спавна NPC | NpcRegistrationTask -> tick % 200 | 200 тиков (10 сек) |
| Интервал проверки ухода | NpcDepartureTask -> runTaskTimer(..., 20L) | 1 сек |

## Стол и стулья

| Что | Где | Сейчас |
|-----|-----|--------|
| Границы стола | DiningCoordinate -> TABLE_MIN/MAX | 3-5, 60-62, 2-4 |
| Координаты стула 1 | DiningChairCoordinate -> CHAIR_1_X/Y/Z | 4.5, 61.0, 1.5 |
| Координаты стула 2 | DiningChairCoordinate -> CHAIR_2_X/Y/Z | 6.5, 61.0, 3.5 |
| Координаты стула 3 | DiningChairCoordinate -> CHAIR_3_X/Y/Z | 4.5, 61.0, 5.5 |
| Координаты стула 4 | DiningChairCoordinate -> CHAIR_4_X/Y/Z | 2.5, 61.0, 3.5 |
| Радиус клика по стулу | DiningChairListener -> findNearby(..., 3.0) | 3.0 |
| Радиус клика по стулу (event) | DiningChairListener -> findNearby(..., 1.5) | 1.5 |
| Индексы стульев | DiningChairIndex | 1, 2, 3, 4 |

## Регистратура (зона 1/2)

| Что | Где | Сейчас |
|-----|-----|--------|
| Границы зоны | RegistrationCoordinate -> MIN_X/Y/Z, MAX_X/Y/Z | см. enum |
| Миры регистратуры | RegistrationWorld -> MAP("_map") | _map |
| Радиус игрока в зоне | RegistrationManager.isPlayerInZone() | через зону |
| Радиус NPC в зоне | NpcRegistrationManager.isInZone() | через зону |

## Голограммы над регистратурой

| Что | Где | Сейчас |
|-----|-----|--------|
| Координаты голограммы | RegistrationHologramCoordinate -> X/Y/Z | см. enum |
| Расстояние между строками | RegistrationHologramCoordinate -> LINE_SPACING | см. enum |
| Текст верхней строки | RegistrationHologramManager -> LINE_TOP | "Регистратура" |
| Текст нижней строки | RegistrationHologramManager -> LINE_BOTTOM | "1" |

## Скорборд

| Что | Где | Сейчас |
|-----|-----|--------|
| Строки скорборда | BoardLayout.apply() | деньги, налог, энергия |
| Титул скорборда | BoardLayout.apply() -> setTitle | "[Таверна]" |
| Лимит видимых символов | Board.split() -> >= 16 | 16 |
| Интервал обновления | BoardUpdater -> runTaskTimer(..., 20L) | 1 сек |

## Экономика и энергия

| Что | Где | Сейчас |
|-----|-----|--------|
| Стартовые деньги | Account -> this.money | 100.0 |
| Максимум энергии | EnergyFactory.createDefault() -> EnergySettings | 100 |
| Трата энергии за тик | EnergySettings -> sprintCost | 1 |
| Восстановление за тик | EnergySettings -> regenAmount | 1 |
| Порог спринта | Energy.canSprint() -> value >= 10 | 10 |
| Интервал энерготаска | EnergyModule.startTask() -> runTaskTimer(..., 2L) | 0.1 сек |
| Голод при энергии >= 10 | EnergyTask | 20 |
| Голод при энергии < 10 | EnergyTask | 5 |
| БД: строка подключения | DatabaseManager -> connectionString | mongodb://localhost:27017/Tavern |
| БД: имя базы | DatabaseManager -> databaseName | Tavern |
| БД: коллекция сохранений | core.DatabaseManager -> collectionName | saves |
| БД: коллекция экономики | commerce.DatabaseManager -> collectionName | commerce |

## Плагины и зависимости

| Что | Где | Сейчас |
|-----|-----|--------|
| Имя ядра | core/plugin.yml -> name | TavernCoreBP |
| Имя экономики | commerce/plugin.yml -> name | TavernCommerceBP |
| Имя HUD | hud/plugin.yml -> name | TavernHUDBP |
| Имя готовки | cuisine/plugin.yml -> name | TavernCuisineBP |
| Имя операций | operations/plugin.yml -> name | TavernOperationsBP |
| Имя прогрессии | progression/plugin.yml -> name | TavernProgressionBP |
| Зависимости HUD | hud/plugin.yml -> depend | TavernCoreBP, TavernCommerceBP |
| Зависимости операций | operations/plugin.yml -> depend | TavernCoreBP, TavernCommerceBP, TavernCuisineBP |
| Версия всех модулей | Корневой build.gradle -> version | 1.0.0 |
| Имя JAR | Корневой build.gradle -> archiveBaseName | Tavern-{Module}BP |

## Утилиты

| Что | Где | Сейчас |
|-----|-----|--------|
| Формат чисел (1.2K, 5M) | FormatNumber | - |
| Цвета (& -> §) | Color | - |
| Парсинг чисел (100M) | Number | - |
| Сообщения | MessageUtil | - |

## Быстрая навигация

- Координаты NPC: NpcRegistrationCoordinate
- Координаты ухода: NpcDepartureCoordinate
- Координаты стульев: DiningChairCoordinate
- Границы стола: DiningCoordinate
- Скорости NPC: NpcRegistrationSettings
- Зона регистратуры: RegistrationCoordinate
- Скорборд: BoardLayout
- Голограмма: RegistrationHologramCoordinate
- Деньги: Account
- Энергия: EnergySettings
- БД: DatabaseManager
- Скины NPC: NpcSkinRegistry
- Имена NPC: NpcNameRegistry
- Типы NPC: NpcTypeRegistry