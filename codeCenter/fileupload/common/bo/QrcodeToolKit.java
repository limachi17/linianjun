package com.moxian.common.bo;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.moxian.common.BeanConfig.MxUploadFileConfig;
import com.moxian.common.config.ConfigUpload;

/**
 * 
 * @ClassName: QrcodeUtil
 * @Description: create qrcode
 * @author Shenghaohao sheng.haohao@moxiangroup.com
 * @Company moxian
 * @date 2015年3月9日 下午3:59:30
 *
 */
@Slf4j
public class QrcodeToolKit {
	
/*	@Inject
	private AvatarUploadConfig avatarUploadConfig;*/
	
	@Inject
	private static  MxUploadFileConfig mxUploadFileConfig;

	// 图片宽度的一般
	private static final int IMAGE_WIDTH = 200;
	private static final int IMAGE_HEIGHT = 200;
	private static final int IMAGE_HALF_WIDTH = IMAGE_WIDTH / 2;
	private static final int FRAME_WIDTH = 2;

	// 二维码写码器
	private static MultiFormatWriter mutiWriter = new MultiFormatWriter();

	/**
	 * 
	 * @param content
	 *            二维码显示的文本
	 * @param width
	 *            二维码的宽度
	 * @param height
	 *            二维码的高度
	 * @param srcImagePath
	 *            中间嵌套的图片
	 * @param destImagePath二维码生成的地址
	 * @Param type 1 个人 2 店铺
	 * 
	 */
//	public static String imageQrcode(String content, int width, int height,
//			String srcImagePath, String destImagePath, int type)
//			throws Exception {
//		String savePrefix="";
//		String returnPrefix="";
//		if (type == 1) {
///*			savePrefix=ConfigUpload.BASE_IMAGE_PATH+ConfigUpload.PAL_QRCODE_IMAGE_PATH;
//			returnPrefix=ConfigUpload.PAL_QRCODE_IMAGE_PATH;*/
//			String basePaths=mxUploadFileConfig.getBasepath();
//			savePrefix=ConfigUpload.BASE_IMAGE_PATH+ConfigUpload.PAL_QRCODE_IMAGE_PATH;
//			returnPrefix=ConfigUpload.PAL_QRCODE_IMAGE_PATH;
//			
//		} else if (type == 2) {
//			savePrefix=ConfigUpload.BASE_IMAGE_PATH+ConfigUpload.SHOP_QRCODE_IMAGE_PATH;
//			returnPrefix=ConfigUpload.SHOP_QRCODE_IMAGE_PATH;
//		}
//		String savePath = savePrefix + "/" + destImagePath;
//		String resultPath = returnPrefix + "/" + destImagePath;
//		File fileDir = new File(savePath);
//		if (!fileDir.exists()) {
//			fileDir.mkdirs();
//		}
//		// ImageIO.write 参数 1、BufferedImage 2、输出的格式 3、输出的文件
//		ImageIO.write(genBarcode(content, width, height, srcImagePath), "jpg",
//				new File(savePath));
//		return resultPath;
//	}
	
