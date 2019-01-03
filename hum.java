package com.vz.uui.pages;

import java.util.List; 
import java.util.Set;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.opera.core.systems.scope.protos.ExecProtos.ActionList.Action;
import com.vz.cip.pages.Task_Common;
import com.vz.irms.pages.IRMS_actions_Standalone;
import com.vz.login.pages.LoginPage;
import com.vz.wfm.pi.pages.PI_Milestones_Page;
import com.vz.wfm.pi.pages.PI_NFTree_Page;

import common_CIP.CIP_UUI_Fns;
import common_CIP.WebDriverUtil;
import extent_report.ENV;
import extent_report.ExtentTestManager;
import extent_report.JIRA_LoggerV2;
import extent_report.User_Action;
import extent_report.print_utilv2;

public class uui_actions_page {

	private Logger logger = Logger.getLogger(uui_actions_page.class);
	
	private String MAIN_PARENT_WINDOW_HANDLE = "NOT-YET-SET";
	private String NEW_CHILD_WINDOW_HANDLE = "NOT-YET-SET";
	private int RETRY_COUNT = 1;
	private int RETRY_GET = 1;
	private User_Action user_actions = new User_Action();
	private Task_Common task_common = new Task_Common();
	
	//Page Objects
	By ByLoc_UUI_Logo = By.xpath("//span[text()='CoFEE Engineering']");
	By ByLoc_UUI_Home = By.xpath("//a[text()='Home' and @class='active']");
	//By ByLoc_UUI_Home = By.xpath("//a[text()='Home']");
	By ByLoc_Tasks = By.xpath("(//a[text()='Tasks'])[2]");
	By ByLoc_MyTasks = By.xpath("(//a[text()='My Tasks'])[2]");
	By ByLoc_AvailableTasks = By.xpath("(//a[text()='Available Tasks'])[2]");
	String TaskList_Table_Header = "//*[@id='tasksdata_wrapper']/div[6]/div[1]/div/table/thead/tr/th";
	String TaskList_All_Rows_Xpath = "//*[@id='tasksdata']/tbody/tr";
	By ByLoc_TaskList_All_Rows_Xpath = By.xpath(TaskList_All_Rows_Xpath);
	By ByLoc_FloatingMenu_Strip = By.xpath("//div[@class='strip activeTaskStrip']");
	By ByLoc_SmallWindow_Submit_And_Open_Task = By.xpath("//button[text()='Submit And Open Task']");
	By ByLoc_SmallWindow_Acquire_And_Open_Task = By.xpath("//button[contains(text(),'Open Task')]");
	By ByLoc_FloatingMenu_3Dot = By.xpath("//li[contains(@class,'threeDotLi')]");
	String FloatingMenu_AcquireAndOpen = "//*[@id='AcquireAndOpenBonita']/span[2]";
	By ByLoc_FloatingMenu_AcquireAndOpen = By.xpath("//li[@id='AcquireAndOpenBonita']");
	By ByLoc_FloatingMenu_Open = By.xpath("//*[@id='OpenUUI']");
	By ByLoc_FloatingMenu_Open_1 = By.xpath("//span[text()='Open']");
	By ByLoc_FloatingMenu_Open_2 = By.xpath("//span[text()='Open']");
	By ByLoc_FloatingMenu_CancelNFID = By.xpath("(//li[@id='CancelNFID'])[2]");
	String Loading_Indicator_1 = "//*[@id='content-spinny2']/span";   // Spinny loader comes when user clicks on any option on Floating menu
	By ByLoc_TaskList_Grid_Filter = By.xpath("//*[@id='tasksdata_filter']/label/input");
	By ByLoc_DNAS_Header = By.xpath("(//pb-title/h2)[1]");
	By ByLoc_Menu_Projects = By.xpath("(//a[text()='Projects'])[2]");
	By ByLoc_SubMenu_AdvancedNFIDSearch = By.xpath("(//a[text()='Advanced NFID Search'])[2]");
	By ByLoc_SubMenu_NFTREE = By.xpath("(//a[text()='NF Tree'])[2]");
	By ByLoc_MainPage_Search_Dropdown = By.xpath("//*[@id='vzuui-searchDropdownBox']");
	By ByLoc_MainPage_Search_TextBox = By.xpath("//*[@id='vzuui-order-search-input-new']");
	By ByLoc_MainPage_Projects = By.xpath("(//a[text()='Projects'])[2]");
	By ByLoc_MainPage_CreateNetworkProject = By.xpath("(//a[text()='Create Network Project'])[2]");
	By ByLoc_Manage_Issues_Tab = By.xpath("//a[contains(text(),'Manage Issues')]");
	By ByLoc_Tags_Tab=By.xpath("//a[contains(text(),'TAGS')]");
	By ByLoc_PM = By.xpath("(//a[text()='PM'])[2]");
	By ByLoc_Programs = By.xpath("(//a[contains(text(),'Programs') and contains(@href,'programAdminList')])[2]");
	String locSmartJump=("//li[@id='CCPCCRSYSTEMPLANPAGE']");
	By ByLoc_Confirm_btn = By.xpath("//button[text()='Confirm']");
	By ByLoc_Acquire_And_Open_Pop_Up_WindowBox = By.xpath("//*[@id='acquirecontainer']");
	By ByLoc_Acquire_And_Open_Pop_Uo_DialogBox = By.xpath("//*[@id='dialog-task-action']");
	By ByLoc_My_Links_Advanced_NFID_Search = By.xpath("//div[@class='name' and text()='Advance NFID Search']");
	
	//IFRAME
	String iFrame_Task_Details = "iframe_content_taskDetails";
	String iFrame_Manage_Issues = "iframe_content_Manage_Issues";
	String iFrame_Tags_Tab="currentElement";
	String Processing_Indicator = "//*[@id='tasksdata_processing']";   // This is processing indicator when user goes on available/my tasks and it shows black screen
	
	/** Navigating to Create Network Project
	 * @param webdriver
	 * @return
	 */
	public boolean UUI2_NEVIATE_TO_CREATE_NETWORK_PROJECT(WebDriver webdriver){
		try{			
			user_actions.commonDelay(webdriver, 5);
			webdriver.switchTo().defaultContent();
			Actions moveElem = new Actions(webdriver);
			WebElement elem = webdriver.findElement(ByLoc_MainPage_Projects);
			moveElem.moveToElement(elem);
			WebElement SubMenu = webdriver.findElement(ByLoc_MainPage_CreateNetworkProject);
			moveElem.moveToElement(SubMenu);
			moveElem.click().build().perform();
			WebDriverUtil.waitForPageLoaded(webdriver);
			Thread.sleep(5000);
			Actions actions1 = new Actions(webdriver);
			WebElement moveonmenu3 = webdriver.findElement(ByLoc_UUI_Logo);
			actions1.moveToElement(moveonmenu3).click().perform();
			Thread.sleep(2000);
			webdriver.switchTo().defaultContent();
			user_actions.Switch_To_Second_Frame_On_CurrentPage(webdriver); 
			return true;
		}catch(Exception e){
			return false;
		}
	}

	/** Navigating to NFID search in Main
	 * @param webdriver
	 * @param NFID
	 * @return
	 */
	public boolean UUI2_NFID_SEARCH_MAIN(WebDriver webdriver,String NFID){
		try{
			if(GET_UUI_MAIN(webdriver) == false){return false;};
			user_actions.commonDelay(webdriver, 2);
			user_actions.waitForPageLoaded(webdriver);
			user_actions.commonDelay(webdriver, 2);
			user_actions.waitForPageLoaded(webdriver);
			webdriver.switchTo().defaultContent();
			user_actions.click(webdriver, ByLoc_UUI_Home);
			webdriver.switchTo().defaultContent();
			user_actions.commonDelay(webdriver, 2);
			user_actions.commonDelay(webdriver, 5);
			int size = webdriver.findElements(ByLoc_MainPage_Search_Dropdown).size();
			logger.info("Size : " + size);
			if(user_actions.js_executor_click(webdriver, ByLoc_MainPage_Search_Dropdown) == false){return false;}
			if(user_actions.Select_From_Dropdown(webdriver, ByLoc_MainPage_Search_Dropdown, "NFID") == false){return false;}
			user_actions.commonDelay(webdriver, 5);
			if(user_actions.sendKeys(webdriver, ByLoc_MainPage_Search_TextBox, NFID) == false){return false;}
			if(user_actions.sendKeys_KeyBoard(webdriver, ByLoc_MainPage_Search_TextBox, Keys.ENTER) == false){return false;}
			return true;
		}catch(Exception e){
			print_utilv2.ts_failed(webdriver, "UUI NFID Search from Main Page failing with Exception : " + e.getMessage());
			return false;
		}
	}
	
	/** Navigating to Advanced NFID search
	 * @param webdriver
	 * @return
	 */
	public boolean UUI2_NEVIGATE_TO_ADVANCED_NFID_SEARCH(WebDriver webdriver){
		try{
			if(GET_UUI_MAIN(webdriver) == false){return false;};
			user_actions.commonDelay(webdriver, 2);
			user_actions.waitForPageLoaded(webdriver);
			user_actions.commonDelay(webdriver, 2);
			user_actions.waitForPageLoaded(webdriver);
			webdriver.switchTo().defaultContent();
			user_actions.click(webdriver, ByLoc_UUI_Home);
			webdriver.switchTo().defaultContent();
			user_actions.commonDelay(webdriver, 2);
			user_actions.commonDelay(webdriver, 5);
			Actions moveElem = new Actions(webdriver);
			WebElement elem = webdriver.findElement(ByLoc_Menu_Projects);
			moveElem.moveToElement(elem);
			WebElement SubMenu = webdriver.findElement(ByLoc_SubMenu_AdvancedNFIDSearch);
			moveElem.moveToElement(SubMenu);
			moveElem.click().build().perform();
			WebDriverUtil.waitForPageLoaded(webdriver);
			Thread.sleep(5000);
			Actions actions1 = new Actions(webdriver);
			WebElement moveonmenu3 = webdriver.findElement(ByLoc_UUI_Logo);
			actions1.moveToElement(moveonmenu3).click().perform();
			user_actions.commonDelay(webdriver, 10);
			user_actions.Find_Frames_On_Page(webdriver);
			//user_actions.Switch_To_Second_Frame_On_CurrentPage(webdriver);
			user_actions.Switch_To_Specified_Frame_On_CurrentPage(webdriver, 3);
			user_actions.Find_Frames_On_Page(webdriver);
			return true;
		}catch(Exception e){
				print_utilv2.ts_failed(webdriver, "Exception to negivate to Advanced NFID Search Page - " + e.getMessage());
				return false;
		}
	
}
	
