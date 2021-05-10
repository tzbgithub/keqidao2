package com.qidao.application.model.common;

import org.apache.commons.lang3.RandomUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

public class VerifyCodeUtils {
    //使用到Algerian字体，系统里没有的话需要安装字体，字体只显示大写，去掉了1,0,i,o几个容易混淆的字符
    private static final String VERIFY_CODES = "1234567890";
    private static final String INVITE_CODES = "23456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";


    /**
     * 使用系统默认字符源生成验证码
     *
     * @param verifySize 验证码长度
     * @return
     */
    public static String generateVerifyCode(int verifySize) {
        return generateVerifyCode(verifySize, VERIFY_CODES);
    }

    /**
     * 使用指定源生成验证码
     *
     * @param verifySize 验证码长度
     * @param sources    验证码字符源
     * @return
     */
    public static String generateVerifyCode(int verifySize, String sources) {
        if (sources == null || sources.length() == 0) {
            sources = VERIFY_CODES;
        }
        int codesLen = sources.length();
        StringBuilder verifyCode = new StringBuilder(verifySize);
        for (int i = 0; i < verifySize; i++) {
            verifyCode.append(sources.charAt(RandomUtils.nextInt(0, codesLen - 1)));
        }
        return verifyCode.toString();
    }

    /**
     * @param inviteSize 长度
     * @return
     */
    public static String generateInviteCode(int inviteSize) {
        return generateInviteCode(inviteSize, INVITE_CODES);
    }

    /**
     * 生成字符串加数字的字符串
     *
     * @param inviteSize 长度
     * @return
     */
    public static String generateInviteCodeAndNotNum(int inviteSize) {
        String str = generateInviteCode(inviteSize);
        if (isNum(str)) {
            return generateInviteCodeAndNotNum(inviteSize);
        }
        return str;
    }

    /**
     * 判断字符串是否是纯数字
     *
     * @param str 字符串
     * @return true 纯数字 false 混合
     */
    public static boolean isNum(String str) {
        return str.matches("^[0-9]*$");
    }

    /**
     * @param inviteSize 长度
     * @param sources    字符源
     * @return
     */
    public static String generateInviteCode(int inviteSize, String sources) {
        char[] ch = sources.toCharArray();
        String inviteCode = "";
        for (int k = 0; k < inviteSize; k++) {
            int index = RandomUtils.nextInt(0, ch.length);//随机获取数组长度作为索引
            inviteCode += ch[index];//循环添加到字符串后面
        }
        return inviteCode;
    }


    /**
     * 生成随机验证码文件,并返回验证码值
     *
     * @param w
     * @param h
     * @param outputFile
     * @param verifySize
     * @return
     * @throws IOException
     */
    public static String outputVerifyImage(int w, int h, File outputFile, int verifySize) throws IOException {
        String verifyCode = generateVerifyCode(verifySize);
        outputImage(w, h, outputFile, verifyCode);
        return verifyCode;
    }

    /**
     * 输出随机验证码图片流,并返回验证码值
     *
     * @param w
     * @param h
     * @param os
     * @param verifySize
     * @return
     * @throws IOException
     */
    public static String outputVerifyImage(int w, int h, OutputStream os, int verifySize) throws IOException {
        String verifyCode = generateVerifyCode(verifySize);
        outputImage(w, h, os, verifyCode);
        return verifyCode;
    }

