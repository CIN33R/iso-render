
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import javax.swing.JFrame;

import java.io.IOException;
import javax.imageio.ImageIO;

public class IsoRender {

    private JFrame frame;
    private Canvas canvas;
    private BufferStrategy bufferStrategy;
    private final String title = "iso test";
    private final int width = 1000;
    private final int height = 650;

    private final int imageWidth = 100;
    private final int imageHeight = 65;

    private BufferedImage image;

    private Graphics graphics;

    public static void main(String[] args) {
        IsoRender test = new IsoRender();
        test.setupDisplay();
        test.render();
    }

    public void tick() {

    }

    public void render() {
        image = ImageLoader.loadImage("/tile.png");

        bufferStrategy = canvas.getBufferStrategy();
        if(bufferStrategy == null) {
            canvas.createBufferStrategy(3);
            bufferStrategy = canvas.getBufferStrategy();
        }
        graphics = bufferStrategy.getDrawGraphics();
        graphics.clearRect(0, 0, width, height);


        for(int r = 0; r < 10; r++) {
            for(int c = 0; c < 10; c++) {
                //graphics.drawImage(image, r*imageHeight, c*imageWidth, imageWidth, imageHeight, null);
                int halfHeight= imageHeight/2;
                int halfWidth = imageWidth/2;
                double halfHeightDouble = imageHeight/2.0;
                double halfWidthDouble = imageWidth/2.0;

                System.out.println("int halfHeight " + halfHeight + ", " + "int halfWidth " + halfWidth);
                System.out.println("double halfHeight " + halfHeightDouble + ", " + "double halfWidth " + halfWidthDouble);

                int screenX = (r-c)*halfWidth;
                int screenY = (r+c)*halfHeight;
                double screenXDouble = (r-c)*halfWidthDouble;
                double screenYDouble = (r+c)*halfHeightDouble/2.0;

                System.out.println("int screenX " + screenX + ", " + "int screenY " + screenY);
                System.out.println("double screenX " + screenXDouble + ", " + "double screenY " + screenYDouble);

                int mapX = (screenX/halfWidth+screenY/halfHeight)/2;
                int mapY = (screenY/halfHeight - (screenX/halfWidth))/2;

                System.out.println("int mapX " + mapX + ", " + "int mapY " + mapY);
                System.out.println();

                graphics.drawImage(image, (int)screenXDouble+width/2, (int)screenYDouble, imageWidth, imageHeight, null);
            }
        }

        bufferStrategy.show();
        graphics.dispose();
    }

    public void setupDisplay() {
        frame = new JFrame(title);
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setMaximumSize(new Dimension(width, height));
        canvas.setMinimumSize(new Dimension(width, height));
        canvas.setFocusable(false);
        canvas.setBackground(Color.BLACK);

        frame.add(canvas);
        frame.pack();
    }
    public void run() {


        int fps = 60;
        double timePerTick = 1000000000 / fps;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();
        long timer = 0;
        int ticks = 0;

        while(true){
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            timer += now - lastTime;
            lastTime = now;

            if(delta >= 1){
                tick();
                render();
                ticks++;
                delta--;
            }

            if(timer >= 1000000000){
                //System.out.println("FPS: " + ticks);
                ticks = 0;
                timer = 0;
            }
        }
    }

}
