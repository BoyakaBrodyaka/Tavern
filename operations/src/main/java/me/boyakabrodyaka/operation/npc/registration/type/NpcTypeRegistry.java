package me.boyakabrodyaka.operation.npc.registration.type;

import me.boyakabrodyaka.operation.npc.registration.skin.NpcSkin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class NpcTypeRegistry {

    private final List<NpcType> types = new ArrayList<>();

    public NpcTypeRegistry() {
        this.types.add(createWitchType());
        this.types.add(createOrcType());
        this.types.add(createGoblinType());
    }

    public NpcType getRandomType() {
        return this.types.get(ThreadLocalRandom.current().nextInt(this.types.size()));
    }

    public List<NpcType> getAll() {
        return this.types;
    }

    private NpcType createWitchType() {
        List<NpcSkin> skins = Arrays.asList(
                new NpcSkin(
                        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWMwYmRhZmYwYjllNTQ3OTdiMTE4MzM3ZWU2OTQ5ZmQzN2VlNjcwMDlhOGViMDVhNjMzYTdkZDNmZjU1YzYxNiJ9fX0=",
                        "mZn93e3/q/l66H3apgvcew8j+ti71P2IzaKgqz02Hgpxp7mdv2juguRKNvEhkfOPQAmIEbaUdAzUMR3ZvjbC2HY9QEGf4ioldcvqxZmvBY5+iCqfw5H83AC/p2xM1S02hV4k7C3UxDHSmo2yOabXuqId060UvXdtGyRvoB5Javd2wqSu+tJIYE2YCqLdMEJZPs9KUjsjwxPVtdV+zMYaQtpDSNtmuwtU9ndWVkg1dINJj396OI+vJY4AhsVkBOMDcje3XEnNWTfQiXtvACRDSghvU5qi7jHdJcZeGgbj540pW2hkRxMEMLWfG8LMI7RJKxOlRuuJ6D+u6y5jfN3Sz5gCvgNNeWUeXBX6Lbe9tmA9ykAlFYHMf+zP9HUK/k+IEhHqlMVyNBmyVGgeaKrfpb3LDGqNzfBWuye71l2w2UYtC+MiHyLa/vY6rN12bZ6/AUbUTZe2yYtp18UDPjB2rwpu4MFtzUF/ESwIao1krI+nju681WYdNa//0/dOR3zYgqT7pnOFLQzF76WmoD4BclbSOy51oqUslxXxPGwlGbFihkX4bAJeXn+IwBxZXKeXGCo3qg+ZkyJeztVunAMKp9doAmW+xlSIfdBbswKQCuuduMpD++1ccuPztPqIWn446e/23332vgyds56apDCjJ8+qeN+jJf7TYpw7tZahUFU="
                ),
                new NpcSkin(
                        "ewogICJ0aW1lc3RhbXAiIDogMTY0MDU0NDEwMjQyNCwKICAicHJvZmlsZUlkIiA6ICJiNzVjZDRmMThkZjg0MmNlYjJhY2MxNTU5MTNiMjA0YiIsCiAgInByb2ZpbGVOYW1lIiA6ICJLcmlzdGlqb25hczEzIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzhlY2UyM2ZlMDM3NWYwMDAzYWY4ZTlhYTBlM2JjZjkzNzM2Y2Q0YWM2YmZmYTY5OTNhNzNiYzNjMTg1ZWY2NTUiLAogICAgICAibWV0YWRhdGEiIDogewogICAgICAgICJtb2RlbCIgOiAic2xpbSIKICAgICAgfQogICAgfQogIH0KfQ==",
                        "heWfpN7Osulz9IahSv5HZ5tiQ+kz0qYT3PycZU8IV9ab6jmDyiGyvpXSIfQGdGpgVJFrVVsh24lz2XpmKKRiMBUgNwfj5TbhI/JGjichc770rCPYY13CY748X1GNhEuN0Sj5oSzCZM6fo2MgAYEI3PpgTpYD7XDn9MV6n94C3kiNB8fc6KfaEXwvzoylyrhd/6CZgDuERGTl5OrXQsER3K1wO54+v8wyg7obye5oMBbG1SEvuKsSaCdqyd3XKamC0NjeYPQP9TFhd83by25M4dv6yS4PrFMgDhHFK4YxeEwfXfXFroHUX7LHixuM+djKZW0v2BAO0Z5Q2ik3nICuToS+/j9gDvYOvXywVszmWqbgMI3W4jZDj5yUVQVB9YWfKVZ7oDCYH8z8ESHs4bp7JQha+4CuBmF/5hoWoa+Id+ie205tXIM8zsfBmDcpFRAVwL1foWXuu3gA04S2fQQYAgy9+YhTWhEk70z/Jz+0LEJfGyDIUHGgzVMogcAjfeDG2/pSvvxKtD0JeAIsOktq7CR7gLNfk2GTWl5Pkq/E5WGIpz4i5Nbhlu9QD5wV0kBrC+FKfnJnRldxwRyi1IxZ7+9wB/5ZNDsHRAg+k6BlGbEKBYqMQi72v8gTUedim9PtJ8ykYSgNTMdKzZy7T210b9k5UUlVW1JDg0wa9IgFwcg="
                ),
                new NpcSkin(
                        "ewogICJ0aW1lc3RhbXAiIDogMTY4OTQwMDM2NzUyOSwKICAicHJvZmlsZUlkIiA6ICJjNTZlMjI0MmNiZWY0MWE2ODdlMzI2MGRjMGNmOTM2MSIsCiAgInByb2ZpbGVOYW1lIiA6ICJMSlI3MzEwMCIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS83MWYzOWU0MTUyNWJkNzhiNmYwZDNlZjc5ZGZhYzk1MDA3ODVjMjJhNGM0ZmYzYjJhYzNhOTgwYWVhMWI0OGQ3IiwKICAgICAgIm1ldGFkYXRhIiA6IHsKICAgICAgICAibW9kZWwiIDogInNsaW0iCiAgICAgIH0KICAgIH0KICB9Cn0=",
                        "YRAAZIDg5DA9NSo2HUacu/kPs6HIdRKdSiTE6EEQLYsGF/Ip2wveKpAlOd/WV6CS8qVGIRPrg6XIJPQgxKwtZTQxS+ClyLrWOaXlbpBAVffLvP+ps8KihbB6CLLKVrVdXihoJ4jYlS6+hgdOuSQ3QzoF+TSPjBAAAjBHJ3gg2E3FLnTJYWdfRzqYivRhqEjiae79QBsNxW93ZK3hJga15BDW3mjyaRVAHslHeYGj0SzC1rHUixhvoyzaTFc6nIs8CnxcYUKMJUz4eaWgIBDt/LZ/8fkTJzM+UjuVZUV2iUbe4rQeoBpFn5ytAHfJa0JzWrzySGWabKe9Ufpt65ZxS+t5XUtlfMMPZrJhByYbfYhPBgy7pPITh/YjXSLk6rS8pI+3jNsoNELL5+TIVFI+XA0EBNlGrjK8BUiTn4kYOYMjAvBRKvPDWNPq4q+33yR9OnMVhE9NrmcXNn2YF+So+nRwqjJsCqS+dZltvhywNPk6L5pgUU6m5AWM1ep0vZPF9EeJx47IyqJWJ+6RX620/NvCPdLFTgnh4Gfmx2wpsoN/donJHgXKsOI0YbdQjlz5qtY8xCsBGC/OHOtbfoNMCU5pp4Spui6pWhl67i34mYYhQB+4P4P8/R8C/ZleEWApkYakdYQtm5HG229l8ujx/+trZ4hwwPwiT7q/Y7djAr4="
                )
        );

        List<String> names = Arrays.asList(
                "§dИзабелла", "§dМоргана", "§dАгата", "§dЛилит", "§dСелена",
                "§dГвендолин", "§dФрея", "§dАврора", "§dНикс", "§dГелла",
                "§dВеспера", "§dЦирцея", "§dМедея", "§dГекуба", "§dПандора"
        );

        return new NpcType("witch", skins, names);
    }

    private NpcType createOrcType() {
        List<NpcSkin> skins = Arrays.asList(
                new NpcSkin(
                        "ewogICJ0aW1lc3RhbXAiIDogMTc4Mzg4NTU5ODcxMSwKICAicHJvZmlsZUlkIiA6ICJiNTQ1ZDcxNDJkZmM0MWVlYjBjMDcwOTkwOTI4NTE0MyIsCiAgInByb2ZpbGVOYW1lIiA6ICJraXdpNDgxMiIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS82OTVjNzNkMmQ3YjIzOTRiZTFiZjE0MzY4NjQ0ZjFjMWNhMDU0MjBlNzY1YzZlN2M1NzA4NjM4ZGI2ZjMxNTI1IgogICAgfQogIH0KfQ==",
                        "tq0dxxEXRdYsAQmxd+caVB6JiY27TRAjQicF7b9L372WFhm8IYugcsW2pTPOj5n95ArUXAapBToOWPbe9FWsNjAqsPOvHwfD33qwjKKY2d7rtczczBwdVGSZmo62ac7CQMP/n05Y9ZAzaZV7amSIZCoC1G8xQ8YlD+iegCXQs5cxIk3VADVhweJiSLOj5i/fFxHu3+ho2t8AwDI2R0PdBxawDS0H2CC7kc8JxoIUGg6olJ3vlFDqp2a/nGtXcv2doKQfa++rEbYTRcuVTGMhemEWx4vpE/QviFIi8XhnT9wjOXQVj3CTm7IJr6J5F4WRRARvTJd/SH4Hx54HSQeG1E8wb25mrfi/G/pTHXdgN+TauAJ0AWf+njl0xqZX5n6RiuplV19PtNMaEmJDDgUbs7cCUla4Rq7uYtzJwf0Cm7L66eFe1SP7r+2roHzbVG070r+Pt/O7JjBMY5OexQ0bLzkBEutpT7ZlneP4KKzmNXlGjLTpdwtf3dq5L0i2T0h5N9VGQucZG1GxIW0k/cmCKZoJ7Qmb5ynejCyYlXw2KaYTBa6mO+XPsS+rDs/nl28V22EjIUOu/q/Jr/c6K2AzgcIqrbtedx5pu5xPQ6j9cfRG1L7hNEhW3CPIQlSXyAm7JXNiWph9CiGStIyGvYMTobbY8qSECQPQ0he7WKm9NPk="
                ),
                new NpcSkin(
                        "ewogICJ0aW1lc3RhbXAiIDogMTYyMTY5NDQxNzI0NCwKICAicHJvZmlsZUlkIiA6ICJiMGQ3MzJmZTAwZjc0MDdlOWU3Zjc0NjMwMWNkOThjYSIsCiAgInByb2ZpbGVOYW1lIiA6ICJPUHBscyIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9mMWY5MjIxOWI1YzYwODg1NjYxMzY5MGUzNTliMjFkYzBkMjYzN2NlODI4NjUxYzkxMzI4OGEzNjJmZDY2ZThkIgogICAgfQogIH0KfQ==",
                        "aTtdfyz0H9FtG2Bjyr3X54j7RTomgwc4MIYv6ImywCyz4IolAXxmb1LAkHZ+lCZWZC791SQPehbo0oWvl0DsjnxKTkll3/ngTBFCPdBMVfz5abtF3096esP9eRO87GTNe7cyTL21ofcKYIRrIkobGC4WdOkESz+3rEV/xskRnkx7+6IeVE1pbwby8wsQ3LQIVZRF5jm5nWUq7lSRZUy/MslNWL4ZOOoS8NkmwnMmQUg5ykDQsHLh2Q9q59HYq1K1Yo75mSCQDciwZFEtCfVbe13ZuF/km/xmHHIiWrsxyOyfqDGcN/wzdVPND+/X/SPiNcLvs0vopovR7fwd4uKy9SYzcp7e7ptX6fqag3N2twlFVGkbhIRmeGIPbh3Kqy5u3+c1LX//YV7nggfvSQ8nGxb0yKNo71k4I0lbKs+HfzRLx4KUFI6Dq5nDxEWFTClYb8KG8RnXMjFmiwkLKcGl9ftmT/P8gevzSUqZnNbTJ88w/CrEd1toERWA0t2eKFd4yeT0SDBLoecaWD/OGAXAFLUHbG789poIpDx4/sNuZ80V/CqXWOv7Wr/ydGckn6TeAGG/oz2DmWECvxlwW6mHC0viHHkWZSHIPDbTZtmtIOWHvEBraioj3JMsFMLZv++jSzJlepZDJJnHG7vNRt+7BkDMz6e68FoyrIXox8Z1+Iw="
                ),
                new NpcSkin(
                        "ewogICJ0aW1lc3RhbXAiIDogMTc0NTMzMjkyMzA1NiwKICAicHJvZmlsZUlkIiA6ICIwNmY4YmNhNzc0NWY0OGIzYWVkMmMxMGY0MGMzN2VjYSIsCiAgInByb2ZpbGVOYW1lIiA6ICJEYXNoTmV0d29yayIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS8xZDVhMDViNjMyYjI3MGJmY2Y4MGEwMjMyMWZiMTYwNDJlNWZmNDViOGY1YmZjNmNhZmZjZmRiZjdhYWU1MDQwIgogICAgfQogIH0KfQ==",
                        "V+33C53LWW1EKpQ4d9EdkxzWv4NI6t58/8tvqQRxFjZT9aDqSvR042ps+DNch8w6yqb3zHrY5yJ/Prw9iyeP+8P82zBzh9nix26IzH5GmimG00WlYZhZmw+9YK3z25mDvhb0SivWo+T05eNNE8qXbcBEtbQrGXpt4RFYAO5KmfQ2BbogMHLph02k2HOL2+rBb18MjEMyFtf+tQC3DKmaYuDw5pqgVUUi+lBTJNDqP5T5cDLVmiKIDxlH4MW3PlDm4tOM4rK625MqUJes0GMuY+/5kTvNjrQw6O7oWhRx2IntYj+AT0IcAoNsB/Eschouo08g1P5KT3dJY9A8x958OePOsY5Yx91Ul4ehYNumvHG5dkFlOb5vvr+gG3QtD7G41xkFQi6WUGmAf/2ac02zTScXqJTF1dvNK22w0o0RVnJc4cbH+UJbbuKbDdjMCxREqCJ/EypC9FEn/9GSLkiy3W3rmY5x8/Gikq2+2X8AHUxGroyP2F4aKI9/EnrGstAum81XgkdEEIrhP/h12oI4ovT6r04UjuwYz1jAGTzQfe0/XCqzhxWXPvdsvlRtD940EjIzPWUuXexaJmW1R09AGC7tmJtlRfM7zBunYM+qxPoj0kKUrymAsKXOfRFyIQSpd/75730kGDwFagYU3e4pxFYD4fGsxpskJhLu5JEDhAg="
                )
        );

        List<String> names = Arrays.asList(
                "§2Громаш", "§2Кругор", "§2Тарзул", "§2Ургаш", "§2Зогар",
                "§2Могрул", "§2Зарг", "§2Тхарг", "§2Грум", "§2Огрум",
                "§2Краг", "§2Брутус", "§2Скар", "§2Угтар", "§2Назгул"
        );

        return new NpcType("orc", skins, names);
    }

    private NpcType createGoblinType() {
        List<NpcSkin> skins = Arrays.asList(
                new NpcSkin(
                        "ewogICJ0aW1lc3RhbXAiIDogMTY1NDQzNTI0NDY5NywKICAicHJvZmlsZUlkIiA6ICJjYmFkZmRmNTRkZTM0N2UwODQ3MjUyMDIyYTFkNGRkZCIsCiAgInByb2ZpbGVOYW1lIiA6ICJmaXdpcGVlIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzI4YzY4MTBlNWRlNGM2M2M1MTNhMzdlYWFhZWEyMjFlMzQxZjY4MjY4NzEzNzhmOTMwODIzMGQwYjVmMWJhYWEiLAogICAgICAibWV0YWRhdGEiIDogewogICAgICAgICJtb2RlbCIgOiAic2xpbSIKICAgICAgfQogICAgfQogIH0KfQ==",
                        "SVmDDhXWOJ1PpqZ+6xCPSemK7TwgG+yC72QncPVSfVgy5sbUjjYU0kM2MFcRlLgB0UDlu+cnuCU/VvPOK5cVlqi2vSk8EgPvrxNTjb5tMVBTw89fbpiLMwAAsEpazjkygno4FTqoQkZkXoKKc9FWcqt0T7fZWoxhHh/cnSdRl4Xfe+pA9iUKjS+U9FtVwfCJyu6cX2mxD1aFOGleRyAw+GV6rXxTjp1Q04hf5sQR3qKVVF6G8PYBRajdRHfNax7MTmxvvMrH5ajGPw/T50yblf0zFMPWvdroPYPD6BkXRI+EZEOMql495JyFvFHXGQUlhmRBoCYaJh7xPN+bsMxPDrLwZTNiIhmf6sw+l8GJdVDNUTw+wkwZw/tuxkbBW05ugVy+1I5BXuqBl9i43goTGVW/QvEt3W1agHXsah9gpX5JlPnIoYqVoPw+x5dMWJLat5ZglLon5iZjmfdXisa2e/+m7X5Vv9k7aM3T84shWiVVSZ+qXM3u4D+o1OFReksrpwRQYCNt0f+NkcRNJkVcseGOoWlf27fJTmLE/dzVe0xdwkYF2C0LVaCt8wwjz0NkUwBDpHs1gkST/MZIYwKzgcr0udMMH6qyxodbsRjEfzOYJ+n4E46OoiC2BdCpP5NNC2KMBrWpdEDRzqmNdhgdNHYrntYRokJBlzETjj73214="
                ),
                new NpcSkin(
                        "ewogICJ0aW1lc3RhbXAiIDogMTYyMjI0ODk2Mjc1MCwKICAicHJvZmlsZUlkIiA6ICJkODAwZDI4MDlmNTE0ZjkxODk4YTU4MWYzODE0Yzc5OSIsCiAgInByb2ZpbGVOYW1lIiA6ICJ0aGVCTFJ4eCIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9hZTVjOTJmMDc0YTZiODVjMmVmOTUyNDkzMTQxZDM3MGM3MGU1NjcwOTJhNDJlZTUyYTE1N2ZlZTAyZTViZTA5IgogICAgfQogIH0KfQ==",
                        "mcVxuBVReUBhx/Din9ZUT8ZDogIGVm1m8m7NkSdc2jM9y1stx5Nag96o+eAtC0Bi8X9MmA5f8e5e6AVVs0jHXbEP1+62KqSBvITK75HZI/nLOqFaFNggA5+ynlKWpYwyGHstkxIoYZk6IILZPpoCll+oACTIfxvBVQ/xAakr5+A11kZWAJoyfXWJ4ijfWXq/NDbZPpwO5lGuk67TofnTYA4lZuU6Vno5vnOpA69B8oEDXUdWDJsGjN/XrfPqQuCDJ3Ab8LQzScDtekHX9ZhdncpihC76sbSvoBruT+mmgNlBJa8Ny61FXU2LxkicBfFhbqfuNNGf/5ZbHgFc+gamDy5pyMXwqLdaRRJ3OvwGQz0SgTLNPci/QMBQ4oqGwoyEuYfaKwq9jMqcI9MQir6x8XbDpR/9zdu95GQzihEElwntMd1bxSXdLCICHmNaKMRSH0zbHvuTKY/EP9M1GxYdo48ivUwKtSrTEyLT+4I76kj+hNqLhGHwO/fw2pl0iyaZuPIXO2DPBVtQwHOU58dHWeLhVG2wmbetRDlp7ZhEuVTPtnxBwQ7XC1erla3DkfrDMC8jdzARrzSzffJD/ls+XD9ufQHW4AJFZX5qSqS2YsWTMSQn+WjICTlNdXatqKHi6cxZKPg6lX1fBw4E4ejh6EAzZPkLSbhRBMTjKE5r300="
                ),
                new NpcSkin(
                        "ewogICJ0aW1lc3RhbXAiIDogMTYxNTE1NTgwMzIyMSwKICAicHJvZmlsZUlkIiA6ICJjMGYzYjI3YTUwMDE0YzVhYjIxZDc5ZGRlMTAxZGZlMiIsCiAgInByb2ZpbGVOYW1lIiA6ICJDVUNGTDEzIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2Q0NGU4NzFmYjcyOGExYjljN2Y3NjM0NmJlNTZiM2YwODAxODUxM2Y5Nzg2NmMwNmRiNzYzYzY2OWJjNjU0ODgiCiAgICB9CiAgfQp9",
                        "EHNfAAhlJFe34fKRO96qVw7+y1ksryguHZFJkIWII4AL/F45gGZY3q8NQrNGNk2WUAzV9Eexwz4m7Kn3M3caX5B6ftUbji7EZtYFA83K2kdJDo8OxY4ff5W83S8ag3jZ9+s2M8WKUixhDn4EYpBrFfTVvrhQsud1R6W2K+2cdR301qdDzF2XT96bVEG02pY+L6OrQlWPAHBBsRDnCvdaVVU5w7ciDIxDidn2GMJbPPPKa9kxEiaKZ+RFOnJuExbS3VItAjlWMUblYre8E4GlmoUj/Gp1Cm6mdQnX9UsaqlRp9F96qjoQoSiGWxRVjz9yRYuLY+WwbBBFGB3HlVVeTwgd82HSz+n/HGvVHfGkWWMKmVVGUVWE52O/5olySjIuEnCoSSlDO3r2We/srAlp2o6BDjMC0lmNrD+WCd0YPxAJRPY6ilRppz2Yavc8qVoSJMHoeya8Whb+47DyatO51yQ0X9Ab9v0OTwbDKYXYrGzZla3S1kN5eOAEezmgfHRp5MVRrWaBBBuJGmGUKvFr5Is7+CAXtJ/sM6gsjDB3z6SliE1hZFjn2fihGvplPLUicfI4DlH0j4/ir9qD5tdDbJQo2wkvxFYfxzvhz2Qy0Xo8R4F08EnVqtFonkj5CSeA96NV9ex2Q6ty0U3czwtuUX240s57qeQJRHnSDGWM1DI="
                )
        );

        List<String> names = Arrays.asList(
                "§aШмыг", "§aКусь", "§aПиск", "§aЖмыг", "§aХрусь",
                "§aЦап", "§aШныг", "§aХвать", "§aЦоп", "§aТыг",
                "§aМыг", "§aЖуй", "§aХрысь", "§aГрызь", "§aНям"
        );

        return new NpcType("goblin", skins, names);
    }
}