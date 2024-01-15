package git.devchewbacca.modules.welcome;

import git.devchewbacca.UtilityBot;
import git.devchewbacca.modules.welcome.config.ImageTemplate;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.utils.ImageProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;

public class WelcomeImageDraw {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ImageTemplate imageTemplate = UtilityBot.getInstance().getConfiguration().getImageTemplate();
    public File[] banners;

    public WelcomeImageDraw() {
        File directory = new File("images/backgrounds");
        this.logger.info("Fetching all banner...");
        if (!directory.exists())
            directory.mkdirs();
        this.banners = directory.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".png");
            }
        });
        assert this.banners != null;
        this.logger.info("Number of banners found: " + this.banners.length);
    }

    public File selectBanner() {
        int random = ThreadLocalRandom.current().nextInt(this.banners.length);
        return this.banners[random];
    }

    public byte[] draw(User user) throws Exception {
        ImageProxy avatar = user.getAvatarUrl() !=null ?
                user.getAvatar() : user.getDefaultAvatar();
        CompletableFuture<InputStream> stream = avatar.download(128);
        BufferedImage inputImage = ImageIO.read(stream.get());
        BufferedImage editAvatar = new BufferedImage(imageTemplate.getAvatarWidth(),imageTemplate.getAvatarHeight(), 2);
        Graphics2D graphics2D = editAvatar.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

//        graphics2D.setClip(
//                new Ellipse2D.Float(
//                        0.0F,
//                        0.0F,
//                        editAvatar.getWidth(),
//                        editAvatar.getHeight()
//                ));

        graphics2D.fill(new RoundRectangle2D.Float(
                0.0F,
                0.0F,
                editAvatar.getWidth(),
                editAvatar.getHeight(),
                imageTemplate.getAvatarCorners(),
                imageTemplate.getAvatarCorners()
        ));

        graphics2D.setComposite(AlphaComposite.SrcAtop);
        graphics2D.drawImage(
                inputImage,
                0,
                0,
                imageTemplate.getAvatarWidth(),
                imageTemplate.getAvatarHeight(), null);
        graphics2D.dispose();

        BufferedImage banner = new BufferedImage(imageTemplate.getBannerWidth(),imageTemplate.getBannerHeight(), 2);
        Graphics2D editBanner = banner.createGraphics();

        editBanner.setPaint(imageTemplate.getPrimaryColor());
        editBanner.fill(new RoundRectangle2D.Float(
                0.0F,
                0.0F,
                banner.getWidth(),
                banner.getHeight(),
                imageTemplate.getBannerCorners(),
                imageTemplate.getBannerCorners()
        ));

        if (imageTemplate.isCustomBackground()) {
            editBanner.setComposite(AlphaComposite.SrcAtop);
            BufferedImage customBackground = ImageIO.read(selectBanner());
            editBanner.drawImage(customBackground,
                    null,
                    banner.getWidth() / 2 - customBackground.getWidth() / 2,
                    banner.getHeight() / 2 - customBackground.getHeight() / 2
            );
        }

        editBanner.setPaint(imageTemplate.getSecondaryColor());
        Font fontHeader = new Font(imageTemplate.getFontName(), Font.BOLD, imageTemplate.getFontHeaderSize());
        FontMetrics metricsHeader = graphics2D.getFontMetrics(fontHeader);
        editBanner.setFont(fontHeader);
        editBanner.drawString(user.getEffectiveName(),
                (banner.getWidth() - metricsHeader.stringWidth(user.getEffectiveName())) / 2,
                (banner.getHeight() - metricsHeader.getHeight()) / 2 + metricsHeader.getAscent() + 70
        );
        Font fontSubtitle = new Font(imageTemplate.getFontName(), Font.ITALIC, imageTemplate.getFontSubtitleSize());
        FontMetrics metricsSubtitle = graphics2D.getFontMetrics(fontSubtitle);
        editBanner.setFont(fontSubtitle);
        editBanner.drawString(user.getName(),
                (banner.getWidth() - metricsSubtitle.stringWidth(user.getName())) / 2,
                (banner.getHeight() - metricsSubtitle.getHeight()) / 2 + metricsSubtitle.getAscent() + 130
        );



        editBanner.drawImage(editAvatar,
                banner.getWidth() / 2 - inputImage.getWidth() / 2,
                banner.getHeight() / 2 - inputImage.getHeight() / 2 - 40,
                inputImage.getWidth(), inputImage.getHeight(), null);
        editBanner.dispose();

        ByteArrayOutputStream byteArrayOutput = new ByteArrayOutputStream();
        ImageIO.write(banner, "png", byteArrayOutput);
        byteArrayOutput.flush();
        return byteArrayOutput.toByteArray();
    }

    public byte[]asyncDraw(User user) throws IOException, ExecutionException, InterruptedException {
        this.logger.info("Incoming request for Picasso...");
        CompletableFuture<byte[]> completableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                return draw(user);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        while (!completableFuture.isDone()) {
            this.logger.info("Picasso finished successfully!");
            return completableFuture.get();
        }

        File file = new File("error/404.png");
        byte[] bytes = Files.readAllBytes(file.toPath());
        return bytes;
    }
}
