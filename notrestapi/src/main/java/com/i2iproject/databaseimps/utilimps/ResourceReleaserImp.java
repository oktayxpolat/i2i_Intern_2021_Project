package com.i2iproject.databaseimps.utilimps;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.stereotype.Component;
import com.i2iproject.databaseimps.utils.ResourceReleaser;

@Component
public class ResourceReleaserImp implements ResourceReleaser{

	@Override
	public void relaseConnection(Connection connectionToRelase) {
		if(connectionToRelase!=null)
			try {
				connectionToRelase.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	@Override
	public void relaseCallableStatement(CallableStatement releaseCallableStatement) {
		if(releaseCallableStatement != null)
			try {
				releaseCallableStatement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
	}

}
