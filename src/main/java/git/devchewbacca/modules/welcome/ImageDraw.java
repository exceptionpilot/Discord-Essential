package git.devchewbacca.modules.welcome;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.utils.ImageProxy;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.concurrent.CompletableFuture;

public class ImageDraw {


    public ImageDraw() {

    }

    public byte[] draw(User user) throws Exception {
        ImageProxy avatar = user.getAvatarUrl() !=null ?
                user.getAvatar() : user.getDefaultAvatar();
        CompletableFuture<InputStream> stream = avatar.download(128);
        BufferedImage inputImage = ImageIO.read(stream.get());
        BufferedImage editAvatar = new BufferedImage(128,128, 2);
        Graphics2D graphics2D = editAvatar.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.setClip(
                new Ellipse2D.Float(
                0.0F,
                0.0F,
                editAvatar.getWidth(),
                editAvatar.getHeight()
        ));
        graphics2D.fill(new RoundRectangle2D.Float(
                0.0F,
                0.0F,
                editAvatar.getWidth(),
                editAvatar.getHeight(),
                300,
                300
        ));
        graphics2D.setComposite(AlphaComposite.SrcAtop);
        graphics2D.drawImage(inputImage, 0,0, 128, 128, null);
        graphics2D.dispose();

        BufferedImage banner = new BufferedImage(1100,480, 2);
        Graphics2D editBanner = banner.createGraphics();

        editBanner.setPaint(Color.decode("#1c1c1c"));
        editBanner.fill(new RoundRectangle2D.Float(
                0.0F,
                0.0F,
                banner.getWidth(),
                banner.getHeight(),
                30,
                30
        ));
        editBanner.setPaint(Color.white);

        Font fontHeader= new Font("Monospace", Font.BOLD, 40);
        FontMetrics metricsHeader = graphics2D.getFontMetrics(fontHeader);
        editBanner.setFont(fontHeader);
        editBanner.drawString(user.getEffectiveName(),
                (banner.getWidth() - metricsHeader.stringWidth(user.getEffectiveName())) / 2,
                (banner.getHeight() - metricsHeader.getHeight()) / 2 + metricsHeader.getAscent() + 70
        );
        Font fontSubtitle = new Font("Monospace", Font.ITALIC, 20);
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
}
