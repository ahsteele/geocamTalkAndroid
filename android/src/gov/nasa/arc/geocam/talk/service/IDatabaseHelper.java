// __BEGIN_LICENSE__
// Copyright (C) 2008-2010 United States Government as represented by
// the Administrator of the National Aeronautics and Space Administration.
// All Rights Reserved.
// __END_LICENSE__

package gov.nasa.arc.geocam.talk.service;

import gov.nasa.arc.geocam.talk.bean.GeoCamTalkMessage;

import java.sql.SQLException;

import com.j256.ormlite.dao.Dao;

// TODO: Auto-generated Javadoc
/**
 * The Interface IDatabaseHelper.
 */
public interface IDatabaseHelper {
	
	/**
	 * Gets the geo cam talk message dao.
	 *
	 * @return the geo cam talk message dao
	 * @throws SQLException the sQL exception
	 */
	public Dao<GeoCamTalkMessage, Integer> getGeoCamTalkMessageDao() throws SQLException;
}