	/**Navigating to NFID Advanced Search Page via 
	 * MyLinks on Home page
	 * @param webdriver
	 */
	public boolean UUI2_MyLinks_NAVIGATE_TO_ADVANCED_NFID_SEARCH(WebDriver webdriver){
		try{
				if(GET_UUI_MAIN(webdriver) == false){return false;};
				user_actions.commonDelay(webdriver, 2);
				user_actions.waitForPageLoaded(webdriver);
				user_actions.commonDelay(webdriver, 2);
				user_actions.waitForPageLoaded(webdriver);
				webdriver.switchTo().defaultContent();
				user_actions.click(webdriver, ByLoc_UUI_Home);
				webdriver.switchTo().defaultContent();
				user_actions.commonDelay(webdriver, 2);
				user_actions.commonDelay(webdriver, 5);
				webdriver.switchTo().frame("page_0");
				webdriver.switchTo().frame("level_one_16082");
				user_actions.click(webdriver, ByLoc_My_Links_Advanced_NFID_Search);
				WebDriverUtil.waitForPageLoaded(webdriver);
				Thread.sleep(5000);
				user_actions.commonDelay(webdriver, 10);
				user_actions.Find_Frames_On_Page(webdriver);
				//user_actions.Switch_To_Second_Frame_On_CurrentPage(webdriver);
				user_actions.Switch_To_Specified_Frame_On_CurrentPage(webdriver, 3);
				user_actions.Find_Frames_On_Page(webdriver);
				return true;
		}catch(Exception e){
				print_utilv2.ts_failed(webdriver, "Exception to negivate to Advanced NFID Search Page - " + e.getMessage());
				return false;
		}
		
	}
	
	
	
	/** Navigating to NFTree
	 * @param webdriver 
	 * @return
	 */
	public boolean UUI2_NAVIGATE_TO_NFTREE(WebDriver webdriver){	
		try{
				if(GET_UUI_MAIN(webdriver) == false){return false;};
				user_actions.commonDelay(webdriver, 2);
				user_actions.waitForPageLoaded(webdriver);
				user_actions.commonDelay(webdriver, 2);
				user_actions.waitForPageLoaded(webdriver);
				webdriver.switchTo().defaultContent();
				user_actions.click(webdriver, ByLoc_UUI_Home);
				webdriver.switchTo().defaultContent();
				user_actions.commonDelay(webdriver, 2);
				user_actions.commonDelay(webdriver, 5);
				Actions moveElem = new Actions(webdriver);
				WebElement elem = webdriver.findElement(ByLoc_Menu_Projects);
				moveElem.moveToElement(elem);
				WebElement SubMenu = webdriver.findElement(ByLoc_SubMenu_NFTREE);
				moveElem.moveToElement(SubMenu);
				moveElem.click().build().perform();
				WebDriverUtil.waitForPageLoaded(webdriver);
				Thread.sleep(5000);
				Actions actions1 = new Actions(webdriver);
				WebElement moveonmenu3 = webdriver.findElement(ByLoc_UUI_Logo);
				actions1.moveToElement(moveonmenu3).click().perform();
				user_actions.commonDelay(webdriver, 10);
				user_actions.Find_Frames_On_Page(webdriver);
				int frames_size = webdriver.findElements(By.tagName("iframe")).size();
				int search_in_frames = frames_size;
				if(frames_size > 5){
					search_in_frames = 5;
				}
				IRMS_actions_Standalone nftree_page = new IRMS_actions_Standalone();
				nftree_page.Verify_NFID_Textbox_is_present_in_Current_Frame(webdriver, search_in_frames);
					
				//user_actions.Switch_To_Second_Frame_On_CurrentPage(webdriver);
				return true;
		}catch(Exception e){
				print_utilv2.ts_failed(webdriver, "Exception to negivate to NF Tree - " + e.getMessage());
				return false;
		}
	}

	/** Searching for NFID in NFTree
	 * @param webdriver
	 * @param NFID
	 * @return
	 */
	public boolean NFIDsearch_NFTREE(WebDriver webdriver, String NFID) {
		IRMS_actions_Standalone NFTree_search = new IRMS_actions_Standalone();
		if(NFTree_search.Enter_NFID_In_NFID_TextBox(webdriver, NFID)==false) {return false;}
		if(NFTree_search.Click_On_Search_Button(webdriver)==false){return false;}
		if(NFTree_search.Navigate_to_Manage_Issues_Tab(webdriver)==false) {return false;}
		return true;
	}
	
	/** Searching for NFID in NFID server URL
	 * @param webdriver
	 * @param NFID
	 * @param NFTreeURL
	 * @return
	 */
	public boolean NFIDsearch_NFTREE_ServerURL(WebDriver webdriver, String NFID, String NFTreeURL) {
		try {
			if (GET_UUI_MAIN(webdriver) == false) {
				return false;
			}
			print_utilv2.ts_debug("Trying to Get : <a href='" + NFTreeURL + "'>URL</a> : " + NFTreeURL);
			webdriver.get(NFTreeURL);
			user_actions.commonDelay(webdriver, 2);
			user_actions.waitForPageLoaded(webdriver);
			user_actions.commonDelay(webdriver, 2);
			user_actions.waitForPageLoaded(webdriver);
			IRMS_actions_Standalone NFTree_search = new IRMS_actions_Standalone();
			if (NFTree_search.Enter_NFID_In_NFID_TextBox(webdriver, NFID) == false) {
				return false;
			}
			if (NFTree_search.Click_On_Search_Button(webdriver) == false) {
				return false;
			}
			if (NFTree_search.Navigate_to_Manage_Issues_Tab(webdriver) == false) {
				return false;
			}
			return true;
		} catch (Exception e) {
			print_utilv2.ts_failed(webdriver,
					"Getting exception at NFIDsearch_NFTREE_ServerURL function in uui_actions page");
			e.printStackTrace();
			return false;
		}
	}

	/** Searching for NFID in NFTree and validating status
	 * @param webdriver
	 * @param NFID
	 * @return
	 */
	public boolean NFIDsearch_NFTREE_NFID_Status(WebDriver webdriver, String NFID) {
		IRMS_actions_Standalone NFTree_search = new IRMS_actions_Standalone();
		if(NFTree_search.Enter_NFID_In_NFID_TextBox(webdriver, NFID)==false) {return false;}
		if(NFTree_search.Click_On_Search_Button(webdriver)==false){return false;}
		if(NFTree_search.get_NFID_Status(webdriver)==false) {
			return false;}
		return true;
	}

	/** Navigating to Advanced NFID screen with NFID
	 * @param webdriver
	 * @param NFID
	 * @return
	 */
	public boolean UUI2_ADVANCED_NFID_SEARCH(WebDriver webdriver, String NFID){	
		try{
				if(UUI2_MyLinks_NAVIGATE_TO_ADVANCED_NFID_SEARCH(webdriver) == false){return false;}
				Advanced_NFID_Search_Page Advanced_NFID_Page = new Advanced_NFID_Search_Page();
				if(Advanced_NFID_Page.Is_NFID_TextBox_Present(webdriver) == false){
					JIRA_LoggerV2.FAILEDv2("PPM-PI", "2017", "October", "Coverage", "UUI User should be able to Nevigate to Advanced NFID Search page", "FVY-6189", Thread.currentThread().getStackTrace()[1].getClassName());
					ExtentTestManager.Append_Test_Name("[Advanced_NFID_Search_Page_Not_Loaded]");
					return false;
				}else{
					JIRA_LoggerV2.PASSEDv2("PPM-PI", "2017", "October", "Coverage", "UUI User should be able to Nevigate to Advanced NFID Search page", "FVY-6189", Thread.currentThread().getStackTrace()[1].getClassName());
				}

				if(Advanced_NFID_Page.Enter_NFID_In_NFID_TextBox(webdriver, NFID) == false){
					JIRA_LoggerV2.FAILEDv2("PPM-PI", "2017", "October", "Coverage", "UUI Advanced NFID Search User should be able to search with NFID", "FVY-6190", Thread.currentThread().getStackTrace()[1].getClassName());
					ExtentTestManager.Append_Test_Name("[Advanced_NFID_Search_Cant_Enter_NFID]");
					return false;
				}else{
					JIRA_LoggerV2.PASSEDv2("PPM-PI", "2017", "October", "Coverage", "UUI Advanced NFID Search User should be able to search with NFID", "FVY-6190", Thread.currentThread().getStackTrace()[1].getClassName());
				}
				
				if(Advanced_NFID_Page.Click_On_Search(webdriver) == false){
					JIRA_LoggerV2.FAILEDv2("PPM-PI", "2017", "October", "Coverage", "Advanced NFID Search button should be enabled", "FVY-6191", Thread.currentThread().getStackTrace()[1].getClassName());
					ExtentTestManager.Append_Test_Name("[Advanced_NFID_Search_Click_On_Search_Fails]");
					return false;
				}else{
					JIRA_LoggerV2.PASSEDv2("PPM-PI", "2017", "October", "Coverage", "Advanced NFID Search button should be enabled", "FVY-6191", Thread.currentThread().getStackTrace()[1].getClassName());
				}
				
				if(Advanced_NFID_Page.Is_NFID_Search_Showing_NFID(webdriver, NFID) == false){
					JIRA_LoggerV2.FAILEDv2("PPM-PI", "2017", "October", "Coverage", "Advanced NFID Search results should show NFID searched", "FVY-6192", Thread.currentThread().getStackTrace()[1].getClassName());
					ExtentTestManager.Append_Test_Name("[Advanced_NFID_Search_Not_Showing_Results]");
					return false;
				}else{
					JIRA_LoggerV2.PASSEDv2("PPM-PI", "2017", "October", "Coverage", "Advanced NFID Search results should show NFID searched", "FVY-6192", Thread.currentThread().getStackTrace()[1].getClassName());
				}
				return true;
		}catch(Exception e){
			print_utilv2.ts_failed(webdriver, "Exception During Advanced NFID Search : " + e.getMessage());
			return false;	
		}
	}

	/** Canceling NFID thorugh ADv NFID search
	 * @param webdriver
	 * @param NFID
	 * @return
	 */
	public boolean Cancel_NFID_From_Advanced_NFID_Search_Page(WebDriver webdriver, String NFID){
		if(UUI2_ADVANCED_NFID_SEARCH(webdriver,NFID) == false){return false;}
		Advanced_NFID_Search_Page Advanced_NFID_Page = new Advanced_NFID_Search_Page();
		if(Advanced_NFID_Page.Click_On_3Dot_Row1(webdriver) == false){return false;}
		if(Advanced_NFID_Page.Click_On_Cancel(webdriver) == false){return false;}
		if(Advanced_NFID_Page.Select_Cancel_NFID_Reason(webdriver) == false){return false;}
		if(Advanced_NFID_Page.Is_Cancel_Successfull_Message_Displayed(webdriver) == false){return false;}
		ExtentTestManager.Append_Test_Name("CANCELLED");
		return true;
	}

