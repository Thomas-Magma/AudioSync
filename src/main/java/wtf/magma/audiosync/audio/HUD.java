package wtf.magma.audiosync.audio;

import com.terraformersmc.modmenu.util.mod.Mod;
import net.minecraft.client.MinecraftClient;
import wtf.magma.audiosync.AudioSync;
import wtf.magma.audiosync.config.ModClientConfig;
import wtf.magma.audiosync.event.annotations.EventTarget;
import wtf.magma.audiosync.event.events.NanoEvent;
import wtf.magma.audiosync.util.Base64ImageRenderer;
import wtf.magma.audiosync.util.TitleParser;
import wtf.magma.audiosync.util.font.NanoFontLoader;

import java.awt.*;

public class HUD {

    public HUD(){
        AudioSync.Instance.getEventManager().register(this);
    }

    @EventTarget
    public void on2d(NanoEvent event){
        boolean cover = ModClientConfig.renderCover;
        boolean titleBoolean = ModClientConfig.renderTitle;
        boolean singerBoolean = ModClientConfig.renderSinger;
        boolean playtimeBoolean = ModClientConfig.renderTime;
        int height = MinecraftClient.getInstance().getWindow().getScaledHeight();

        switch (ModClientConfig.position){

            case Bottom_left -> {
                float fontX = cover ? 70 : 10;
                float fontY = titleBoolean ? MinecraftClient.getInstance().getWindow().getScaledHeight() - 58 : MinecraftClient.getInstance().getWindow().getScaledHeight() - 68;

                if (cover) Base64ImageRenderer.drawBase64Image(SMTC.getBase64(),String.valueOf(SMTC.getBase64().length()),10, MinecraftClient.getInstance().getWindow().getScaledHeight() - 70,50,50);
                if (titleBoolean) NanoFontLoader.Misans_Regular.drawGlowString(TitleParser.getSongName(SMTC.getTitle()),fontX,MinecraftClient.getInstance().getWindow().getScaledHeight() - 68, Color.WHITE);
                if (singerBoolean) NanoFontLoader.Misans_Regular.drawGlowString(TitleParser.getArtist(SMTC.getTitle()),fontX,fontY, Color.WHITE);

                String pos = String.format("%02d:%02d", SMTC.getPos() / 60, SMTC.getPos() % 60);
                String dur = String.format("%02d:%02d", SMTC.getDur() / 60, SMTC.getDur() % 60);
                float timeFontY = titleBoolean ? singerBoolean ? height - 45 : height - 58 : singerBoolean ? height -58 : height -68 ;
                if (SMTC.getDur() != 0 && playtimeBoolean) NanoFontLoader.Misans_Regular.drawGlowString(pos + " / " + dur, fontX, timeFontY, Color.WHITE);
            }

            case Top_left -> {
                float fontX = cover ? 70 : 10;
                float fontY = titleBoolean ? 22: 12;

                if (cover) Base64ImageRenderer.drawBase64RoundRectImage(SMTC.getBase64(),String.valueOf(SMTC.getBase64().length()),10, 10,50,50,4);
                if (titleBoolean) NanoFontLoader.Misans_Regular.drawGlowString(TitleParser.getSongName(SMTC.getTitle()),fontX,12, Color.WHITE);
                if (singerBoolean) NanoFontLoader.Misans_Regular.drawGlowString(TitleParser.getArtist(SMTC.getTitle()),fontX,fontY, Color.WHITE);

                String pos = String.format("%02d:%02d", SMTC.getPos() / 60, SMTC.getPos() % 60);
                String dur = String.format("%02d:%02d", SMTC.getDur() / 60, SMTC.getDur() % 60);
                float timeFontY = titleBoolean ? singerBoolean ? 35 : 22 : singerBoolean ? 22 : 12 ;
                if (SMTC.getDur() != 0 && playtimeBoolean) NanoFontLoader.Misans_Regular.drawGlowString(pos + " / " + dur, fontX, timeFontY, Color.WHITE);
            }

            case Top_right -> {
                float width = MinecraftClient.getInstance().getWindow().getScaledWidth();

                String pos = String.format("%02d:%02d", SMTC.getPos() / 60, SMTC.getPos() % 60);
                String dur = String.format("%02d:%02d", SMTC.getDur() / 60, SMTC.getDur() % 60);

                float titleFontX = cover ? width - NanoFontLoader.Misans_Regular.getStringWidth(TitleParser.getSongName(SMTC.getTitle())) - 70 : width - NanoFontLoader.Misans_Regular.getStringWidth(TitleParser.getSongName(SMTC.getTitle())) - 10;
                float singerFontX = cover ? width - NanoFontLoader.Misans_Regular.getStringWidth(TitleParser.getArtist(SMTC.getTitle())) - 70 : width - NanoFontLoader.Misans_Regular.getStringWidth(TitleParser.getArtist(SMTC.getTitle())) - 10;
                float posFontX = cover ? width - NanoFontLoader.Misans_Regular.getStringWidth(pos + " / " + dur) - 70 : width - NanoFontLoader.Misans_Regular.getStringWidth(pos + " / " + dur) - 10;

                float fontY = titleBoolean ? 22: 12;

                if (cover) Base64ImageRenderer.drawBase64Image(SMTC.getBase64(),String.valueOf(SMTC.getBase64().length()),width - 60, 10,50,50);
                if (titleBoolean) NanoFontLoader.Misans_Regular.drawGlowString(TitleParser.getSongName(SMTC.getTitle()),titleFontX,12, Color.WHITE);
                if (singerBoolean) NanoFontLoader.Misans_Regular.drawGlowString(TitleParser.getArtist(SMTC.getTitle()),singerFontX,fontY, Color.WHITE);

                float timeFontY = titleBoolean ? singerBoolean ? 35 : 22 : singerBoolean ? 22 : 12 ;
                if (SMTC.getDur() != 0 && playtimeBoolean) NanoFontLoader.Misans_Regular.drawGlowString(pos + " / " + dur, posFontX, timeFontY, Color.WHITE);
            }

            case Bottom_right -> {
                float width = MinecraftClient.getInstance().getWindow().getScaledWidth();

                String pos = String.format("%02d:%02d", SMTC.getPos() / 60, SMTC.getPos() % 60);
                String dur = String.format("%02d:%02d", SMTC.getDur() / 60, SMTC.getDur() % 60);

                float titleFontX = cover ? width - NanoFontLoader.Misans_Regular.getStringWidth(TitleParser.getSongName(SMTC.getTitle())) - 70 : width - NanoFontLoader.Misans_Regular.getStringWidth(TitleParser.getSongName(SMTC.getTitle())) - 10;
                float singerFontX = cover ? width - NanoFontLoader.Misans_Regular.getStringWidth(TitleParser.getArtist(SMTC.getTitle())) - 70 : width - NanoFontLoader.Misans_Regular.getStringWidth(TitleParser.getArtist(SMTC.getTitle())) - 10;
                float posFontX = cover ? width - NanoFontLoader.Misans_Regular.getStringWidth(pos + " / " + dur) - 70 : width - NanoFontLoader.Misans_Regular.getStringWidth(pos + " / " + dur) - 10;

                float fontY = titleBoolean ? MinecraftClient.getInstance().getWindow().getScaledHeight() - 58 : MinecraftClient.getInstance().getWindow().getScaledHeight() - 68;

                if (cover) Base64ImageRenderer.drawBase64Image(SMTC.getBase64(),String.valueOf(SMTC.getBase64().length()),width - 60, MinecraftClient.getInstance().getWindow().getScaledHeight() - 68,50,50);
                if (titleBoolean) NanoFontLoader.Misans_Regular.drawGlowString(TitleParser.getSongName(SMTC.getTitle()),titleFontX,MinecraftClient.getInstance().getWindow().getScaledHeight() - 68, Color.WHITE);
                if (singerBoolean) NanoFontLoader.Misans_Regular.drawGlowString(TitleParser.getArtist(SMTC.getTitle()),singerFontX,fontY, Color.WHITE);

                float timeFontY = titleBoolean ? singerBoolean ? height - 45 : height - 58 : singerBoolean ? height -58 : height -68 ;
                if (SMTC.getDur() != 0 && playtimeBoolean) NanoFontLoader.Misans_Regular.drawGlowString(pos + " / " + dur, posFontX, timeFontY, Color.WHITE);

            }
        }
    }
}
