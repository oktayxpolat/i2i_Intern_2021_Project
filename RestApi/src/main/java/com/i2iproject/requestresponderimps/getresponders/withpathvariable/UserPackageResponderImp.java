package com.i2iproject.requestresponderimps.getresponders.withpathvariable;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.i2iproject.builders.CollectionBuilder;
import com.i2iproject.builders.ItemsBuilder;
//import com.i2iproject.builders.LinksBuilder;
import com.i2iproject.builders.models.CollectionResponse;
import com.i2iproject.builders.models.Item;
import com.i2iproject.builders.models.ItemsData;
import com.i2iproject.requestresponderimps.getresponders.withpathvariable.models.Package;
//import com.i2iproject.builders.models.LinkRelation;
import com.i2iproject.requestresponders.GetRequestWithPathVariableResponder;

@Component("GetUserPackageResponder")
@Scope("prototype")
public class UserPackageResponderImp implements GetRequestWithPathVariableResponder{
	private CollectionBuilder collectionBuilder;
	private ItemsBuilder itemsBuilder;
	private Item newItem;
	private List<Package> packages;
	private Package currentPackageToAdd;
	//private LinksBuilder linksBuilder;
	
	private Package newPackage;
	
	public UserPackageResponderImp(CollectionBuilder collectionBuilder, 
			ItemsBuilder itemsBuilder
			/*LinksBuilder linksBuilder*/) {
		this.collectionBuilder = collectionBuilder;
		this.itemsBuilder = itemsBuilder;
		//this.linksBuilder = linksBuilder;
	}

	@Override
	public CollectionResponse respond(Object pathVariables) {
		collectionBuilder.addHref("/api/users/5/package");
		collectionBuilder.addVersion("3.12.0");
		addItems();
		//addLinks();
		return collectionBuilder.getConstructedCollection();
	}

	private void addItems() {
		populatePackages();
		addPackages(packages);
		collectionBuilder.addItems(itemsBuilder.getConstructedItems());
	}
	
	private void populatePackages() {
		packages = new ArrayList<>();
		addVoicePackage1();
		addVoicePackage2();
		
		addSmsPackage1();
		addSmsPackage2();
		
		addDataPackage1();
		addDataPackage2();
	}
	
	/*
	 * 
	 * SELECT msisdn, packageid
	 * FROM Users,USER_PACKAGE
	 * WHERE Users.user_id = USER_PACKAGE.user_id; T
	 * 
	 * SELECT T.msisdn, T.packageId, packageType, packageName, packageLimit, businessZone, visibility
	 * FROM T, Packages
	 * WHERE T.packageId = Packages.package_id;
	 * 
	 * SELECT msisdn, packageId, packageType, packageName, packageLimit, businessZone, visibility
	 * FROM (Users NATURAL JOIN USER_PACKAGE) NATURAL JOIN Packages;
	 * 
	 */
	private void addVoicePackage1() {
		newPackage = new Package();
		String packageType = "packageType";
		String packageTypeValue = "v";
		newPackage.addPackageItem(new ItemsData(packageType,packageTypeValue));
		String packageName = "packageName";
		String packageNameValue = "Flying Package";
		newPackage.addPackageItem(new ItemsData(packageName,packageNameValue));
		String voiceUsed = "voiceUsed";
		String voiceUsedValue = "200";
		newPackage.addPackageItem(new ItemsData(voiceUsed,voiceUsedValue));
		String voiceRemained= "voiceRemained";
		String voiceRemainedValue= "300";
		newPackage.addPackageItem(new ItemsData(voiceRemained,voiceRemainedValue));
		String endDate = "endDate";
		String endDateValue = "17.08.2021";
		newPackage.addPackageItem(new ItemsData(endDate,endDateValue));	
		String businessZone = "businessZone";
		String businessZoneValue = "YURT DIŞI";
		newPackage.addPackageItem(new ItemsData(businessZone,businessZoneValue));
		String visiblity = "visible";
		String visibilityValue = "true";
		newPackage.addPackageItem(new ItemsData(visiblity,visibilityValue));	
		packages.add(newPackage);
	}
	
	private void addVoicePackage2() {
		newPackage = new Package();
		String packageType = "packageType";
		String packageTypeValue = "v";
		newPackage.addPackageItem(new ItemsData(packageType,packageTypeValue));
		String packageName = "packageName";
		String packageNameValue = "Lucky Package";
		newPackage.addPackageItem(new ItemsData(packageName,packageNameValue));
		String voiceUsed = "voiceUsed";
		String voiceUsedValue = "500";
		newPackage.addPackageItem(new ItemsData(voiceUsed,voiceUsedValue));
		String voiceRemained= "voiceRemained";
		String voiceRemainedValue= "600";
		newPackage.addPackageItem(new ItemsData(voiceRemained,voiceRemainedValue));
		String endDate = "endDate";
		String endDateValue = "25.08.2021";
		newPackage.addPackageItem(new ItemsData(endDate,endDateValue));
		String businessZone = "businessZone";
		String businessZoneValue = "YURT İÇİ";
		newPackage.addPackageItem(new ItemsData(businessZone,businessZoneValue));
		String visiblity = "visible";
		String visibilityValue = "true";
		newPackage.addPackageItem(new ItemsData(visiblity,visibilityValue));
		packages.add(newPackage);
	}
	
