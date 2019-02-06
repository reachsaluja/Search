package com.api.aa.test.uts;

import static org.mockito.Matchers.contains;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jayway.jsonpath.JsonPath;
import com.vz.service.common.ServiceCalls;
import com.ws.common.SpringMybatisAngularApplication;
import com.api.ien.common.DatatableReader;
import com.api.uts.sanity.*;
//import com.ws.common.springMybatisAngularApplication;
import com.ws.common.reporting.ReportStep;
import com.ws.common.reporting.ScenarioDetails;

import io.restassured.response.Response;
import net.minidev.json.JSONArray;

public class RestServiceGenericCode {
	ReportStep rs;
	ArrayList<ReportStep> rsl = new ArrayList<ReportStep>();
	ScenarioDetails sdtl;
	ServiceCalls serviceCalls = new ServiceCalls();

	public void Restcall(ArrayList<ScenarioDetails> sd, String feature) {

		String rootFilePath = System.getProperty("user.dir");
		File[] RestFiles = new File(rootFilePath + "/InputFile/RestData/" + feature).listFiles();
	
		for (int i = 0; i < RestFiles.length; i++) {
			String testCaseId = "";
			String jiraId = "";
			String fileName = RestFiles[i].getName();
			ArrayList<ArrayList<String>> testSheetData = null;
			System.out.println("Length of File: " + RestFiles.length);

			System.out.println("Processing :" + fileName + "\n\n");
			String scenarioName = fileName.replace(".csv", "");
			// read requestName.csv file
			DatatableReader dr = new DatatableReader();
			testSheetData = dr.readCSVData(rootFilePath + "/InputFile/RestData/" + feature + "/" + fileName);
			ArrayList<String> headerRow = testSheetData.get(0);

			for (String s : headerRow) {
				System.out.println("HeaderRow--!!" + s);
			}

			HashMap<String, String> hashMap = new HashMap<String, String>();
			// tagMap to hold list of high level xPath expressions for a given
			// tag
			HashMap<String, ArrayList<String>> tagMap = new HashMap<String, ArrayList<String>>();
			System.out.println("Test Sheet data: " + testSheetData.size());

			System.out.println("Header" + headerRow.size());
			for (int testrowCounter = 1; testrowCounter < testSheetData.size(); testrowCounter++) {
				for (int colCounter = 1; colCounter < headerRow.size(); colCounter++) {

					System.out.println("Inside" + colCounter + " for loops");
					String tagName = testSheetData.get(0).get(colCounter);
					System.out.println(tagName);
					if (testrowCounter == 1) {
						String tagValue = testSheetData.get(testrowCounter).get(colCounter);
						hashMap.put(tagName, tagValue);
					}
					if (testrowCounter == 2) {
						// total number of tag expressions available for the
						// given input tag name
						// (defined in the input test csv file)
						String tagCount = testSheetData.get(testrowCounter).get(colCounter);
						if (tagCount.equalsIgnoreCase("")) {
							continue;
						}
						int tagCnt = Integer.parseInt(tagCount);
						ArrayList<String> tagList = new ArrayList<String>();
						String tag;
						for (int cnt = 1; cnt <= tagCnt; cnt++) {
							tag = testSheetData.get(cnt + 2).get(colCounter);
							tagList.add(tag);
							System.out.println(tag + "  =========>is Present");
						}
						tagMap.put(tagName, tagList);
					}

				}
			}
			// System.out.println("Array List is------!"+headerRow[0]);

			String api = testSheetData.get(1).get(0);
			// read tags and build tagmap
			System.out.println("API URL : " + api);
			long startTime = System.nanoTime();
			ScenarioDetails sdtl = new ScenarioDetails();
		
			try {

				sdtl.setScenarioName(scenarioName + " Tag Validation ");
//				String dogetcal = testSheetData.get(2).get(0);
				String user = testSheetData.get(2).get(0);
				String pass = testSheetData.get(3).get(0);
				jiraId = testSheetData.get(4).get(0);
				testCaseId = testSheetData.get(5).get(0);
				
				//add if contiions with 6 different doget contions
				Response responsee = serviceCalls.doGet7(api, user , pass); 
				rs = new ReportStep("given", "Given API : ", "PASSED", "Given API is  : " +api);
				rsl.add(rs);

				System.out.println("The API Request reponse id :" + responsee.asString());
				if (responsee.getStatusCode() == 200) {

					String response = responsee.getBody().asString();
					 rs = new ReportStep("when", "Checking if Status is 200 OK ","PASSED", "Checking if Status is 200 OK " +(responsee.getStatusCode() == 200));
					 rsl.add(rs);

					System.out.println("Response is : " + response);
					 rs = new ReportStep("then", " Response contains STATUS = 200 OK","PASSED", " Response contains STATUS = 200 OK");
					 rsl.add(rs);
					System.out.println("Starting validation");
					
					//Calling Rest Validator 
					validateTags(hashMap, response, tagMap, rsl);
					
					System.out.println("Ending validation");

				} else {
					rsl.add(rs);
					rs = new ReportStep("when", "Validating high level tags", "PASSED", "Validating high level tags");
					rsl.add(rs);
					rs = new ReportStep("then", "FAILED ", "FAILED", "ITS not 200 OK response");
					rsl.add(rs);
				}
			} catch (Exception e) {
				System.out.println(e); 
				rs = new ReportStep("then", "Exception : ", "FAILED", "Exception is : "+e.getMessage());
				rsl.add(rs);/// add report step for exception
			} finally {

				// add scenario details
				//story
				sdtl.setJiraId(jiraId);
				//tescase
				sdtl.setTestCaseID(testCaseId);
				sdtl.setModuleName("VI-RestService");
				sdtl.setRs(rsl);
				sdtl.setDuration((System.nanoTime()) - startTime);
				sd.add(sdtl);
			}

		}

	}

