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