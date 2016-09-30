package com.wxhledu.cn.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 * 参考ref. http://www.tuicool.com/articles/yyIVVrz
 * @fun 产生图片验证码的工具类
 * @date 2016/8/12
 * @author 皮锋
 *
 */
public class CaptchaUtil {

	/**
	 * 随机字典字符，不包括0、O、1、I这些难以辨认的字符
	 */
	private static final char[] CHARS = { '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G',
			'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

	/**
	 * 随机数
	 */
	private static Random random = new Random();

	private CaptchaUtil() {
	}

	/**
	 * @fun 获得length位随机数
	 * @date 2016/8/12
	 * @author 皮锋
	 * @param length
	 */
	public static String getRandomString(int length) {
		StringBuffer buffer = new StringBuffer();// 字符串缓存
		for (int i = 0; i < length; i++) {// 循环length次
			// random.nextInt(n)方法调用返回介于0(含)和n(不含)伪随机，均匀分布的int值
			buffer.append(CHARS[random.nextInt(CHARS.length)]);// 每次获取一个随机字符
		}
		return buffer.toString();
	}

	/**
	 * @fun 获得随机的颜色
	 * @date 2016/8/12
	 * @author 皮锋
	 */
	private static Color getRandomColor() {
		return new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
	}

	/**
	 * @fun 获得某的颜色反色
	 * @date 2016/8/12
	 * @author 皮锋
	 * @param c
	 */
	private static Color getReverseColor(Color c) {
		return new Color(255 - c.getRed(), 255 - c.getGreen(), 255 - c.getBlue());
	}

	/**
	 * @fun 创建验证码图片
	 * @date 2016/8/12
	 * @author 皮锋
	 * @param width
	 * @param height
	 * @param os
	 * @param randomString
	 * @throws IOException
	 */
	public static void outputImage(int width, int height, OutputStream os, String randomString) throws IOException {
		int verifySize = randomString.length(); // 验证码长度
		Color color = getRandomColor();// 随机颜色，用于背景色
		Color reverse = getReverseColor(color);// 反色，用于前景色

		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);// 创建一个彩色图片

		Graphics2D g = bi.createGraphics();// 获取绘图对象

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // 设置图像抗锯齿
		g.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, height - 4));// 设置字体: Serif的意思是，在字的笔划开始及结束的地方有额外的装饰，而且笔划的粗细会因直横的不同而有不同。相反的，Sans Serif则没有这些额外的装饰，笔划粗细大致差不多
		g.setColor(color);// 设置颜色
		g.fillRect(0, 0, width, height);// 绘制背景:用这个颜色填充这个区域
		g.setColor(reverse);// 设置颜色

		for (int i = 0; i < verifySize; i++) { // 绘制验证码
			g.drawChars(randomString.toCharArray(), i, 1, ((width - 10) / verifySize) * i + 5,
					height / 2 + height / 2 - 10);
		}

		drawNoisePoint(width, height, 500, g); // 绘制最多1000个噪音点

		drawInterferingLine(width, height, 30, g); // 产生30条干扰线

		shear(width, height, g, color);// 使图片扭曲

		g.dispose();// 释放由此 Window、其子组件及其拥有的所有子组件所使用的所有本机屏幕资源

		ImageIO.write(bi, "jpg", os);
	}

	/**
	 * @fun 绘制最多num个噪音点
	 * @date 2016/8/12
	 * @author 皮锋
	 * @param width
	 * @param height
	 * @param num
	 * @param g
	 */
	private static void drawNoisePoint(int width, int height, int num, Graphics2D g) {
		for (int i = 0, n = random.nextInt(num); i < n; i++) {// 绘制最多num个噪音点
			g.drawRect(random.nextInt(width), random.nextInt(height), 1, 1);// 随机噪音点
		}
	}

	/**
	 * @fun 产生n条干扰线
	 * @date 2016/8/12
	 * @author 皮锋
	 * @param width
	 * @param height
	 * @param n
	 * @param g
	 */
	private static void drawInterferingLine(int width, int height, int n, Graphics2D g) {
		for (int i = 0; i < n; i++) {
			int x = random.nextInt(width - 1);
			int y = random.nextInt(height - 1);
			int xl = random.nextInt(6) + 1;
			int yl = random.nextInt(12) + 1;
			g.drawLine(x, y, x + xl + 40, y + yl + 20);
		}
	}

	/**
	 * @fun 使图片扭曲
	 * @date 2016/8/12
	 * @author 皮锋
	 * @param width
	 * @param height
	 * @param g
	 * @param color
	 */
	private static void shear(int width, int height, Graphics2D g, Color color) {
		shearX(g, width, height, color);
		shearY(g, width, height, color);
	}

	/**
	 * @fun 使图片Y轴方向扭曲
	 * @date 2016/8/12
	 * @author 皮锋
	 * @param g
	 * @param width
	 * @param height
	 * @param color
	 */
	private static void shearY(Graphics2D g, int width, int height, Color color) {
		int period = random.nextInt(40) + 10; // 周期
		int frames = 20; // 帧
		int phase = 7; // 相位
		for (int i = 0; i < width; i++) {
			double d = (double) (period >> 1)
					* Math.sin((double) i / (double) period + (Math.PI * 2 * (double) phase) / (double) frames);
			g.copyArea(i, 0, 1, height, 0, (int) d);
			g.setColor(color);
			g.drawLine(i, (int) d, i, 0);
			g.drawLine(i, (int) d + height, i, height);
		}
	}

	/**
	 * @fun 使图片X轴方向扭曲
	 * @date 2016/8/12
	 * @author 皮锋
	 * @param g
	 * @param width
	 * @param height
	 * @param color
	 */
	private static void shearX(Graphics2D g, int width, int height, Color color) {
		int period = random.nextInt(2);
		int frames = 1;
		int phase = random.nextInt(2);
		for (int i = 0; i < height; i++) {
			double d = (double) (period >> 1)
					* Math.sin((double) i / (double) period + (Math.PI * 2 * (double) phase) / (double) frames);
			g.copyArea(0, i, width, 1, (int) d, 0);
			g.setColor(color);
			g.drawLine((int) d, i, 0, i);
			g.drawLine((int) d + width, i, width, i);
		}
	}

	/**
	 * @fun 生成指定验证码图像文件
	 * @date 2016/8/12
	 * @author 皮锋
	 * @param width
	 * @param height
	 * @param file
	 * @param code
	 * @throws IOException
	 */
	public static void outputImageFile(int width, int height, File file, String code) throws IOException {
		if (file == null) {
			return;
		}
		File dir = file.getParentFile();// 获得父目录
		if (!dir.exists()) { // 如果父目录不存在，则创建一个
			dir.mkdirs();
		}
		try {
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			outputImage(width, height, fos, code); // 创建验证码图片
			fos.close(); // 关闭流
		} catch (IOException e) {
			throw e;
		}
	}

	public static void main(String[] args) throws IOException {
		File dir = new File("F:/verifies");
		int width = 200, height = 80;
		for (int i = 0; i < 50; i++) {
			String verifyCode = getRandomString(4);
			File file = new File(dir, verifyCode + ".jpg");
			outputImageFile(width, height, file, verifyCode);
		}
	}
}
