package com.i2iproject.responderimps;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.i2iproject.database.PackageInfoGetter;
import com.i2iproject.database.models.FullPackageInfo;
import com.i2iproject.database.models.PackageGetRequestInfo;
import com.i2iproject.responders.PackageInfoResponder;
import com.i2iproject.responders.models.PackageResponseItem;

@Component
@Scope("prototype")
public class PackageInfoResponderImp implements PackageInfoResponder{
	private PackageInfoGetter packageInfoGetter;
	
	private int userId;
	private List<PackageResponseItem> responsePackages;
	private List<FullPackageInfo> fullPackageInfoList;
	private FullPackageInfo currentPackage;
	
	private PackageResponseItem currentPackageResponseItem;

	
	public PackageInfoResponderImp(PackageInfoGetter packageInfoGetter) {
		this.packageInfoGetter = packageInfoGetter;
	}
	
	@Override
	public List<PackageResponseItem> getPackageInfoById(int userId) {
		this.userId = userId;
		
		responsePackages = new ArrayList<>();
		
		getFullPackageInfoList();
		addFullPackagesToPackageResponseItemList();
		return responsePackages;
	}
	
	private void getFullPackageInfoList() {
		fullPackageInfoList = packageInfoGetter.getPackageInfo(new PackageGetRequestInfo(userId));
	}
	
	private void addFullPackagesToPackageResponseItemList() {
		for(FullPackageInfo currentPackage: fullPackageInfoList) {
			this.currentPackage = currentPackage;
			convertCurrentFullPackageInfoToPackageResponseItem();
			addConvertedInfoToList();
		}
	}
	
	private void convertCurrentFullPackageInfoToPackageResponseItem() {
		currentPackageResponseItem = new PackageResponseItem();
		currentPackageResponseItem.setBusinessZone(currentPackage.getBusinessZone());
		currentPackageResponseItem.setEndDate(currentPackage.getEndDate());
		currentPackageResponseItem.setPackageName(currentPackage.getPackageName());
		currentPackageResponseItem.setPackageType(currentPackage.getPackageType());
		currentPackageResponseItem.setRemainedAmount(currentPackage.getPackageLimit() - currentPackage.getUsedAmount());
		currentPackageResponseItem.setUsedAmount(currentPackage.getUsedAmount());
		currentPackageResponseItem.setVisible(currentPackage.isVisible());
	}

	private void addConvertedInfoToList() {
		responsePackages.add(currentPackageResponseItem);
	}
}
