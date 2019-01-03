	public static String takeScreenshot(WebDriver webdriver) {
		acceptAlert(webdriver);
		logger.info("Take Screenshot ..");
		String data_uri = null;
		try {
			Thread.sleep(2000);
			data_uri = ((TakesScreenshot) webdriver).getScreenshotAs(OutputType.BASE64);
			data_uri = "data:image/gif;base64," + data_uri;
		} catch (Exception e) {
			logger.info("Exception while taking the Screenshot: " + e.getMessage());
		}
		return data_uri;
	}

	public static String captureScreenShot(String imageFile) {
		try {

			imageFile = imageFile.replaceAll("\\W", "_");
			logger.info("imageFile+" + imageFile);
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			Date date = new Date();
			imageFile = imageFile + "_" + dateFormat.format(date);
			Robot robot = new Robot();
			BufferedImage screenShot = robot
					.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
			String base64String = imgToBase64String(screenShot, "png");
			if ((imageFile == null) || (imageFile == "")) {
				long uTime = System.currentTimeMillis() / 1000L;
				imageFile = Long.toString(uTime) + ".jpg";
			}
			File filed = new File("C:\\AutomationImages");
			if (!filed.exists()) {
				if (filed.mkdir()) {
					logger.info("directory is created");
				}
			} else {
				logger.info("directory exist");
			}
			File newImageFile = new File("C:\\AutomationImages\\" + imageFile + ".jpg");
			javax.imageio.ImageIO.write(screenShot, "JPG", newImageFile);
			// return newImageFile.getName();
			String img = "</pre><img src=\"data:image/png;base64," + base64String + "\"><pre>";
			return img;

		} catch (AWTException e) {
			e.printStackTrace();
		} catch (IOException io) {
			io.printStackTrace();
		}
		// logger.info("[Error]: Failed to Capture screeshot");
		return "UNKNOWN";
	}