	public static String imageQrcodeKent(String content, int width, int height,
			String srcImagePath, String savePath,String basePath,String filename,
			String url)
			throws Exception {
		String lastsavePath = basePath + File.separatorChar + savePath+File.separatorChar+filename;
		String resultPath = "http://"+url+File.separatorChar+savePath+File.separatorChar+filename;
		File fileDir = new File(lastsavePath);
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}
		// ImageIO.write 参数 1、BufferedImage 2、输出的格式 3、输出的文件
		ImageIO.write(genBarcode(content, width, height, srcImagePath), "jpg",
				new File(lastsavePath));
		return resultPath;
	}
	
	
	public static String textQrcodeKent(String content, int width, int height, String savePath,String basePath,String filename,
			String url) throws Exception {
		String lastsavePath = basePath + File.separatorChar + savePath+File.separatorChar+filename;
		String resultPath = "http://"+url+File.separatorChar+savePath+File.separatorChar+filename;
		File fileDir = new File(lastsavePath);
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}
		// 二维码的图片格式
		log.debug("生成的返回路径"+resultPath);
		String format = "png";
		Map<EncodeHintType, Object> hint = new HashMap<EncodeHintType, Object>();
		hint.put(EncodeHintType.CHARACTER_SET, "utf-8");
		hint.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		
		BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
				BarcodeFormat.QR_CODE, width, height, hint);
		File outputFile = new File(lastsavePath);
		
		MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile);
		
		
		boolean wantSavePath = true; // 黄芳建想要保存路径，而不是需要url，所以暂时增加这段代码		
		if(wantSavePath){
			return savePath+File.separatorChar+filename;
		}else{
			return resultPath;
		}
		
		
	}


	/**
	 * @Title: textQrcode
	 * @param:
	 * @Description: 文本二维码
	 * @return void
	 */
