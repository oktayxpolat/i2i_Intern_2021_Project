package test

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable

public class Login {

	public static openBrowser(String url) {

		WebUI.openBrowser(url);
	}


	public static signin(String msisdn,String password) {

		WebUI.setText(findTestObject('Test/msisdnInput'),msisdn);
		WebUI.setText(findTestObject('Test/passInput'),password);
		WebUI.click(findTestObject('Test/btn'));
	}

	public static checkLogin(String name,String surname,String phone,String mail,String customerInfoText ) {

		String getName=WebUI.getText(findTestObject('Test/name'));
		String getSur=WebUI.getText(findTestObject('Test/surname'));
		String getPhone=WebUI.getText(findTestObject('Test/phone'));
		String getMail=WebUI.getText(findTestObject('Test/mail'));
		String getCustomerInfoText=WebUI.getText(findTestObject('Test/customerInfoText'));


		if(!getName.equals(name)) {
			assert false : "Beklenilen = " +name + "Sonuç = " +getName + "birbirine eşit değildir.";
		}

		if(!getSur.equals(surname)) {
			assert false : "Beklenilen = " +surname + "Sonuç = " +getSur + "birbirine eşit değildir.";
		}

		if(!getPhone.equals(phone)) {
			assert false : "Beklenilen = " +phone + "Sonuç = " + getPhone + "birbirine eşit değildir.";
		}


		if(!getMail.equals(mail)) {
			assert false : "Beklenilen = " +mail+ "Sonuç = " +getMail + "birbirine eşit değildir.";
		}


		if(!getCustomerInfoText.equals(customerInfoText)) {
			assert false : "Beklenilen = " +customerInfoText+ "Sonuç = " +getCustomerInfoText + "birbirine eşit değildir.";
		}


		WebUI.click(findTestObject('Test/btn_customerInfo'));
	}




	public static closeBrowser() {
		WebUI.closeBrowser();
	}
}
