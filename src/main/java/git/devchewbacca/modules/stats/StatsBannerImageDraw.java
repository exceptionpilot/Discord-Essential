package git.devchewbacca.modules.stats;

import git.devchewbacca.UtilityBot;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class StatsBannerImageDraw {

    public File[] banners;

    public StatsBannerImageDraw() {
        File directory = new File("images/banners/");
        if (!directory.exists())
            directory.mkdirs();
        this.banners = directory.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".png");
            }
        });
    }

    public File selectBanner() {
        int random = ThreadLocalRandom.current().nextInt(this.banners.length);
        return this.banners[random];
    }

    public byte[]draw(Guild guild) throws IOException {
        BufferedImage bufferedImage = new BufferedImage(960, 540,2);
        Graphics2D graphics2D = bufferedImage.createGraphics();

        graphics2D.setPaint(Color.black);
        graphics2D.fill(new Rectangle(0, 0, 960, 540));

        BufferedImage customBackground = ImageIO.read(selectBanner());
        graphics2D.drawImage(customBackground,
                null,
                bufferedImage.getWidth() / 2 - customBackground.getWidth() / 2,
                bufferedImage.getHeight() / 2 - customBackground.getHeight() / 2
        );

        int onlineCount = 0;
        for (Member member : guild.getMembers()) {
            if (!member.getUser().isBot() || guild.getSelfMember().getId().equals(member.getId())) {
            if (member.getOnlineStatus() != OnlineStatus.OFFLINE) {
                onlineCount++;
                }
            }
        }

        graphics2D.setPaint(Color.white);
        Font font = new Font("Monospace", Font.BOLD, 65);
        FontMetrics metrics = graphics2D.getFontMetrics(font);
        graphics2D.setFont(font);

        graphics2D.drawString(String.valueOf(onlineCount),
                140,
                430
        );

        graphics2D.drawString(String.valueOf(guild.getMemberCount()),
                140,
                500
        );

        ByteArrayOutputStream byteArrayOutput = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", byteArrayOutput);
        byteArrayOutput.flush();
        return byteArrayOutput.toByteArray();
    }
}
