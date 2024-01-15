package git.devchewbacca.modules.welcome.config;

import java.awt.*;

public class ImageTemplate {

    private final String fontName;
    private final int fontHeaderSize;
    private final int fontSubtitleSize;
    private final int avatarCorners;
    private final int avatarWidth;
    private final int  avatarHeight;
    private final String primaryColor;
    private final String secondaryColor;
    private final boolean customBackground;
    private final int bannerCorners;
    private final int bannerWidth;
    private final int bannerHeight;

    public ImageTemplate(String fontName, int fontHeaderSize, int fontSubtitleSize, int avatarCorners, int avatarWidth, int avatarHeight, String primaryColor, String secondaryColor, boolean customBackground, int bannerCorners, int bannerWidth, int bannerHeight) {
        this.fontName = fontName;
        this.fontHeaderSize = fontHeaderSize;
        this.fontSubtitleSize = fontSubtitleSize;
        this.avatarCorners = avatarCorners;
        this.avatarWidth = avatarWidth;
        this.avatarHeight = avatarHeight;
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
        this.customBackground = customBackground;
        this.bannerCorners = bannerCorners;
        this.bannerWidth = bannerWidth;
        this.bannerHeight = bannerHeight;
    }

    public String getFontName() {
        return fontName;
    }

    public int getFontHeaderSize() {
        return fontHeaderSize;
    }

    public int getFontSubtitleSize() {
        return fontSubtitleSize;
    }

    public int getAvatarCorners() {
        return avatarCorners;
    }

    public int getAvatarWidth() {
        return avatarWidth;
    }

    public int getAvatarHeight() {
        return avatarHeight;
    }

    public Color getPrimaryColor() {
        return Color.decode(primaryColor);
    }

    public Color getSecondaryColor() {
        return Color.decode(secondaryColor);
    }

    public boolean isCustomBackground() {
        return customBackground;
    }

    public int getBannerCorners() {
        return bannerCorners;
    }

    public int getBannerWidth() {
        return bannerWidth;
    }

    public int getBannerHeight() {
        return bannerHeight;
    }

}