	/** Cancel NFID from Adv NFID screen
	 * @param webdriver
	 * @param NFID
	 * @param Task_Name
	 * @return
	 */
	public boolean UUI2_CANCEL_NFID(WebDriver webdriver,String NFID,String Task_Name){
		
		//Find the task and make sure its present in task list - tries 50 times
		print_utilv2.ts_debug("Trying to Cancel For NFID : " + NFID +" Task_Name : " + Task_Name);

		if(UUI2_NEVIGATE_TO_AVAILABLE_TASKS_50TimesFind(webdriver,NFID, Task_Name) == false){
			print_utilv2.ts_failed(webdriver,"Task not coming in Available Tasks - Failure");
			return false;
		}

		if(UUI2_ENTER_NFID_IN_SEARCH_FIELD_AVAILABLE_TASKS(webdriver,NFID) == false){
			print_utilv2.ts_failed(webdriver,"Not able to enter NFID in search field - Failure");
			return false;
		}

		if(UUI2_GET_FLOATING_MENU_FOR_SPECIFIC_TASK_NAME(webdriver,Task_Name) == false){
			print_utilv2.ts_failed(webdriver,"UUI2_ACQUIRE_AND_OPEN - Failed to get floating menu for Task : " + Task_Name);
			return false;
		}

		if(UUI2_ON_FLOATING_MENU_CLICK_ON_OPTION_CANCEL(webdriver) == false){
			print_utilv2.ts_failed(webdriver,"UUI2_CANCEL_NFID - Failed to click on option of FLoating menu : Cancel NFID  , " + Task_Name);
			ExtentTestManager.Append_Test_Name("[UUI Cancel NFID Failed {" + Task_Name + "}]");
			return false;
		}
		
		user_actions.Find_Frames_On_Page(webdriver);
		user_actions.Switch_To_First_Frame_On_CurrentPage(webdriver);
		
		if(user_actions.click(webdriver, ByLoc_Confirm_btn) == false){
			print_utilv2.ts_failed(webdriver, "Failed to click on Confirm on Cancel NFID Widget");
			return false;
		}
		user_actions.commonDelay(webdriver, 10);
		ExtentTestManager.Append_Test_Name("CANCELLED");
		return true;		
	}
	
	/** Navigating to PM Programs
	 * @param webdriver
	 * @return
	 */
	public boolean UUI2_NEVIGATE_TO_PM_PROGRAMS(WebDriver webdriver){
		try{
				if(GET_UUI_MAIN(webdriver) == false){return false;};
				user_actions.commonDelay(webdriver, 2);
				user_actions.waitForPageLoaded(webdriver);
				user_actions.commonDelay(webdriver, 2);
				user_actions.waitForPageLoaded(webdriver);
				WebDriverWait wait = new WebDriverWait(webdriver, 60);
				WebElement menuLink = wait.until(ExpectedConditions.presenceOfElementLocated(ByLoc_Programs));	
				JavascriptExecutor executor = (JavascriptExecutor) webdriver;
				executor.executeScript("arguments[0].click();", menuLink);
				user_actions.waitForPageLoaded(webdriver);
				user_actions.commonDelay(webdriver, 40);
				user_actions.Switch_To_Second_Frame_On_CurrentPage(webdriver);		
				return true;
		}catch(Exception e){
			print_utilv2.ts_failed(webdriver, "Exception During Advanced NFID Search : " + e.getMessage());
			return false;
		}
	}	
	
	/** Acquire and open trial for multiple times
	 * @param webdriver
	 * @param NFID
	 * @param Task_Name
	 * @return
	 */
	public boolean UUI2_ACQUIRE_AND_OPEN_AND_SWITCH_MULTIPLE(WebDriver webdriver, String NFID, String Task_Name){	
		int count = 1;
		while(true){
			if(UUI2_ACQUIRE_AND_OPEN_AND_SWITCH(webdriver, NFID, Task_Name)){
				print_utilv2.ts_passed("Multiple windows are open now");
				return true;
			}else{
				count++;
				print_utilv2.ts_debug_ss(webdriver,"Failure during Acquire and Open Process. Retry " + count);
				if(count > 5){
					ExtentTestManager.Append_Test_Name("[UUI_ACQUIRE_OPEN_FAILING]");
					print_utilv2.tc_failed(webdriver,"Retried UUI2_ACQUIRE_AND_OPEN_AND_SWITCH multiple times - but its still not working");
					return false;
				}
			}
		}
	}
	
	public boolean UUI2_OPEN_AND_SWITCH_MULTIPLE(WebDriver webdriver, String NFID, String Task_Name){	
		int count = 1;
		while(true){
			if(UUI2_OPEN_AND_SWITCH(webdriver, NFID, Task_Name)){
				print_utilv2.ts_passed("Multiple windows are open now");
				return true;
			}else{
				count++;
				print_utilv2.ts_debug_ss(webdriver,"Failure during Acquire and Open Process. Retry " + count);
				if(count > 5){
					ExtentTestManager.Append_Test_Name("[UUI_ACQUIRE_OPEN_FAILING]");
					print_utilv2.tc_failed(webdriver,"Retried UUI2_ACQUIRE_AND_OPEN_AND_SWITCH multiple times - but its still not working");
					return false;
				}
			}
		}
	}
	
	/** This will Acquire, open and Switch to Child Window
	 * @param webdriver
	 * @param NFID
	 * @param Task_Name
	 * @return
	 */
	public boolean UUI2_ACQUIRE_AND_OPEN_AND_SWITCH(WebDriver webdriver, String NFID, String Task_Name){
		if(UUI2_ACQUIRE_AND_OPEN(webdriver, NFID, Task_Name) == false){
			for(int retryCount = 0; retryCount < 3; retryCount++) {	
			if(UUI2_NEVIGATE_TO_AVAILABLE_TASKS_50TimesFind(webdriver, NFID,Task_Name) == false){
				print_utilv2.ts_failed(webdriver, "Task not coming in UUI Task List");
				continue;
			}
			if(UUI2_ACQUIRE_AND_OPEN_After_TaskFind(webdriver, NFID, Task_Name)) {
				break;
			}
			if (retryCount == 2) {
				print_utilv2.ts_failed(webdriver, "Unable to acquire and open task even after trying multiple times/repeat search");
				return false;
			}
		}
		}		if(SWITCH_TO_NEW_WINDOW(webdriver) == false){return false;}
		return true;
	}

	public boolean UUI2_OPEN_AND_SWITCH(WebDriver webdriver, String NFID, String Task_Name){
		if(UUI2_OPEN(webdriver, NFID, Task_Name) == false){return false;}
		if(SWITCH_TO_NEW_WINDOW(webdriver) == false){return false;}
		return true;
	}

	/** This will acquire and open tasks
	 * @param webdriver
	 * @param NFID
	 * @param Task_Name
	 * @return
	 */
	public boolean UUI2_ACQUIRE_AND_OPEN(WebDriver webdriver, String NFID, String Task_Name) {
		print_utilv2.ts_debug("Trying to Acquire and Open for task : " + Task_Name);
		if(UUI2_NEVIGATE_TO_AVAILABLE_TASKS_50TimesFind(webdriver,NFID, Task_Name) == false){
			print_utilv2.ts_failed(webdriver,"Task not coming in Available Tasks - Failure");
			return false;
		}
		if(UUI2_ENTER_NFID_IN_SEARCH_FIELD_AVAILABLE_TASKS(webdriver,NFID) == false){
			print_utilv2.ts_failed(webdriver,"Not able to enter NFID in search field - Failure");
			return false;
		}
		if(UUI2_GET_FLOATING_MENU_FOR_SPECIFIC_TASK_NAME(webdriver,Task_Name) == false){
			print_utilv2.ts_failed(webdriver,"UUI2_ACQUIRE_AND_OPEN - Failed to get floating menu for Task : " + Task_Name);
			return false;
		}
		if(UUI2_ON_FLOATING_MENU_CLICK_OPEN(webdriver, NFID, Task_Name) == false){
			print_utilv2.ts_failed(webdriver,"UUI2_ACQUIRE_AND_OPEN - Failed to click on option of FLoating menu : Acquire And Open  , " + Task_Name);
			return false;
		}
		if(user_actions.WAIT_TILL_TWO_WINDOWS_ARE_OPEN(webdriver) == false){
			for (int i = 0; i < 4; i++) {
				print_utilv2.ts_debug("New window did not open for task: " + Task_Name + "Trying again #" + i );
				if(UUI2_SMALL_WINDOW_SUBMIT_AND_OPEN_TASK(webdriver, NFID, Task_Name) == false) {continue;}
				if(user_actions.WAIT_TILL_TWO_WINDOWS_ARE_OPEN(webdriver) == true) {break;}
				if(i == 3) {return false;}
			}
		};
		return true;	
	}
	
	
	public boolean UUI2_OPEN(WebDriver webdriver, String NFID, String Task_Name) {
		print_utilv2.ts_debug("Trying to Acquire and Open for task : " + Task_Name);
		if(UUI2_NEVIGATE_TO_AVAILABLE_TASKS_50TimesFind(webdriver,NFID, Task_Name) == false){
			print_utilv2.ts_failed(webdriver,"Task not coming in Available Tasks - Failure");
			return false;
		}
		if(UUI2_ENTER_NFID_IN_SEARCH_FIELD_AVAILABLE_TASKS(webdriver,NFID) == false){
			print_utilv2.ts_failed(webdriver,"Not able to enter NFID in search field - Failure");
			return false;
		}
		if(UUI2_GET_FLOATING_MENU_FOR_SPECIFIC_TASK_NAME(webdriver,Task_Name) == false){
			print_utilv2.ts_failed(webdriver,"UUI2_ACQUIRE_AND_OPEN - Failed to get floating menu for Task : " + Task_Name);
			return false;
		}
		if(UUI2_ON_FLOATING_MENU_CLICK_OPEN(webdriver, NFID, Task_Name) == false){
			print_utilv2.ts_failed(webdriver,"UUI2_ACQUIRE_AND_OPEN - Failed to click on option of FLoating menu : Acquire And Open  , " + Task_Name);
			return false;
		}
		if(user_actions.WAIT_TILL_TWO_WINDOWS_ARE_OPEN(webdriver) == false){return false;};
		return true;	
	}
	
	
	/**
	 * @param webdriver
	 * @param This
	 * will acquire and open tasks
	 * @return
	 * @author Arpit Patel
	 */
	public boolean UUI2_ACQUIRE_AND_OPEN_After_TaskFind(WebDriver webdriver, String NFID, String Task_Name) {
		
		boolean Status = true;
		print_utilv2.ts_debug_ss(webdriver, "Trying to Acquire and Open task - Start");
		Status = UUI2_GET_FLOATING_MENU_FOR_SPECIFIC_TASK_NAME(webdriver, Task_Name);
		if (!Status) {
			print_utilv2.ts_failed(webdriver, "UUI2_ACQUIRE_AND_OPEN - Failed to get floating menu for Task : "
					+ Task_Name);
		}
		if (Status) {
			Status = UUI2_ON_FLOATING_MENU_CLICK_ON_OPTION_ACQUIRE_AND_OPEN(webdriver, NFID, Task_Name);
			if (!Status) {
				print_utilv2.ts_failed(webdriver,
						"UUI2_ACQUIRE_AND_OPEN - Failed to click on option of FLoating menu : Acquire And Open  , "
								+ Task_Name);
				return false;
			} else {
				print_utilv2.ts_passed("Able to successfully Acquire and Open task card");
			}
		}
		if (user_actions.WAIT_TILL_TWO_WINDOWS_ARE_OPEN(webdriver) == false) {return false;}
		if (SWITCH_TO_NEW_WINDOW(webdriver) == false) {
			return false;
		}
		return true;
	}
	
