/*
 * Copyright (C), 2018-2019, 深圳拾年技术有限公司
 * FileName: GraphCodeUtil
 * Author:   lufeiwang
 * Date:   2019/5/7
 */
package com.sn.gz.pmp.dsc.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * 验证码
 *
 * @author lufeiwang
 * 2019/5/7
 */
public class GraphCodeUtil {
    /**
     * 验证码字符集（去掉了不好辨识的O、o、0）
     */
    private static final char[] CHARS = {
            '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm', 'n',
            'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N',
            'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    /**
     * 字符数量
     */
    private static final int SIZE = 4;
    /**
     * 干扰线数量
     */
    private static final int LINES = 5;
    /**
     * 宽度（之前为80）
     */
    private static final int WIDTH = 90;
    /**
     * 高度
     */
    private static final int HEIGHT = 40;
    /**
     * 字体大小
     */
    private static final int FONT_SIZE = 26;

    /**
     * 创建验证码图片
     *
     * @return java.lang.Object[]
     * @author Enma.ai
     * 2018/5/13
     */
    public static Object[] createGraphCode() throws IOException {
        StringBuilder code = new StringBuilder();
        // 1.创建空白图片
        BufferedImage image = new BufferedImage(
                WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        // 2.获取图片画笔
        Graphics graphic = image.getGraphics();
        // 3.设置画笔颜色
        graphic.setColor(Color.WHITE);
        // 4.绘制矩形背景
        graphic.fillRect(0, 0, WIDTH, HEIGHT);
        // 5.画随机字符
        Random ran = new Random();
        for (int i = 0; i < SIZE; i++) {
            // 取随机字符索引
            int n = ran.nextInt(CHARS.length);
            // 设置随机颜色
            graphic.setColor(getRandomColor());
            // 设置字体大小
            graphic.setFont(new Font(
                    null, Font.BOLD + Font.ITALIC, FONT_SIZE));
            // 画字符
            graphic.drawString(
                    CHARS[n] + "", i * WIDTH / SIZE, HEIGHT * 2 / 3);
            // 记录字符
            code.append(CHARS[n]);
        }
        // 6.画干扰线
        for (int i = 0; i < LINES; i++) {
            // 设置随机颜色
            graphic.setColor(getRandomColor());
            // 随机画线
            graphic.drawLine(ran.nextInt(WIDTH), ran.nextInt(HEIGHT),
                    ran.nextInt(WIDTH), ran.nextInt(HEIGHT));
        }
        // 7.返回图片和验证码
        return new Object[]{imageToBytes(image), code.toString()};
    }

    private static Color getRandomColor() {
        Random ran = new Random();
        return new Color(ran.nextInt(256),
                ran.nextInt(256), ran.nextInt(256));
    }

    /**
     * 转化图片格式
     *
     * @param image 突破
     * @return byte[]
     * @author Enma.ai
     * 2018/5/13
     */
    private static byte[] imageToBytes(BufferedImage image) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(image, "png", out);
        return out.toByteArray();
    }
}