	private void addSmsPackage1() {
		newPackage = new Package();
		String packageType = "packageType";
		String packageTypeValue = "s";
		newPackage.addPackageItem(new ItemsData(packageType,packageTypeValue));
		String packageName = "packageName";
		String packageNameValue = "Sms Monster";
		newPackage.addPackageItem(new ItemsData(packageName,packageNameValue));
		String smsUsed = "smsUsed";
		String smsUsedValue = "200";
		newPackage.addPackageItem(new ItemsData(smsUsed,smsUsedValue));
		String smsRemained= "smsRemained";
		String smsRemainedValue= "300";
		newPackage.addPackageItem(new ItemsData(smsRemained,smsRemainedValue));
		String endDate = "endDate";
		String endDateValue = "17.10.2021";
		newPackage.addPackageItem(new ItemsData(endDate,endDateValue));	
		String businessZone = "businessZone";
		String businessZoneValue = "YURT İÇİ";
		newPackage.addPackageItem(new ItemsData(businessZone,businessZoneValue));
		String visiblity = "visible";
		String visibilityValue = "true";
		newPackage.addPackageItem(new ItemsData(visiblity,visibilityValue));
		packages.add(newPackage);
	}

	private void addSmsPackage2() {
		newPackage = new Package();
		String packageType = "packageType";
		String packageTypeValue = "s";
		newPackage.addPackageItem(new ItemsData(packageType,packageTypeValue));
		String packageName = "packageName";
		String packageNameValue = "Sms Beautiy";
		newPackage.addPackageItem(new ItemsData(packageName,packageNameValue));
		String smsUsed = "smsUsed";
		String smsUsedValue = "200";
		newPackage.addPackageItem(new ItemsData(smsUsed,smsUsedValue));
		String smsRemained= "smsRemained";
		String smsRemainedValue= "300";
		newPackage.addPackageItem(new ItemsData(smsRemained,smsRemainedValue));
		String endDate = "endDate";
		String endDateValue = "17.11.2021";
		newPackage.addPackageItem(new ItemsData(endDate,endDateValue));	
		String businessZone = "businessZone";
		String businessZoneValue = "YURT İÇİ";
		newPackage.addPackageItem(new ItemsData(businessZone,businessZoneValue));
		String visiblity = "visible";
		String visibilityValue = "false";
		newPackage.addPackageItem(new ItemsData(visiblity,visibilityValue));
		packages.add(newPackage);
	}
	
	private void addDataPackage1() {
		newPackage = new Package();
		String packageType = "packageType";
		String packageTypeValue = "d";
		newPackage.addPackageItem(new ItemsData(packageType,packageTypeValue));
		String packageName = "packageName";
		String packageNameValue = "New Year Suprise";
		newPackage.addPackageItem(new ItemsData(packageName,packageNameValue));
		String dataUsed = "dataUsed";
		String dataUsedValue = "200";
		newPackage.addPackageItem(new ItemsData(dataUsed,dataUsedValue));
		String dataRemained= "dataRemained";
		String dataRemainedValue= "300";
		newPackage.addPackageItem(new ItemsData(dataRemained,dataRemainedValue));
		String endDate = "endDate";
		String endDateValue = "17.08.2021";
		newPackage.addPackageItem(new ItemsData(endDate,endDateValue));	
		String businessZone = "businessZone";
		String businessZoneValue = "YURT İÇİ";
		newPackage.addPackageItem(new ItemsData(businessZone,businessZoneValue));
		String visiblity = "visible";
		String visibilityValue = "false";
		newPackage.addPackageItem(new ItemsData(visiblity,visibilityValue));
		packages.add(newPackage);
	}

	private void addDataPackage2() {
		newPackage = new Package();
		String packageType = "packageType";
		String packageTypeValue = "d";
		newPackage.addPackageItem(new ItemsData(packageType,packageTypeValue));
		String packageName = "packageName";
		String packageNameValue = "Happy Face";
		newPackage.addPackageItem(new ItemsData(packageName,packageNameValue));
		String dataUsed = "dataUsed";
		String dataUsedValue = "200";
		newPackage.addPackageItem(new ItemsData(dataUsed,dataUsedValue));
		String dataRemained= "dataRemained";
		String dataRemainedValue= "300";
		newPackage.addPackageItem(new ItemsData(dataRemained,dataRemainedValue));
		String endDate = "endDate";
		String endDateValue = "17.08.2021";
		newPackage.addPackageItem(new ItemsData(endDate,endDateValue));	
		String businessZone = "businessZone";
		String businessZoneValue = "YURT İÇİ";
		newPackage.addPackageItem(new ItemsData(businessZone,businessZoneValue));
		String visiblity = "visible";
		String visibilityValue = "true";
		newPackage.addPackageItem(new ItemsData(visiblity,visibilityValue));
		packages.add(newPackage);
	}
	
	private void addPackages(List<Package> packages) {
		for(Package currentPackage: packages) {
			this.currentPackageToAdd = currentPackage;
			addPackage();
		}
	}
	
	private void addPackage() {
		newItem = new Item();
		for(ItemsData currentPackageItem: this.currentPackageToAdd.getPackageInfos()) {
			newItem.addData(currentPackageItem);
		}
		itemsBuilder.addItem(newItem);
	}
	
	/*
	private void addLinks() {
		addOwner();
		collectionBuilder.addLinks(linksBuilder.getConstructedLinks());
	}*/

	
	/*
	private void addOwner() {
		final String rel = "owner";
		final String href = "/api/users/5";
		linksBuilder.addLinkRelation(new LinkRelation(rel, href));
	}*/

}
