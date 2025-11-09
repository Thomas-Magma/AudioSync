package wtf.magma.audiosync.config;

import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.api.controller.EnumControllerBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import dev.isxander.yacl3.dsl.ControllersKt;
import dev.isxander.yacl3.gui.controllers.cycling.EnumController;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public final class ModClientConfig {
    public static final ConfigClassHandler<ModClientConfig> INSTANCE = ConfigClassHandler.createBuilder(ModClientConfig.class)
        .id(Identifier.of("audiosync", "config"))
        .serializer(config -> GsonConfigSerializerBuilder.create(config)
                .setPath(FabricLoader.getInstance().getConfigDir().resolve("audiosync-client.json")).build())
        .build();

    @SerialEntry
    public static boolean renderCover = true;
    @SerialEntry
    public static boolean renderSinger = true;
    @SerialEntry
    public static boolean renderTitle = true;
    @SerialEntry
    public static boolean renderTime = true;
    @SerialEntry
    public static Position position = Position.Top_left;
    @SerialEntry
    public static boolean round = true;


    public static Screen makeScreen(Screen parent) {
        return YetAnotherConfigLib.create(INSTANCE, (defaults, config, builder) -> builder
                        .title(Text.of("Option"))
                        .category(ConfigCategory.createBuilder()
                                .name(Text.translatable("client.config.screen"))
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.translatable("client.music.cover"))
                                        .description(OptionDescription.of(Text.translatable("client.music.cover.description")))
                                        .binding(
                                                true,
                                                () -> renderCover,
                                                value -> renderCover = value
                                        )
                                        .controller(opt -> BooleanControllerBuilder.create(opt)
                                                .valueFormatter(val -> Text.literal(val ? "ON": "OFF"))
                                                .coloured(true))
                                        .build())
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.translatable("client.music.title"))
                                        .description(OptionDescription.of(Text.translatable("client.music.title.description")))
                                        .binding(
                                                true,
                                                () -> renderTitle,
                                                value -> renderTitle = value
                                        )
                                        .controller(opt -> BooleanControllerBuilder.create(opt)
                                                .valueFormatter(val -> Text.literal(val ? "ON": "OFF"))
                                                .coloured(true))
                                        .build())
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.translatable("client.music.time"))
                                        .description(OptionDescription.of(Text.translatable("client.music.time.description")))
                                        .binding(
                                                true,
                                                () -> renderTime,
                                                value -> renderTime = value
                                        )
                                        .controller(opt -> BooleanControllerBuilder.create(opt)
                                                .valueFormatter(val -> Text.literal(val ? "ON": "OFF"))
                                                .coloured(true))
                                        .build())
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.translatable("client.music.singer"))
                                        .description(OptionDescription.of(Text.translatable("client.music.singer.description")))
                                        .binding(
                                                true,
                                                () -> renderSinger,
                                                value -> renderSinger = value
                                        )
                                        .controller(opt -> BooleanControllerBuilder.create(opt)
                                                .valueFormatter(val -> Text.literal(val ? "ON": "OFF"))
                                                .coloured(true))
                                        .build())
                                .option(Option.<Position>createBuilder()
                                        .name(Text.translatable("client.position.name"))
                                        .description(OptionDescription.of(Text.translatable("client.position.description")))
                                        .binding(defaults.position, () -> position, newVal -> position = newVal)
                                        .controller(opt -> EnumControllerBuilder.create(opt)
                                                .enumClass(Position.class)
                                                .valueFormatter(v -> Text.translatable("client.position." + v.name().toLowerCase())))
                                        .build())
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.translatable("client.music.round"))
                                        .description(OptionDescription.of(Text.translatable("client.music.round.description")))
                                        .binding(
                                                true,
                                                () -> round,
                                                value -> round = value
                                        )
                                        .controller(opt -> BooleanControllerBuilder.create(opt)
                                                .valueFormatter(val -> Text.literal(val ? "ON": "OFF"))
                                                .coloured(true))
                                        .build())
                                .build()))
                .generateScreen(parent);
    }
}