    /**
     * 生成指定验证码图像文件
     *
     * @param w
     * @param h
     * @param outputFile
     * @param code
     * @throws IOException
     */
    public static void outputImage(int w, int h, File outputFile, String code) throws IOException {
        if (outputFile == null) {
            return;
        }
        File dir = outputFile.getParentFile();
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try {
            if (outputFile.createNewFile()) {
                FileOutputStream fos = new FileOutputStream(outputFile);
                outputImage(w, h, fos, code);
                fos.close();
            }
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * 输出指定验证码图片流
     *
     * @param w
     * @param h
     * @param os
     * @param code
     * @throws IOException
     */
    public static void outputImage(int w, int h, OutputStream os, String code) throws IOException {
        int verifySize = code.length();
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Color[] colors = new Color[5];
        Color[] colorSpaces = new Color[]{Color.WHITE, Color.CYAN,
                Color.GRAY, Color.LIGHT_GRAY, Color.MAGENTA, Color.ORANGE,
                Color.PINK, Color.YELLOW};
        float[] fractions = new float[colors.length];
        for (int i = 0; i < colors.length; i++) {
            colors[i] = colorSpaces[RandomUtils.nextInt(0, colorSpaces.length)];
            fractions[i] = RandomUtils.nextFloat();
        }
        Arrays.sort(fractions);

        g2.setColor(Color.GRAY);// 设置边框色
        g2.fillRect(0, 0, w, h);

        Color c = getRandColor(200, 250);
        g2.setColor(c);// 设置背景色
        g2.fillRect(0, 2, w, h - 4);

        //绘制干扰线
        g2.setColor(getRandColor(160, 200));// 设置线条的颜色
        for (int i = 0; i < 20; i++) {
            int x = RandomUtils.nextInt(0, w - 1);
            int y = RandomUtils.nextInt(0, h - 1);
            int xl = RandomUtils.nextInt(0, 6) + 1;
            int yl = RandomUtils.nextInt(0, 12) + 1;
            g2.drawLine(x, y, x + xl + 40, y + yl + 20);
        }

        // 添加噪点
        float yawpRate = 0.05f;// 噪声率
        int area = (int) (yawpRate * w * h);
        for (int i = 0; i < area; i++) {
            int x = RandomUtils.nextInt(0, w);
            int y = RandomUtils.nextInt(0, h);
            int rgb = getRandomIntColor();
            image.setRGB(x, y, rgb);
        }

        shear(g2, w, h, c);// 使图片扭曲

        g2.setColor(getRandColor(100, 160));
        int fontSize = h - 4;
        Font font = new Font("Algerian", Font.ITALIC, fontSize);
        g2.setFont(font);
        char[] chars = code.toCharArray();
        double halfFontSize = (double) fontSize / 2;
        for (int i = 0; i < verifySize; i++) {
            AffineTransform affine = new AffineTransform();
            Double anchorx = getAnchorx(w, verifySize);
            Double anchory = getAnchory(h);
            affine.setToRotation(Math.PI / 4 * RandomUtils.nextDouble() *
                            (RandomUtils.nextBoolean() ? 1 : -1),
                    anchorx * i + halfFontSize, anchory);
            g2.setTransform(affine);
            g2.drawChars(chars, i, 1, ((w - 10) / verifySize) * i + 5, h / 2 + fontSize / 2 - 10);
        }

        g2.dispose();
        ImageIO.write(image, "png", os);
    }

    public static Double getAnchory(int h) {
        double anchory = (double) h / 2;
        return anchory;
    }

    public static Double getAnchorx(int w, int verifySize) {
        double anchorx = (double) w / (double) verifySize;
        return anchorx;
    }


    private static Color getRandColor(int fc, int bc) {
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + RandomUtils.nextInt(0, bc - fc);
        int g = fc + RandomUtils.nextInt(0, bc - fc);
        int b = fc + RandomUtils.nextInt(0, bc - fc);
        return new Color(r, g, b);
    }

    private static int getRandomIntColor() {
        int[] rgb = getRandomRgb();
        int color = 0;
        for (int c : rgb) {
            color = color << 8;
            color = color | c;
        }
        return color;
    }

    private static int[] getRandomRgb() {
        int[] rgb = new int[3];
        for (int i = 0; i < 3; i++) {
            rgb[i] = RandomUtils.nextInt(0, 255);
        }
        return rgb;
    }

    private static void shear(Graphics g, int w1, int h1, Color color) {
        shearX(g, w1, h1, color);
        shearY(g, w1, h1, color);
    }

    private static void shearX(Graphics g, int w1, int h1, Color color) {

        int period = RandomUtils.nextInt(0, 2);

        boolean borderGap = true;
        int frames = 1;
        int phase = RandomUtils.nextInt(0, 2);

        for (int i = 0; i < h1; i++) {
            double d = (double) (period >> 1)
                    * Math.sin((double) i / (double) period
                    + (6.2831853071795862D * (double) phase)
                    / (double) frames);
            g.copyArea(0, i, w1, 1, (int) d, 0);
            if (borderGap) {
                g.setColor(color);
                g.drawLine((int) d, i, 0, i);
                g.drawLine((int) d + w1, i, w1, i);
            }
        }

    }

    private static void shearY(Graphics g, int w1, int h1, Color color) {

        int period = RandomUtils.nextInt(0, 40) + 10; // 50;

        boolean borderGap = true;
        int frames = 20;
        int phase = 7;
        for (int i = 0; i < w1; i++) {
            double d = (double) (period >> 1)
                    * Math.sin((double) i / (double) period
                    + (6.2831853071795862D * (double) phase)
                    / (double) frames);
            g.copyArea(i, 0, 1, h1, 0, (int) d);
            if (borderGap) {
                g.setColor(color);
                g.drawLine(i, (int) d, i, 0);
                g.drawLine(i, (int) d + h1, i, h1);
            }

        }
    }

}