//	public static String textQrcode(String content, int width, int height,
//			String destImagePath, int type) throws Exception {
//		String savePrefix="";
//		String returnPrefix="";
//		if (type == 1) {
//			savePrefix=ConfigUpload.BASE_IMAGE_PATH+ConfigUpload.PAL_QRCODE_IMAGE_PATH;
//			returnPrefix=ConfigUpload.PAL_QRCODE_IMAGE_PATH;
//		} else if (type == 2) {
//			savePrefix=ConfigUpload.BASE_IMAGE_PATH+ConfigUpload.SHOP_QRCODE_IMAGE_PATH;
//			returnPrefix=ConfigUpload.SHOP_QRCODE_IMAGE_PATH;
//		}
//		String savePath = savePrefix + "/" + destImagePath;
//		String resultPath = returnPrefix + "/" + destImagePath;
//		File fileDir = new File(savePath);
//		if (!fileDir.exists()) {
//			fileDir.mkdirs();
//		}
//		// 二维码的图片格式
//		String format = "png";
//		Map<EncodeHintType, Object> hint = new HashMap<EncodeHintType, Object>();
//		hint.put(EncodeHintType.CHARACTER_SET, "utf-8");
//		hint.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
//		BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
//				BarcodeFormat.QR_CODE, width, height, hint);
//		File outputFile = new File(savePath);
//		MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile);
//		return resultPath;
//	}

	/**
	 * 
	 * @Title: genBarcode
	 * @param: content 二维码显示的文本
	 * @param width
	 *            二维码的宽度
	 * @param height
	 *            二维码的高度
	 * @param srcImagePath
	 *            中间嵌套的图片
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @return BufferedImage
	 */
	private static BufferedImage genBarcode(String content, int width,
			int height, String srcImagePath) throws WriterException,
			IOException {
		// 读取源图像
		BufferedImage scaleImage = scale(srcImagePath, IMAGE_WIDTH,
				IMAGE_HEIGHT, false);
		int[][] srcPixels = new int[IMAGE_WIDTH][IMAGE_HEIGHT];
		for (int i = 0; i < scaleImage.getWidth(); i++) {
			for (int j = 0; j < scaleImage.getHeight(); j++) {
				srcPixels[i][j] = scaleImage.getRGB(i, j);
			}
		}

		Map<EncodeHintType, Object> hint = new HashMap<EncodeHintType, Object>();
		hint.put(EncodeHintType.CHARACTER_SET, "utf-8");
		hint.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		// 生成二维码
		BitMatrix matrix = mutiWriter.encode(content, BarcodeFormat.QR_CODE,
				width, height, hint);

		// 二维矩阵转为一维像素数组
		int halfW = matrix.getWidth() / 2;
		int halfH = matrix.getHeight() / 2;
		int[] pixels = new int[width * height];

		for (int y = 0; y < matrix.getHeight(); y++) {
			for (int x = 0; x < matrix.getWidth(); x++) {
				// 读取图片
				if (x > halfW - IMAGE_HALF_WIDTH
						&& x < halfW + IMAGE_HALF_WIDTH
						&& y > halfH - IMAGE_HALF_WIDTH
						&& y < halfH + IMAGE_HALF_WIDTH) {
					pixels[y * width + x] = srcPixels[x - halfW
							+ IMAGE_HALF_WIDTH][y - halfH + IMAGE_HALF_WIDTH];
				}
				// 在图片四周形成边框
				else if ((x > halfW - IMAGE_HALF_WIDTH - FRAME_WIDTH
						&& x < halfW - IMAGE_HALF_WIDTH + FRAME_WIDTH
						&& y > halfH - IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH
						+ IMAGE_HALF_WIDTH + FRAME_WIDTH)
						|| (x > halfW + IMAGE_HALF_WIDTH - FRAME_WIDTH
								&& x < halfW + IMAGE_HALF_WIDTH + FRAME_WIDTH
								&& y > halfH - IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH
								+ IMAGE_HALF_WIDTH + FRAME_WIDTH)
						|| (x > halfW - IMAGE_HALF_WIDTH - FRAME_WIDTH
								&& x < halfW + IMAGE_HALF_WIDTH + FRAME_WIDTH
								&& y > halfH - IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH
								- IMAGE_HALF_WIDTH + FRAME_WIDTH)
						|| (x > halfW - IMAGE_HALF_WIDTH - FRAME_WIDTH
								&& x < halfW + IMAGE_HALF_WIDTH + FRAME_WIDTH
								&& y > halfH + IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH
								+ IMAGE_HALF_WIDTH + FRAME_WIDTH)) {
					pixels[y * width + x] = 0xfffffff;
				} else {
					// 此处可以修改二维码的颜色，可以分别制定二维码和背景的颜色；
					pixels[y * width + x] = matrix.get(x, y) ? 0xff000000
							: 0xfffffff;
				}
			}
		}

		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		image.getRaster().setDataElements(0, 0, width, height, pixels);

		return image;
	}

	/**
	 * 
	 * @Title: scale
	 * @param srcImageFile
	 *            源文件地址
	 * @param height
	 *            目标高度
	 * @param width
	 *            目标宽度
	 * @param hasFiller
	 *            比例不对时是否需要补白：true为补白; false为不补白;
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @return BufferedImage
	 */
	private static BufferedImage scale(String srcImageFile, int height,
			int width, boolean hasFiller) throws IOException {
		double ratio = 0.0; // 缩放比例
		File file = new File(srcImageFile);
		BufferedImage srcImage = ImageIO.read(file);
		Image destImage = srcImage.getScaledInstance(width, height,
				BufferedImage.SCALE_SMOOTH);
		// 计算比例
		if ((srcImage.getHeight() > height) || (srcImage.getWidth() > width)) {
			if (srcImage.getHeight() > srcImage.getWidth()) {
				ratio = (new Integer(height)).doubleValue()
						/ srcImage.getHeight();
			} else {
				ratio = (new Integer(width)).doubleValue()
						/ srcImage.getWidth();
			}
			AffineTransformOp op = new AffineTransformOp(
					AffineTransform.getScaleInstance(ratio, ratio), null);
			destImage = op.filter(srcImage, null);
		}
		if (hasFiller) {// 补白
			BufferedImage image = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D graphic = image.createGraphics();
			graphic.setColor(Color.white);
			graphic.fillRect(0, 0, width, height);
			if (width == destImage.getWidth(null))
				graphic.drawImage(destImage, 0,
						(height - destImage.getHeight(null)) / 2,
						destImage.getWidth(null), destImage.getHeight(null),
						Color.white, null);
			else
				graphic.drawImage(destImage,
						(width - destImage.getWidth(null)) / 2, 0,
						destImage.getWidth(null), destImage.getHeight(null),
						Color.white, null);
			graphic.dispose();
			destImage = image;
		}
		return (BufferedImage) destImage;
	}

}