	public static void validateTags(HashMap<String, String> inputTagsMap, String restResponse,
			HashMap<String, ArrayList<String>> tagMap, ArrayList<ReportStep> rsl) {
		ReportStep rs;

		ArrayList<String> tagList = new ArrayList<String>();

		for (String key : inputTagsMap.keySet()) {
			rs = new ReportStep("given", key.replace("_Input", "") + " value as - " + inputTagsMap.get(key), "PASSED",
					"");
			rsl.add(rs);
			// System.out.println("tag:"+tagList.get(i));
			rs = new ReportStep("when", "", "PASSED", "Validating Tags under " + key.replace("_Input", ""));
			rsl.add(rs);
			tagList = tagMap.get(key);

			if (tagList != null && !tagList.isEmpty()) {
				// System.out.println("Tags Validation under
				// TAG:"+key.replace("_Input", ""));
				for (int i = 0; i < tagList.size(); i++) {
					String expression = tagList.get(i);
					try {

						if ((JsonPath.parse(restResponse).read(expression)).toString() != null) {
							System.out.println(expression);
							System.out.println("TRUE");
							rs = new ReportStep("then", "", "PASSED", "Tag:" + expression
									+ " is present in the response under TAG :" + key.replace("_Input", ""));
							rsl.add(rs);
						}

						else {
							rs = new ReportStep("then", "", "FAILED", "Tag:" + expression
									+ " is not present in the response under TAG :" + key.replace("_Input", ""));
							rsl.add(rs);
						}
					} catch (Exception e) {
						// System.out.println("Exception: "+ex.getMessage());
						rs = new ReportStep("then", "", "FAILED", "Exception: " + e.getMessage());
						rsl.add(rs);
					}
				}
			} else {
				// TODO check input tag value and validate xml data for the tag
				rs = new ReportStep("then", "", "PASSED",
						"Tag:" + key.replace("_Input", "") + " is present in :" + key.replace("_Input", ""));
				rsl.add(rs);
			}

		}

	}
}
