package com.i2iproject.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.i2iproject.controllers.factories.ProducerOfPackageInfoResponder;
import com.i2iproject.responders.PackageInfoResponder;
import com.i2iproject.responders.models.PackageResponse;
import com.i2iproject.responders.models.PackageResponseItem;

@RestController //PackageInfoResponder
public class UserController {
	private ProducerOfPackageInfoResponder producerOfPackageInfoResponder;
	
	public UserController(ProducerOfPackageInfoResponder producerOfPackageInfoResponder) {
		this.producerOfPackageInfoResponder = producerOfPackageInfoResponder;
	}
	
	@GetMapping(path = "/api/users/{userId}/package", produces = "application/json")
	public PackageResponse getPackageInfoOfTheUser(@PathVariable("userId") int userId) {
		List<PackageResponseItem> packages = getPackages(userId);
		return fillPackagesToResponse(packages);
	}
	
	private List<PackageResponseItem> getPackages(int userId) {
		PackageInfoResponder packageInfoResponder = producerOfPackageInfoResponder.producePackageInfoResponder();
		return packageInfoResponder.getPackageInfoById(userId);
	}
	
	private PackageResponse fillPackagesToResponse(List<PackageResponseItem> packages) {
		PackageResponse response = new PackageResponse();
		for(PackageResponseItem currentPackage : packages) {
			response.addPackageItem(currentPackage);
		}
		return response;
	}
	
}

	