	/** Switching to New Window after acquire and open
	 * @param webdriver
	 * @return
	 */
	public boolean SWITCH_TO_NEW_WINDOW(WebDriver webdriver){
		print_utilv2.ts_debug("Trying to Switching to new window - After Acquire and Open / Or / Open");
		Set<String> AllWindowHandles = webdriver.getWindowHandles();
		String MAIN_WINDOW = (String) AllWindowHandles.toArray()[0];
		System.out.print("Total Windows Open = " + AllWindowHandles.size());
		if(AllWindowHandles.size() == 1){
			print_utilv2.ts_debug("There is only one window open - Something wrong in UUI");
			return false;
		}
		System.out.print("MAIN_WINDOW handle = "+AllWindowHandles.toArray()[0]);
		NEW_CHILD_WINDOW_HANDLE = (String) AllWindowHandles.toArray()[1];
		System.out.print("NEW_CHILD_WINDOW_HANDLE handle code = "+AllWindowHandles.toArray()[1]);
		webdriver.switchTo().window(NEW_CHILD_WINDOW_HANDLE);
		logger.info("NEW_CHILD_WINDOW  title : " + webdriver.getTitle());
		print_utilv2.ts_debug("Switched to new Child window...");
		return true;
	}

	/** To Close Child Window tab
	 * @param webdriver
	 * @return
	 */
	public boolean CLOSE_CHILD_WINDOW_TAB(WebDriver webdriver){
		try{
			logger.info("Closing child window " + NEW_CHILD_WINDOW_HANDLE);
			webdriver.close();
			print_utilv2.ts_debug("Child window closed...");
			return true;
		}catch(Exception e){
			print_utilv2.ts_failed(webdriver,"Exception while closing child window..");
			e.printStackTrace();
			return false;
		}
	}

	/** Switching back to Main Window
	 * @param webdriver
	 * @return
	 */
	public boolean SWITCH_TO_MAIN_WINDOW(WebDriver webdriver){
		webdriver.switchTo().window(MAIN_PARENT_WINDOW_HANDLE);
		print_utilv2.ts_debug("Switched to Main Parent Widnwo");
		return true;
	}
	
    /** Checking conitnously in My Tasks and Available task for a specific task associated to NFID
     * @param webdriver
     * @param NFID
     * @param Task_Name
     * @return
     */
    public boolean UUI2_NEVIGATE_TO_AVAILABLE_TASKS_50TimesFind(WebDriver webdriver, String NFID, String Task_Name){
		int count = RETRY_COUNT;
		int Max_Retry = 15;
		try{
			logger.info(Max_Retry + " times Checking in My Tasks and Available Tasks to see if task is showing up for NFID : " + NFID  + " Task Name : " + Task_Name);
			int total_rows = 0;
			boolean task_match_found = false;
			int match_found_at_row = 1;
			Actions actions1 = new Actions(webdriver);	
			while(!task_match_found){				
			if((count%2)== 0){
				if(GET_UUI_MAIN(webdriver) == false){return false;};
				user_actions.commonDelay(webdriver, 2);
				user_actions.waitForPageLoaded(webdriver);
				user_actions.commonDelay(webdriver, 2);
				user_actions.waitForPageLoaded(webdriver);
				user_actions.click(webdriver, ByLoc_UUI_Home);
				print_utilv2.ts_debug("Checking for Task to Show up on My Tasks - Try : " + count  + " - NFID : " + NFID  + " Task Name : " + Task_Name);
				webdriver.switchTo().defaultContent();
				user_actions.commonDelay(webdriver, 2);
				user_actions.wait4XpathPresent2(webdriver, ByLoc_Tasks, 20);
				
				Actions Retry_Action = new Actions(webdriver);
				WebElement mainMenu = webdriver.findElement(ByLoc_Tasks);
				Retry_Action.moveToElement(mainMenu);
				WebElement SubMenu = webdriver.findElement(ByLoc_MyTasks);
				Retry_Action.moveToElement(SubMenu);
				Retry_Action.click().build().perform();
				
				user_actions.commonDelay(webdriver, 2);
				Thread.sleep(2000);
				WebElement moveonmenu3 = webdriver.findElement(ByLoc_UUI_Logo);
				actions1.moveToElement(moveonmenu3).click().perform();
				Thread.sleep(2000);
			}else{
				if(GET_UUI_MAIN(webdriver) == false){return false;};
				user_actions.commonDelay(webdriver, 2);
				user_actions.waitForPageLoaded(webdriver);
				user_actions.commonDelay(webdriver, 2);
				user_actions.click(webdriver, ByLoc_UUI_Home);
				print_utilv2.ts_debug("Checking for Task to Show up on Available Tasks - Try : " + count   + " - NFID : " + NFID  + " Task Name : " + Task_Name);
				webdriver.switchTo().defaultContent();
				user_actions.commonDelay(webdriver, 2);
				user_actions.wait4XpathPresent2(webdriver, ByLoc_Tasks, 20);				
				Actions Retry_Action = new Actions(webdriver);
				WebElement mainMenu = webdriver.findElement(ByLoc_Tasks);
				Retry_Action.moveToElement(mainMenu);
				WebElement SubMenu = webdriver.findElement(ByLoc_AvailableTasks);
				Retry_Action.moveToElement(SubMenu);
				Retry_Action.click().build().perform();
				user_actions.commonDelay(webdriver, 2);
				Thread.sleep(2000);
				WebElement moveonmenu3 = webdriver.findElement(ByLoc_UUI_Logo);
				actions1.moveToElement(moveonmenu3).click().perform();
				Thread.sleep(2000);
			}
			logger.info("Page Ready");
			user_actions.commonDelay(webdriver, 3);
			user_actions.AJAX_LOOP(webdriver, Processing_Indicator);
			int search_in_frames = Get_Frames_Count_on_Current_Page(webdriver, 5);
			Verify_Presence_of_Element_And_Navigate_to_Frame(webdriver, search_in_frames, ByLoc_TaskList_Grid_Filter);
			
				if(user_actions.wait4XpathPresent2(webdriver, ByLoc_TaskList_Grid_Filter, 200) == false){
					print_utilv2.ts_debug_ss(webdriver,"Grid Filter not coming after waiting for 200 seconds");
					throw new Exception("Exception During wait4XpathPresent2 Wait for Task Data Filter");
				}
				if(user_actions.sendKeys(webdriver, ByLoc_TaskList_Grid_Filter, NFID) == false){
					print_utilv2.ts_debug_ss(webdriver,"Getting Error trying to send NFID to Search Box in UUI");
					throw new Exception("Exception During Trying to send NFID to Search Filed on My/Availbalbe Tasks");
				}else{
					logger.info("NFID Entered Successfully in Search Box in UUI");
				}
				user_actions.sendKeys_KeyBoard(webdriver, ByLoc_TaskList_Grid_Filter, Keys.ENTER);
				waitTillProcessing(webdriver);
				user_actions.commonDelay(webdriver, 5);
				List<WebElement> rows = webdriver.findElements(ByLoc_TaskList_All_Rows_Xpath);
				total_rows = rows.size();
				int row_num,col_num;
				user_actions.commonDelay(webdriver, 2);
				row_num=1;
				for(WebElement trElement : rows)
				{
					List<WebElement> td_collection = trElement.findElements(By.xpath("td"));
					logger.info("NUMBER OF COLUMNS=" + td_collection.size() + " - My Task [" + Task_Name + "]"
							+ " >>> NFID:" + NFID);
					col_num = 1;
					for (WebElement tdElement : td_collection) {
						logger.info("row # " + row_num + ", col # " + col_num + "text=" + tdElement.getText());
						if (tdElement.getText().equalsIgnoreCase(NFID)) {
							col_num = 1;
							for (WebElement tdElement1 : td_collection) {
								logger.info("row # " + row_num + ", col # " + col_num + "text=" + tdElement1.getText());
							if (tdElement1.getText().equalsIgnoreCase(Task_Name)) {
								match_found_at_row = row_num;
								task_match_found = true;
								break;
							}
							col_num++;
						}	
					}
						col_num++;
						if(task_match_found) break;						
				}
					row_num++;
					if(task_match_found) break;
				}

				count ++;
				RETRY_COUNT++;
				logger.info("count " + count );
				logger.info("RETRY_COUNT " + RETRY_COUNT );
				print_utilv2.ts_debug("Retrying task find : RETRY_COUNT" + RETRY_COUNT  + " Count : " + count);
				if(count > Max_Retry){
					ExtentTestManager.Append_Test_Name("[TASK {" + Task_Name + "} NOT COMING IN UUI]");
					print_utilv2.tc_failed(webdriver,Max_Retry + " time Tried to wait for task to appear in UUI - However task is not coming in task list - Please check this manually[" + Task_Name +"]" + " >>> NFID:" + NFID);
					return false;
				}
			}
			print_utilv2.ts_debug("Task is coming in the UUI Task List : Task Name - [" + Task_Name + "]" + " >>> NFID:" + NFID);
		}catch(Exception e){
			if(count > Max_Retry){
				ExtentTestManager.Append_Test_Name("[UUI_ENV_ISSUE_FIND_TASK]");
				print_utilv2.tc_failed(webdriver,Max_Retry + "Time retried - but still getting exception - Returning False");
				e.printStackTrace();
				RETRY_COUNT = 1;
				return false;
			}else{
				print_utilv2.ts_debug_ss(webdriver,"Getting exception - retrying - UUI2_NEVIGATE_TO_AVAILABLE_TASKS_50TimesFind");
				e.printStackTrace();
				RETRY_COUNT ++;
				UUI2_NEVIGATE_TO_AVAILABLE_TASKS_50TimesFind(webdriver, NFID, Task_Name);
			}
		}
		RETRY_COUNT = 1;
		return true;
	}
    
   
	/**
	 * To get total number of frames on current page which will be later used to navigate to to the frame where element is located
	 * @param webdriver
	 * @param maximumframes_allowed This is useful to set the maximum frame count
	 * @return
	 */
	public int Get_Frames_Count_on_Current_Page(WebDriver webdriver, int maximumframes_allowed) {
		try {
			 webdriver.switchTo().defaultContent();
				user_actions.Find_Frames_On_Page(webdriver);
				int frames_size = webdriver.findElements(By.tagName("iframe")).size();
				int search_in_frames = frames_size;
				if(frames_size > maximumframes_allowed){
					search_in_frames = maximumframes_allowed;
				}
			return search_in_frames;
		} catch (Exception e) {
			return 0000;
		}
	}
    
    
    
    /**
     * To Verify presence of Element on a page and switch to the frame where the element is located
     * @param webdriver
     * @param search_in_frames
     * @param By_Loc_XPATH
     * @return
     */
	public boolean Verify_Presence_of_Element_And_Navigate_to_Frame(WebDriver webdriver, int search_in_frames, By By_Loc_XPATH) {
		try {
			boolean element_present = false;
			for (int i = 1; i <= search_in_frames; i++) {
				webdriver.switchTo().defaultContent();
				user_actions.Switch_To_Specified_Frame_On_CurrentPage(webdriver, i);
				if (webdriver.findElements(By_Loc_XPATH).size() > 0) {
					element_present = true;
					logger.info("Element : "+By_Loc_XPATH+" is found in frame : "+i+" and succefully navigated to that frame");
					break;
				}
				{
					element_present = false;
				}
			}
			if(!element_present){
				print_utilv2.ts_failed(webdriver, "Unable to find element "+ By_Loc_XPATH+" even after searching in "+search_in_frames+ " frames");
				return false;
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}
    
    /** Click on floating menu acquire and open
     * @param webdriver
     * @return
     */
    public boolean UUI2_ON_FLOATING_MENU_CLICK_ON_OPTION_ACQUIRE_AND_OPEN(WebDriver webdriver, String NFID, String Task_Name) {
    	try {
    		int[] size_and_status = new int[2];
    		int size = 0;
    		int initial_status=0;
    		boolean final_status = false;
    		size_and_status = Click_Floating_Menu_Acquire_And_Open(webdriver);
    			size = size_and_status[0];
    			initial_status = size_and_status[1]; // if status is zero it indicates fail and if it is 1 it indicates pass
    				final_status = UUI2_SMALL_WINDOW_SUBMIT_AND_OPEN_TASK(webdriver, NFID,Task_Name);
    				
    		if (!final_status)  {
    			print_utilv2.ts_debug_ss(webdriver,
    					"Getting failire while trying to Acquire and / or Open task");
    			return false;
    		}
    		return true;
    	} catch (Exception e) {
    		print_utilv2.ts_failed(webdriver,
    				"Exception - Exception in clicking Acquire and Open - floating menu option - UUI2_ON_FLOATING_MENU_CLICK_ON_OPTION_ACQUIRE_AND_OPEN");
    		e.printStackTrace();
    		return false;
    	}
    }
    
    //To Open
    public boolean UUI2_ON_FLOATING_MENU_CLICK_OPEN(WebDriver webdriver, String NFID, String Task_Name) {
    	try {
    		int i = 1;
    		int[] size_and_status = new int[2];
    		int size = 0;
    		int initial_status=0;
    		boolean final_status = false;
    		while (i < 6) {
    			size=0;
    			initial_status=0;
    			size_and_status = Click_Floating_Menu_Acquire_And_Open(webdriver);
    			size = size_and_status[0];
    			initial_status = size_and_status[1]; // if status is zero it indicates fail and if it is 1 it indicates pass
    			if (initial_status == 0) {
    				i++;
    			} 
    				final_status = UUI2_SMALL_WINDOW_SUBMIT_AND_OPEN_TASK(webdriver, NFID, Task_Name);
    				if (!final_status) {
    					i++;
    				} else {
    					break;
    				}
    		}
    		if ((size != 0 && !final_status) || (size == 0 && initial_status == 0)) {
    			print_utilv2.ts_debug_ss(webdriver,
    					"Getting failure even after retrying to Acquire and Open task multiple times");
    			return false;
    		}
    		return true;
    	} catch (Exception e) {
    		print_utilv2.ts_failed(webdriver,
    				"Exception - Exception in clicking Acquire and Open - floating menu option - UUI2_ON_FLOATING_MENU_CLICK_ON_OPTION_ACQUIRE_AND_OPEN");
    		e.printStackTrace();
    		return false;
    	}
    }
	
	
	/** Floating Menu Acquire and Open
	 * @param webdriver
	 * @return
	 */
	public int[] Click_Floating_Menu_Acquire_And_Open(WebDriver webdriver){
		int[] return_value = new int[2];
		try{
			int size;
			logger.info("Trying to click on Floating menu option - Acquire and Open");
			SET_MAIN_PARENT_WINDOW_HANDLE_TO_CURRENT_WINDOW(webdriver);		
			size = webdriver.findElements(ByLoc_FloatingMenu_AcquireAndOpen).size();
			return_value[0] = size;
			if(size == 0){
				if(ENV.env_name.equalsIgnoreCase("SIT")){
					if(user_actions.click(webdriver, ByLoc_FloatingMenu_Open_2) == false){
						print_utilv2.ts_failed(webdriver,"Unable to click on OPEN button from My Tasks - Please check " + size);
						return_value[1] = 0;
						return return_value;
					}
				}
				if(ENV.env_name.equalsIgnoreCase("UAT")) {
				if(user_actions.click(webdriver, ByLoc_FloatingMenu_Open) == false){
					print_utilv2.ts_failed(webdriver,"Unable to click on OPEN button from My Tasks - Please check " + size);
					return_value[1] = 0;
					return return_value;
				}
				}else if(ENV.env_name.equalsIgnoreCase("Production")){
					if(user_actions.click(webdriver, ByLoc_FloatingMenu_Open_1) == false){
						print_utilv2.ts_failed(webdriver,"Unable to click on OPEN button from My Tasks - Please check " + size);
						return_value[1] = 0;
						return return_value;
					}
				}
				logger.info("Floating Menu - Open - Clicked Successfully");
			}else if(size == 1){
			if(!click_FloatingMenu_AcquireAndOpen_And_Verify_Pop_Up_Box_is_Displayed(webdriver)){
				return_value[1] = 0;
				return return_value;
			}
			}else{
				if(user_actions.click(webdriver, By.xpath(FloatingMenu_AcquireAndOpen + "[" + size + "]")) == false){
					print_utilv2.ts_failed(webdriver,"ELSE - Unable to click on ACQUIRE AND OPEN button from Available Tasks - Please check" + size);
					return_value[1] = 0;
					return return_value;
				}
				print_utilv2.ts_debug_ss(webdriver, "Floating Menu - Acquire and Open with size "+size+" - Clicked Successfully");
			}
			return_value[1] = 1;
			return return_value;
		}catch(Exception e){
			e.printStackTrace();
			print_utilv2.ts_failed(webdriver, "Getting exception at Click_Floating_Menu_Acquire_And_Open in uui_actions_page");
			return_value[1] = 0;
			return return_value;
		}
	}
	
	/**
	 * This is used to click on Acquire and Open option on floating Menu and Verify if pop up box is displayed or not
	 * @param webdriver
	 * @return
	 */
	public boolean click_FloatingMenu_AcquireAndOpen_And_Verify_Pop_Up_Box_is_Displayed(WebDriver webdriver) {
		try {
			int size1 = 0; 
			int size2 = 0;
			for (int i =1; i <= 10; i++) {
				Thread.sleep(6000);
				user_actions.wait4XpathPresent2(webdriver, ByLoc_FloatingMenu_AcquireAndOpen, 20);
				if (user_actions.click(webdriver, ByLoc_FloatingMenu_AcquireAndOpen) == false) {
					print_utilv2.ts_failed(webdriver,
							"Unable to click on ACQUIRE AND OPEN button from Available Tasks - Please check");
					return false; 
				}
				
				print_utilv2.ts_debug_ss(webdriver, "Floating Menu - Acquire and Open - Clicked Successfully");

				size1 = webdriver.findElements(ByLoc_SmallWindow_Acquire_And_Open_Task).size();
				size2 = webdriver.findElements(ByLoc_Acquire_And_Open_Pop_Up_WindowBox).size();
				

				if (size1 == 0 && size2 == 0) {
					logger.info("Size1 = "+size1+" Size2 = "+size2);
					logger.info("Pop up Window is not displayed even after successfully clicking on Acquire and Open on Floationg menu for "
									+ i + " times");
				} else {
					logger.info("Size1 = "+size1+" Size2 = "+size2);
					print_utilv2.ts_debug_ss(webdriver, "Pop up window is getting displayed after trying "+i+" time/times");
					return true;
				}
			}
			print_utilv2.ts_failed(webdriver, "Pop up window is not displayed even after clicking Acquire and Option on floating for 10 time/times");
			return false;
		} catch (Exception e) {
			print_utilv2.ts_failed(webdriver,
					"Getting exception at click_FloatingMenu_AcquireAndOpen_And_Verify_Pop_Up_Box_is_Displayed");
			return false;
		}

	}
	
	//Open
	public int[] Click_Floating_Menu_Open(WebDriver webdriver){
		int[] return_value = new int[2];
		try{
			int size;
			logger.info("Trying to click on Floating menu option - Acquire and Open");
			SET_MAIN_PARENT_WINDOW_HANDLE_TO_CURRENT_WINDOW(webdriver);		
			size = webdriver.findElements(ByLoc_FloatingMenu_AcquireAndOpen).size();
			return_value[0] = size;
			if(size == 0){
				if(user_actions.click(webdriver, ByLoc_FloatingMenu_Open) == false){
					print_utilv2.ts_failed(webdriver,"Unable to click on OPEN button from My Tasks - Please check " + size);
					return_value[1] = 0;
					return return_value;
				}
				logger.info("Floating Menu - Open - Clicked Successfully");
			}else if(size == 1){
				if(user_actions.click(webdriver, ByLoc_FloatingMenu_AcquireAndOpen) == false){
					print_utilv2.ts_failed(webdriver,"Unable to click on ACQUIRE AND OPEN button from Available Tasks - Please check" + size);
					return_value[1] = 0;
					return return_value;
					}
				print_utilv2.ts_debug_ss(webdriver, "Floating Menu - Acquire and Open - Clicked Successfully");
			}else{
				if(user_actions.click(webdriver, By.xpath(FloatingMenu_AcquireAndOpen + "[" + size + "]")) == false){
					print_utilv2.ts_failed(webdriver,"ELSE - Unable to click on ACQUIRE AND OPEN button from Available Tasks - Please check" + size);
					return_value[1] = 0;
					return return_value;
				}
				print_utilv2.ts_debug_ss(webdriver, "Floating Menu - Acquire and Open with size "+size+" - Clicked Successfully");
			}
			return_value[1] = 1;
			return return_value;
		}catch(Exception e){
			e.printStackTrace();
			print_utilv2.ts_failed(webdriver, "Getting exception at Click_Floating_Menu_Acquire_And_Open in uui_actions_page");
			return_value[1] = 0;
			return return_value;
		}
	}
	/** Floating menu cancel option
	 * @param webdriver
	 * @return
	 */
	public boolean UUI2_ON_FLOATING_MENU_CLICK_ON_OPTION_CANCEL(WebDriver webdriver){

		try{
			int size;
			logger.info("Trying to click on Floating menu option - Cancel NFID");
			SET_MAIN_PARENT_WINDOW_HANDLE_TO_CURRENT_WINDOW(webdriver);
			user_actions.click_IFFOUND(webdriver, ByLoc_FloatingMenu_3Dot);
			//adding code to handle SIT and UAT environment both - seems SIT env has issue - there are two Acquire and open elements in SIT - while UAT has only 1
			size = webdriver.findElements(ByLoc_FloatingMenu_CancelNFID).size();
			if(user_actions.click(webdriver, ByLoc_FloatingMenu_CancelNFID) == false){
				print_utilv2.ts_failed(webdriver,"Unable to click on CANCEL NFID button Please check" + size);
				return false;
			}
			return true;
		}catch(Exception e){
			print_utilv2.ts_failed(webdriver,"Exception - Exception in clicking Acquire and Open - floating menu option - UUI2_ON_FLOATING_MENU_CLICK_ON_OPTION_ACQUIRE_AND_OPEN");
			e.printStackTrace();
			return false;
		}
	}

	/** This function will enter NFID in Available Tasks - Search field
	 * @param webdriver
	 * @param NFID
	 * @return
	 */
	public boolean UUI2_ENTER_NFID_IN_SEARCH_FIELD_AVAILABLE_TASKS(WebDriver webdriver, String NFID){
		logger.info("Trying to enter NFID in search field in UUI Task List");
		try{
			user_actions.commonDelay(webdriver, 2);
			if(user_actions.clear(webdriver, ByLoc_TaskList_Grid_Filter) == false){return false;};
			waitTillProcessing(webdriver);
			if(user_actions.sendKeys(webdriver, ByLoc_TaskList_Grid_Filter, NFID) == false) {return false;};
			if(user_actions.sendKeys_KeyBoard(webdriver, ByLoc_TaskList_Grid_Filter, Keys.ENTER) == false) {return false;}
			waitTillProcessing(webdriver);
			logger.info("NFID entered in search field");
			return true;
		}catch(Exception e){
			print_utilv2.ts_failed(webdriver,"Exception : UUI2_ENTER_NFID_IN_SEARCH_FIELD_AVAILABLE_TASKS");
			e.printStackTrace();
			return false;
		}
	}
	
	/** Getting Floating menu for a specific task in UUI
	 * @param webdriver
	 * @param Task_Name
	 * @return
	 */
	public boolean UUI2_GET_FLOATING_MENU_FOR_SPECIFIC_TASK_NAME(WebDriver webdriver, String Task_Name){
		try{
			logger.info("Trying to get Floating Menu for task in UUI " + Task_Name);
			SET_MAIN_PARENT_WINDOW_HANDLE_TO_CURRENT_WINDOW(webdriver);
			List<WebElement> taskList = webdriver.findElements(By.xpath(TaskList_All_Rows_Xpath));
			int taskNameIndex = user_actions.GET_COLUMN_INDEX_FOR_TABLE(webdriver, "Task Name", TaskList_Table_Header);
			int match_found_at = 9999;
			for (int j = 1; j <= taskList.size(); j++) {
				String tempTaskName= user_actions.getText(webdriver, By.xpath(TaskList_All_Rows_Xpath + "["+j+"]/td["+taskNameIndex+"]"));
				if (tempTaskName!=null && tempTaskName.contains(Task_Name)) {
					match_found_at = j;
					break;
				}
			}
			//commenting this out to see if this solves problem that sometimes floating menu not coming in UUI
			//nevigateToElement(webdriver, By.xpath(ALL_ROWS_XPATH_AVAILABLE_TASKS + "["+match_found_at+"]/td[2]"));
			//WFM_Common_fns.findDynamicElement(webdriver, By.xpath(ALL_ROWS_XPATH_AVAILABLE_TASKS + "["+match_found_at+"]/td[2]"), 20).sendKeys(Keys.DOWN);
			//WFM_Common_fns.click(webdriver, By.xpath(ALL_ROWS_XPATH_AVAILABLE_TASKS + "["+match_found_at+"]/td[2]"));
			//WebDriverUtil.wait4XpathPresent1(webdriver, "//div[@class='strip activeTaskStrip']", 10);
			int count = 1;
			while(true){
				if(VERIFY_FLOATING_MENU_IS_AVAILABLE(webdriver, By.xpath(TaskList_All_Rows_Xpath + "["+match_found_at+"]/td[2]"))){
					break;
				}else{
					count++;
				}
				if(count == 10){
					ExtentTestManager.Append_Test_Name("[UUI_FLOATING_MENU_NOT_COMING]");
					print_utilv2.tc_failed(webdriver,"Not Getting Floating menu in UUI - Retried " + count + " times - but still not LUCK - plz check manually");
					return false;
				}
			}
			print_utilv2.ts_passed_ss(webdriver, "Floating menu coming in UUI for Task Name :  " + Task_Name);
			return true;
		}catch(Exception e){
			print_utilv2.ts_failed(webdriver,"Exception in - UUI2_GET_FLOATING_MENU_FOR_SPECIFIC_TASK_NAME");
			e.printStackTrace();
			return false;
		}
	}
	
	/** Need to make sure this function is called before two windows are opened
	 * @param webdriver
	 */
	public void SET_MAIN_PARENT_WINDOW_HANDLE_TO_CURRENT_WINDOW(WebDriver webdriver){
		MAIN_PARENT_WINDOW_HANDLE = webdriver.getWindowHandle().trim();
		logger.info("Main Window Handle : " + MAIN_PARENT_WINDOW_HANDLE);
	}
	
	public boolean waitTillProcessing(WebDriver webdriver) {
		boolean testResult = false;
		for (int i = 0; i < 30; i++) {
			try {
				if (webdriver.findElement(By.id("tasksdata_processing")).getAttribute("style")
						.equals("display: none;")) {
					return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("Waiting for Processing... to disappear");
			user_actions.commonDelay(webdriver, 2);
		}
		return testResult;
	}
	
	/** Trying to get UUI Main
	 * @param webdriver
	 * @return
	 */
	public boolean GET_UUI_MAIN(WebDriver webdriver) {	
		for(int count = 1;count < 10; count ++){
			if(GET_UUI_MAIN_ROUTINE(webdriver)){
				break;
			}else{
				ExtentTestManager.Append_Test_Name("[UUI_DOWN]");
				print_utilv2.tc_failed(webdriver,"Failed to Get UUI Main - Can not proceed with Testing - Please Check");
				return false;
			}
		}
		return true;
	}
	
	/** Trying to get NF Tree
	 * @param webdriver
	 * @return
	 */
	public boolean GET_NF_Tree(WebDriver webdriver, String nftree_url) {	
		for(int count = 1;count < 10; count ++){
			if(Login_from_NF_Tree(webdriver, nftree_url)){
				break;
			}else{
				ExtentTestManager.Append_Test_Name("[UUI_DOWN]");
				print_utilv2.tc_failed(webdriver,"Failed to Get NF Tree - Can not proceed with Testing - Please Check");
				return false;
			}
		}
		return true;
	}
	
	/** Trying to get UUI Main Routine
	 * @param webdriver
	 * @return
	 */
	public boolean GET_UUI_MAIN_ROUTINE(WebDriver webdriver) {
		try {
			LoginPage login = new LoginPage();
			user_actions.waitForPageLoaded(webdriver);
			user_actions.commonDelay(webdriver, 2);
			print_utilv2.ts_debug("Trying to Get : <a href='" + ENV.UUI_URL + "'>URL</a> : " + ENV.UUI_URL);
			webdriver.get(ENV.UUI_URL);
			user_actions.commonDelay(webdriver, 2);
			if (webdriver.getTitle().equalsIgnoreCase("Unified UI")) {
				user_actions.waitForPageLoaded(webdriver);
				login.Close_PopUP_Notifications(webdriver);
				return true;
			} else if (webdriver.getTitle().equalsIgnoreCase("Enterprise Single Sign On")
					|| webdriver.getTitle().contains("Sign On")) {
				if(login.Login_For_UUI(webdriver,ENV.UUI_URL, ENV.WFM_USERNAME,ENV.WFM_PASSWORD) == false){
					print_utilv2.tc_failed(webdriver,"Login Failed - Can not proceed with Testing - Please Check");
			}
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			print_utilv2.ts_debug_ss(webdriver,"Failed to get UUL Main page - Retrying...");
			return false;
		}
	}
	
	/**
	 * This is to login to NF Tree page
	 * @author KANNEVE
	 * @param webdriver
	 * @return
	 */
	public boolean Login_from_NF_Tree(WebDriver webdriver, String nf_tree_url) {
		try { // /html/head/title
			LoginPage login = new LoginPage();
			user_actions.waitForPageLoaded(webdriver);
			user_actions.commonDelay(webdriver, 2);
 			print_utilv2.ts_debug("Trying to Get : <a href='" + nf_tree_url + "'>URL</a> : " + nf_tree_url);
			webdriver.get(nf_tree_url);
			user_actions.commonDelay(webdriver, 2);
			if (webdriver.getTitle().equalsIgnoreCase("NFID Main Page")) {
				user_actions.waitForPageLoaded(webdriver);
				return true;
			} else if (webdriver.getTitle().equalsIgnoreCase("Enterprise Single Sign On")
					|| webdriver.getTitle().contains("Sign On")) {
				if(login.Login_For_NFTree(webdriver,nf_tree_url, ENV.WFM_USERNAME,ENV.WFM_PASSWORD) == false){
					print_utilv2.tc_failed(webdriver,"Login Failed - Can not proceed with Testing - Please Check");
			}
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			print_utilv2.ts_debug_ss(webdriver,"Failed to get NF Tree page - Retrying...");
			return false;
		}
	}
 
	/** Switching to second frame after acquire and open
	 * @param webdriver
	 * @return
	 */
	public boolean UUI_TASKLIST_WAIT_AND_SWITCH_TO_SECOND_FRAME(WebDriver webdriver){
    	logger.info("Checking for Second frame for UUI Task Lists");
    	int size = user_actions.GET_Number_Of_Frames_On_Page(webdriver);
    	logger.info("1. Frame Count : " + size);
    	int count = 1;
    	while(size < 2){
    		user_actions.commonDelay(webdriver, 1);
    		size = user_actions.GET_Number_Of_Frames_On_Page(webdriver);
    		logger.info("2. Frame Count : " + size  + " Retrying again : " + count);
    		count++;
    		if(count == 60){
    			print_utilv2.ts_debug_ss(webdriver,"Retried Multiple times but Second Frame not coming");
    			return false;
    		}
    	}    	
    	if(user_actions.Switch_To_Second_Frame_On_CurrentPage(webdriver) == false){
    		return false;
    	}
    	return true;
    }
    
	/** Clicking on Submit and open
	 * @param webdriver
	 * @return
	 */
	public boolean UUI2_SMALL_WINDOW_SUBMIT_AND_OPEN_TASK(WebDriver webdriver, String NFID, String Task_Name){
		try{
			logger.info("Trying to click on small window - Submit and Open option");
			SET_MAIN_PARENT_WINDOW_HANDLE_TO_CURRENT_WINDOW(webdriver);
			int retry_count = 1;
			int acquire__window_size = 0;
			int submit_window_size = 0;
			while (retry_count < 5) {
				user_actions.wait4XpathPresent2(webdriver, ByLoc_SmallWindow_Acquire_And_Open_Task, 10);
				acquire__window_size = webdriver.findElements(ByLoc_SmallWindow_Acquire_And_Open_Task).size();
				logger.info("Small Widnow - Acquire and Open - Size : " + acquire__window_size);
				submit_window_size = webdriver.findElements(ByLoc_SmallWindow_Submit_And_Open_Task).size();
				logger.info("Small Window Submit and Open - Size : " + submit_window_size);
				if (acquire__window_size == 0 && submit_window_size == 0) {
					retry_count++;
				} else {
					break;
				}
			}
			if (acquire__window_size > 0) {
				if (user_actions.click(webdriver, ByLoc_SmallWindow_Acquire_And_Open_Task) == false) {
					print_utilv2.ts_debug_ss(webdriver,
							"Failed to Click on Acquire and Open Task - Option on Small Window");
					return false;
				}
			} else if (submit_window_size > 0) {
				if (user_actions.click(webdriver, ByLoc_SmallWindow_Submit_And_Open_Task) == false) {
					print_utilv2.ts_debug_ss(webdriver,
							"Failed to Click on Submit and Open Task - Option on Small Window");
					return false;
				}
			} else {
				for(int i = 0; i < 4; i++) {
					print_utilv2.ts_failed(webdriver,"no pop up window - Failed inside humphrey loop...task: " + Task_Name);
					if(UUI2_NEVIGATE_TO_AVAILABLE_TASKS_50TimesFind(webdriver,NFID, Task_Name) == false){continue;}
					if(UUI2_ENTER_NFID_IN_SEARCH_FIELD_AVAILABLE_TASKS(webdriver,NFID) == false){continue;}
					if(UUI2_GET_FLOATING_MENU_FOR_SPECIFIC_TASK_NAME(webdriver,Task_Name) == false){continue;}
		    		int[] size_and_status = Click_Floating_Menu_Acquire_And_Open(webdriver);
//		    			int size = size_and_status[0];
		    			int initial_status = size_and_status[1]; // if status is zero it indicates fail and if it is 1 it indicates pass
		    			 if (initial_status == 0 ) {continue;}
							while (retry_count < 5) {
								user_actions.wait4XpathPresent2(webdriver, ByLoc_SmallWindow_Acquire_And_Open_Task, 10);
								acquire__window_size = webdriver.findElements(ByLoc_SmallWindow_Acquire_And_Open_Task).size();
								logger.info("Small Widnow - Acquire and Open - Size : " + acquire__window_size);
								submit_window_size = webdriver.findElements(ByLoc_SmallWindow_Submit_And_Open_Task).size();
								logger.info("Small Window Submit and Open - Size : " + submit_window_size);
								if (acquire__window_size == 0 && submit_window_size == 0) {
									retry_count++;
								} else {
									break;
								}
							}
							if (acquire__window_size > 0) {
								if (user_actions.click(webdriver, ByLoc_SmallWindow_Acquire_And_Open_Task) == false) {
									print_utilv2.ts_debug_ss(webdriver,
											"Failed to Click on Acquire and Open Task - Option on Small Window -humphrey");
								} else break;
							} else if (submit_window_size > 0) {
								if (user_actions.click(webdriver, ByLoc_SmallWindow_Submit_And_Open_Task) == false) {
									print_utilv2.ts_debug_ss(webdriver,
											"Failed to Click on Submit and Open Task - Option on Small Window - humphrey");
								} else break;
							}
					if (i == 3) {
						return false;
					}
				}
//				print_utilv2.tc_failed(webdriver,
//						" Niether Acquire and Open nor Submit and Open pop up window is getting displayed please check");
//				return false;
			}	
			if(user_actions.AJAX_LOOP(webdriver, Loading_Indicator_1) == false){
				print_utilv2.ts_debug_ss(webdriver, "Loading indicator not disappering upon clicking on Submt and Open Task");
				return false;
			}
			user_actions.waitForPageLoaded(webdriver);
			user_actions.commonDelay(webdriver, 10);
			logger.info("Small Window - Submit and Open Task - Completed");
			return true;
		}catch(Exception e){
			print_utilv2.ts_failed(webdriver,"Exception - small window - Submit and Open option - UUI2_SMALL_WINDOW_SUBMIT_AND_OPEN_TASK");
			e.printStackTrace();
			return false;
		}
	}

	/** Verifying Floating Menu
	 * @param webdriver
	 * @param ByXpath
	 * @return
	 */
	public boolean VERIFY_FLOATING_MENU_IS_AVAILABLE(WebDriver webdriver, By ByXpath){
		if(user_actions.click(webdriver, ByXpath) == false){
			print_utilv2.ts_debug_ss(webdriver,"Not able to click on Checkbox of Available or My Tasks - to get floating menu");
			return false;
		}
		if(user_actions.wait4XpathPresent2(webdriver, ByLoc_FloatingMenu_Strip, 10) == false){
			print_utilv2.ts_debug_ss(webdriver,"Floating menu not coming - Please retry");
			return false;
		}
		return true;
	}
	
	/** Switching to task details
	 * @param webdriver
	 * @return
	 */
	public boolean SWITCH_TO_TASK_DETAILS(WebDriver webdriver){
		try{
			user_actions.waitForPageLoaded(webdriver);
			user_actions.commonDelay(webdriver, 5);
			user_actions.waitForPageLoaded(webdriver);
			webdriver.switchTo().frame(iFrame_Task_Details);
			return true;
		}catch(Exception e){
			print_utilv2.ts_debug_ss(webdriver, "Failed to switch to task details iframe");
			return false;
		}
	}

	/** Verifying the column values after getting NFID details strings
	 * @param webdriver
	 * @param Task_Name
	 * @param GL_NFID
	 * @param column_name
	 * @param Expected_Value
	 * @return
	 */
	public String Verify_Column_Value(WebDriver webdriver, String Task_Name, String GL_NFID, String column_name, String Expected_Value){
		String actual_column_data = Get_Column_Value(webdriver, Task_Name, GL_NFID, column_name);
		if(actual_column_data.equalsIgnoreCase("Fail")){
			print_utilv2.ts_debug("Not able to get column Value : " + column_name);
			return "Fail";
		}else if(actual_column_data.equalsIgnoreCase("Column-Not-Found")){
			return "Column-Not-Found";
		}
		if(actual_column_data.equalsIgnoreCase(Expected_Value)){
			print_utilv2.ts_passed("Column Value matching - Actual : " + actual_column_data + " Expected : " + Expected_Value);
			return "Pass";
		}else{
			print_utilv2.ts_failed(webdriver,"Column Value not matching - Actual : " + actual_column_data + " Expected : " + Expected_Value);
			return "Fail";
		}
	}

	/** Verifying the column names with return type as boolean
	 * @param webdriver
	 * @param Task_Name
	 * @param GL_NFID
	 * @param column_name
	 * @param Expected_Value
	 * @return
	 */
	public boolean Verify_Column_Value_V2(WebDriver webdriver, String Task_Name, String GL_NFID, String column_name, String Expected_Value){
		String actual_column_data = Get_Column_Value(webdriver, Task_Name, GL_NFID, column_name);
		if(actual_column_data.equalsIgnoreCase("Fail")){
			print_utilv2.ts_debug("Not able to get column Value : " + column_name);
			return false;
		}else if(actual_column_data.equalsIgnoreCase("Column-Not-Found")){
			return false;
		}
		if(actual_column_data.equalsIgnoreCase(Expected_Value)){
			print_utilv2.ts_passed("Column Value matching - Actual : " + actual_column_data + " Expected : " + Expected_Value+" for task "+Task_Name);
			return true;
		}else{
			print_utilv2.ts_failed(webdriver,"Column Value not matching - Actual : " + actual_column_data + " Expected : " + Expected_Value+" for task "+Task_Name);
			return false;
		}
	}
	
	/** Getting the column values checking the task name
	 * @param webdriver
	 * @param Task_Name
	 * @param GL_NFID
	 * @param column_name
	 * @return
	 */
	public String Get_Column_Value(WebDriver webdriver, String Task_Name, String GL_NFID, String column_name){	
		int row_num= Get_Row_Number_For_Task(webdriver,Task_Name);
		if(row_num == 999){
			print_utilv2.ts_debug("Task Name not found - Cant perform validation - Skipping");
			return "Fail";
		}
		int Column_Index = Get_Any_Column_Index(webdriver,column_name);
		if(Column_Index == 999){
			print_utilv2.ts_debug(column_name + " - Column not found in current view - Cant perform validation - Skipping");
			return "Column-Not-Found";
		}
		String column_data_xpath = TaskList_All_Rows_Xpath + "[" + row_num + "]/td[" + Column_Index + "]";
		String actual_column_data = user_actions.getText(webdriver, By.xpath(column_data_xpath));
		print_utilv2.ts_debug("Column Name : " + column_name + " - Value : " + actual_column_data);
		return actual_column_data;
	}
	
	/** Getting the column value for checking the NFID
	 * @param webdriver
	 * @param GL_NFID
	 * @param column_name
	 * @return
	 */
	public String Get_Column_Value_Using_NFID(WebDriver webdriver, String GL_NFID, String column_name){
		int row_num= Get_Row_Number_For_NFID(webdriver, GL_NFID);
		if(row_num == 999){
			print_utilv2.ts_debug("NFID not found - Cant perform validation - Skipping");
			return "Fail";
		}
		int Column_Index = Get_Any_Column_Index(webdriver,column_name);
		if(Column_Index == 999){
			print_utilv2.ts_debug(column_name + " - NFID found in current view - Cant perform validation - Skipping");
			return "Column-Not-Found";
		}
		String column_data_xpath = TaskList_All_Rows_Xpath + "[" + row_num + "]/td[" + Column_Index + "]";
		String actual_column_data = user_actions.getText(webdriver, By.xpath(column_data_xpath));
		print_utilv2.ts_debug("Column Name : " + column_name + " - Value : " + actual_column_data);
		return actual_column_data;
	}

	/** Getting row number for task name
	 * @param webdriver
	 * @param Task_Name
	 * @return
	 */
	public int Get_Row_Number_For_Task(WebDriver webdriver,String Task_Name){
		int Task_Name_Column_Index = Get_Task_Name_Column_Index(webdriver);
		int Size_Off_AllRows = Get_Size_Of_All_Rows_In_TaskList(webdriver);
		for(int row = 1; row <= Size_Off_AllRows ; row++ ){
			String TaskName_column_data_xpath = TaskList_All_Rows_Xpath + "[" + row + "]/td[" + Task_Name_Column_Index + "]";
			String Current_Task_Name = user_actions.getText(webdriver, By.xpath(TaskName_column_data_xpath));
			if(Current_Task_Name.equalsIgnoreCase(Task_Name)){
				return row;
			}
		}
		return 999;
	} 

	/** Getting row number for NFID
	 * @param webdriver
	 * @param GL_NFID
	 * @return
	 */
	public int Get_Row_Number_For_NFID(WebDriver webdriver,String GL_NFID){
		int NFID_Column_Index = Get_NFID_Column_Index(webdriver);
		int Size_Off_AllRows = Get_Size_Of_All_Rows_In_TaskList(webdriver);
		for(int row = 1; row <= Size_Off_AllRows ; row++ ){
			//*[@id='tasksdata']/tbody/tr[1]/td[6]
			String NFID_column_data_xpath = TaskList_All_Rows_Xpath + "[" + row + "]/td[" + NFID_Column_Index + "]";
			String Current_NFID = user_actions.getText(webdriver, By.xpath(NFID_column_data_xpath));
			if(Current_NFID.equalsIgnoreCase(GL_NFID)){
				return row;
			}
		}
		return 999;
	} 

	/** Getting index for column name
	 * @param webdriver
	 * @param Column_Name
	 * @return
	 */
	public int Get_Any_Column_Index(WebDriver webdriver,String Column_Name){
		int Index = user_actions.GET_COLUMN_INDEX_FOR_TABLE(webdriver, Column_Name, TaskList_Table_Header);
		if(Index == 999){
			print_utilv2.ts_debug(Column_Name + " column not present in current view");
		}
		logger.info(Column_Name + " Index : " + Index);
		return Index;
	}
	
	/** Getting column index for table
	 * @param webdriver
	 * @return
	 */
	public int Get_Task_Name_Column_Index(WebDriver webdriver){
		int Index = user_actions.GET_COLUMN_INDEX_FOR_TABLE(webdriver, "Task Name", TaskList_Table_Header);
		if(Index == 999){
			print_utilv2.ts_failed(webdriver, "Task Name column must be preset in your view for making any UUI Validation");
		}
		logger.info("Task Name Column Index : " + Index);
		return Index;
	}
	
	/** Getting NFID column index for table
	 * @param webdriver
	 * @return
	 */
	public int Get_NFID_Column_Index(WebDriver webdriver){
		int Index = user_actions.GET_COLUMN_INDEX_FOR_TABLE(webdriver, "NFID", TaskList_Table_Header);
		if(Index == 999){
			print_utilv2.ts_failed(webdriver, "NFID column must be preset in your view for making any UUI Validation");
		}
		logger.info("NFID Column Index : " + Index);
		return Index;
	}
	
	/** Getting row sizes of all rows
	 * @param webdriver
	 * @return
	 */
	public int Get_Size_Of_All_Rows_In_TaskList(WebDriver webdriver){
		List<WebElement> rows = webdriver.findElements(By.xpath(TaskList_All_Rows_Xpath));
		return rows.size();
	}
	
	/** shifting the window handle to new opened page
	 * @param webdriver
	 * @param NFID
	 * @param Task_Name
	 * @param winHandleBefore
	 * @return
	 */
	public boolean Get_Active_Task_Card_Page(WebDriver webdriver, String NFID,String Task_Name, String winHandleBefore) {
		while (webdriver.getWindowHandles().size() > 1) {
			if(webdriver.getWindowHandle().equals(winHandleBefore)){
			}else{
				webdriver.close();
			}
		}
		webdriver.switchTo().window(winHandleBefore);
		int i = 0;
		boolean Status = false;
		while (i < 5) {
			if (UUI2_ACQUIRE_AND_OPEN(webdriver, NFID, Task_Name) == false) {
				i++;
				Status = false;
			} else {
				print_utilv2
						.tc_debug("Able to acquire and open task card " + Task_Name + " after trying for" + i + "times");
				Status = true;
				break;
			}
		}
		if (!Status) {
			print_utilv2.ts_failed(webdriver, "Getting failure while trying to Acquire and Open " + Task_Name
					+ " task card even after trying " + i + " times");
			ExtentTestManager.Append_Test_Name("[UUI Acquire And Open Failed [" + Task_Name + "]]");
			return false;
		}
		
		if (SWITCH_TO_NEW_WINDOW(webdriver) == false) {
			return false;
		}
		SWITCH_TO_TASK_DETAILS(webdriver);
		return true;
	}
	
	/** Closing windows and returning to main page
	 * @param webdriver
	 * @param winHandleBefore
	 * @return
	 */
	public boolean Return_To_Main_Page(WebDriver webdriver,String winHandleBefore){
		while (webdriver.getWindowHandles().size() > 1) {
			if(webdriver.getWindowHandle().equals(winHandleBefore)){
			}else{
				webdriver.close();
			}
		}
		webdriver.switchTo().window(winHandleBefore);
		return true;
	}
	
	/** Verifying the column parameters in UUI
	 * @param webdriver
	 * @param Task_Name
	 * @param NFID
	 * @param Column_Name
	 * @param Expected_Data
	 * @return
	 */
	public boolean VERIFY_COLUMN_PARAMETER_VALUE_IN_UUI(WebDriver webdriver, String Task_Name, String NFID, String Column_Name,String Expected_Data){
		if(UUI2_NEVIGATE_TO_AVAILABLE_TASKS_50TimesFind(webdriver, NFID,Task_Name) == false){return false;}
		if(Verify_Column_Value(webdriver, Task_Name, NFID, Column_Name, Expected_Data) == "Fail"){return false;}
		return true;

	}
	
	
	/**
	 * This function is used for verifying a value in the task card
	 * @param webdriver
	 * @param Field_Value name of the field for which verification will be done
	 * @param Expected_Data actual value to the field that needs to be there
	 * @return
	 * @author Venkatesh Kannedhara
	 */
	public boolean Verify_FieldValue_In_TaskCardPage(WebDriver webdriver, String Field_Value, String Expected_Data){
		if(Field_Value.equals("Task Name")){
			if(task_common.Verify_Task_Name(webdriver, Expected_Data)==false){
				return false;
			}
			return true;
		} else if(Field_Value.equals("Network Activity")){
			if(task_common.Verify_Network_Activity(webdriver, Expected_Data)==false){
				return false;
			}
			return true;
		} else if(Field_Value.equals("Engineering Discipline")){
			if(task_common.Verify_Engineering_Discipline(webdriver, Expected_Data)==false){
				return false;
			}
			return true;
		} else if(Field_Value.equals("Network Type")){
			if(task_common.Verify_Network_Type(webdriver, Expected_Data)==false){
				return false;
			}
			return true;
		} else if(Field_Value.equals("Project Type")){
			if(task_common.Verify_Project_Type(webdriver, Expected_Data)==false){
				return false;
			}
			return true;
		}
		else{
			logger.info("Please check the verification task card field");
			return false;
		}
	}
	
	/** Used to wait until the page is loaded
	 * @param webdriver
	 * @return
	 */
	public boolean Wait_For_DNAS_Page_Load(WebDriver webdriver){
		user_actions.waitForPageLoaded(webdriver);
		user_actions.wait4XpathPresent2(webdriver, ByLoc_DNAS_Header, 10);
		return true;
	}
	
	/** verifying the size of smart jump link
	 * @param webdriver
	 * @param LINK
	 * @return
	 */
	public boolean VERIFY_SMART_JUMP_LINK(WebDriver webdriver,String LINK){
		logger.info("UUI Smart Jump Link Verification Started for : " + LINK);
		int size = 0;
		if(LINK.equalsIgnoreCase("CCP_SYSTEM_PLAN")){
			size = user_actions.GET_SIZE_OF_XPATH(webdriver, locSmartJump);
		}else{
			print_utilv2.ts_failed(webdriver, "UUI Smart Jump Link Verification Failed - Not a valid Link Name : " + LINK);
		}
		
		logger.info("Size of Links : " + size);
		if(size > 0){
			return true;
		}else{
			return false;
		}
	}
	
	/** Navigating to Manage issues tab in NFTree
	 * @param webdriver
	 * @return
	 */
	public boolean CLICK_ON_MANAGE_ISSUES_TAB(WebDriver webdriver){
		user_actions.commonDelay(webdriver, 15);
		if(user_actions.click(webdriver, ByLoc_Manage_Issues_Tab) == false){return false;}
		user_actions.commonDelay(webdriver, 10);
		user_actions.Find_Frames_On_Page(webdriver);
		if(user_actions.Swtich_Frame_Using_FrameID(webdriver, iFrame_Manage_Issues) == false){return false;}
		print_utilv2.ts_passed_ss(webdriver, "switched - manage issues");
		return true;
	}
	
	/** Navigating to Tags tab in NFTree
	 * @param webdriver
	 * @return
	 */
	public boolean CLICK_ON_TAGS_TAB(WebDriver webdriver){
		user_actions.commonDelay(webdriver, 10);
		if(user_actions.click(webdriver, ByLoc_Manage_Issues_Tab) == false){return false;}
		user_actions.commonDelay(webdriver, 10);
		user_actions.Find_Frames_On_Page(webdriver);
		if(user_actions.Swtich_Frame_Using_FrameID(webdriver, iFrame_Tags_Tab) == false){return false;}
		print_utilv2.ts_passed_ss(webdriver, "switched - Tags issues");
		return true;
	}
	
}	
