/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.lms.model.impl;

import com.liferay.lms.model.LearningActivityTry;
import com.liferay.lms.model.LearningActivityTryModel;
import com.liferay.lms.model.LearningActivityTrySoap;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.Serializable;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The base model implementation for the LearningActivityTry service. Represents a row in the &quot;Lms_LearningActivityTry&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link com.liferay.lms.model.LearningActivityTryModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link LearningActivityTryImpl}.
 * </p>
 *
 * @author TLS
 * @see LearningActivityTryImpl
 * @see com.liferay.lms.model.LearningActivityTry
 * @see com.liferay.lms.model.LearningActivityTryModel
 * @generated
 */
public class LearningActivityTryModelImpl extends BaseModelImpl<LearningActivityTry>
	implements LearningActivityTryModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a learning activity try model instance should use the {@link com.liferay.lms.model.LearningActivityTry} interface instead.
	 */
	public static final String TABLE_NAME = "Lms_LearningActivityTry";
	public static final Object[][] TABLE_COLUMNS = {
			{ "uuid_", Types.VARCHAR },
			{ "latId", Types.BIGINT },
			{ "actId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "startDate", Types.TIMESTAMP },
			{ "result", Types.BIGINT },
			{ "endDate", Types.TIMESTAMP },
			{ "tryData", Types.VARCHAR },
			{ "tryResultData", Types.VARCHAR },
			{ "comments", Types.VARCHAR }
		};
	public static final String TABLE_SQL_CREATE = "create table Lms_LearningActivityTry (uuid_ VARCHAR(75) null,latId LONG not null primary key,actId LONG,userId LONG,startDate DATE null,result LONG,endDate DATE null,tryData VARCHAR(75) null,tryResultData TEXT null,comments TEXT null)";
	public static final String TABLE_SQL_DROP = "drop table Lms_LearningActivityTry";
	public static final String ORDER_BY_JPQL = " ORDER BY learningActivityTry.latId ASC";
	public static final String ORDER_BY_SQL = " ORDER BY Lms_LearningActivityTry.latId ASC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.util.service.ServiceProps.get(
				"value.object.entity.cache.enabled.com.liferay.lms.model.LearningActivityTry"),
			false);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.util.service.ServiceProps.get(
				"value.object.finder.cache.enabled.com.liferay.lms.model.LearningActivityTry"),
			false);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.util.service.ServiceProps.get(
				"value.object.column.bitmask.enabled.com.liferay.lms.model.LearningActivityTry"),
			true);
	public static long ACTID_COLUMN_BITMASK = 1L;
	public static long USERID_COLUMN_BITMASK = 2L;
	public static long UUID_COLUMN_BITMASK = 4L;

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static LearningActivityTry toModel(LearningActivityTrySoap soapModel) {
		if (soapModel == null) {
			return null;
		}

		LearningActivityTry model = new LearningActivityTryImpl();

		model.setUuid(soapModel.getUuid());
		model.setLatId(soapModel.getLatId());
		model.setActId(soapModel.getActId());
		model.setUserId(soapModel.getUserId());
		model.setStartDate(soapModel.getStartDate());
		model.setResult(soapModel.getResult());
		model.setEndDate(soapModel.getEndDate());
		model.setTryData(soapModel.getTryData());
		model.setTryResultData(soapModel.getTryResultData());
		model.setComments(soapModel.getComments());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 */
	public static List<LearningActivityTry> toModels(
		LearningActivityTrySoap[] soapModels) {
		if (soapModels == null) {
			return null;
		}

		List<LearningActivityTry> models = new ArrayList<LearningActivityTry>(soapModels.length);

		for (LearningActivityTrySoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.util.service.ServiceProps.get(
				"lock.expiration.time.com.liferay.lms.model.LearningActivityTry"));

	public LearningActivityTryModelImpl() {
	}

	public long getPrimaryKey() {
		return _latId;
	}

	public void setPrimaryKey(long primaryKey) {
		setLatId(primaryKey);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_latId);
	}

	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	public Class<?> getModelClass() {
		return LearningActivityTry.class;
	}

	public String getModelClassName() {
		return LearningActivityTry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("latId", getLatId());
		attributes.put("actId", getActId());
		attributes.put("userId", getUserId());
		attributes.put("startDate", getStartDate());
		attributes.put("result", getResult());
		attributes.put("endDate", getEndDate());
		attributes.put("tryData", getTryData());
		attributes.put("tryResultData", getTryResultData());
		attributes.put("comments", getComments());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long latId = (Long)attributes.get("latId");

		if (latId != null) {
			setLatId(latId);
		}

		Long actId = (Long)attributes.get("actId");

		if (actId != null) {
			setActId(actId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		Date startDate = (Date)attributes.get("startDate");

		if (startDate != null) {
			setStartDate(startDate);
		}

		Long result = (Long)attributes.get("result");

		if (result != null) {
			setResult(result);
		}

		Date endDate = (Date)attributes.get("endDate");

		if (endDate != null) {
			setEndDate(endDate);
		}

		String tryData = (String)attributes.get("tryData");

		if (tryData != null) {
			setTryData(tryData);
		}

		String tryResultData = (String)attributes.get("tryResultData");

		if (tryResultData != null) {
			setTryResultData(tryResultData);
		}

		String comments = (String)attributes.get("comments");

		if (comments != null) {
			setComments(comments);
		}
	}

	public String getUuid() {
		if (_uuid == null) {
			return StringPool.BLANK;
		}
		else {
			return _uuid;
		}
	}

	public void setUuid(String uuid) {
		if (_originalUuid == null) {
			_originalUuid = _uuid;
		}

		_uuid = uuid;
	}

	public String getOriginalUuid() {
		return GetterUtil.getString(_originalUuid);
	}

	public long getLatId() {
		return _latId;
	}

	public void setLatId(long latId) {
		_columnBitmask = -1L;

		_latId = latId;
	}

	public long getActId() {
		return _actId;
	}

	public void setActId(long actId) {
		_columnBitmask |= ACTID_COLUMN_BITMASK;

		if (!_setOriginalActId) {
			_setOriginalActId = true;

			_originalActId = _actId;
		}

		_actId = actId;
	}

	public long getOriginalActId() {
		return _originalActId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_columnBitmask |= USERID_COLUMN_BITMASK;

		if (!_setOriginalUserId) {
			_setOriginalUserId = true;

			_originalUserId = _userId;
		}

		_userId = userId;
	}

	public String getUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getUserId(), "uuid", _userUuid);
	}

	public void setUserUuid(String userUuid) {
		_userUuid = userUuid;
	}

	public long getOriginalUserId() {
		return _originalUserId;
	}

	public Date getStartDate() {
		return _startDate;
	}

	public void setStartDate(Date startDate) {
		_startDate = startDate;
	}

	public long getResult() {
		return _result;
	}

	public void setResult(long result) {
		_result = result;
	}

	public Date getEndDate() {
		return _endDate;
	}

	public void setEndDate(Date endDate) {
		_endDate = endDate;
	}

	public String getTryData() {
		if (_tryData == null) {
			return StringPool.BLANK;
		}
		else {
			return _tryData;
		}
	}

	public void setTryData(String tryData) {
		_tryData = tryData;
	}

	public String getTryResultData() {
		if (_tryResultData == null) {
			return StringPool.BLANK;
		}
		else {
			return _tryResultData;
		}
	}

	public void setTryResultData(String tryResultData) {
		_tryResultData = tryResultData;
	}

	public String getComments() {
		if (_comments == null) {
			return StringPool.BLANK;
		}
		else {
			return _comments;
		}
	}

	public void setComments(String comments) {
		_comments = comments;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(0,
			LearningActivityTry.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public LearningActivityTry toEscapedModel() {
		if (_escapedModelProxy == null) {
			_escapedModelProxy = (LearningActivityTry)ProxyUtil.newProxyInstance(_classLoader,
					_escapedModelProxyInterfaces,
					new AutoEscapeBeanHandler(this));
		}

		return _escapedModelProxy;
	}

	@Override
	public Object clone() {
		LearningActivityTryImpl learningActivityTryImpl = new LearningActivityTryImpl();

		learningActivityTryImpl.setUuid(getUuid());
		learningActivityTryImpl.setLatId(getLatId());
		learningActivityTryImpl.setActId(getActId());
		learningActivityTryImpl.setUserId(getUserId());
		learningActivityTryImpl.setStartDate(getStartDate());
		learningActivityTryImpl.setResult(getResult());
		learningActivityTryImpl.setEndDate(getEndDate());
		learningActivityTryImpl.setTryData(getTryData());
		learningActivityTryImpl.setTryResultData(getTryResultData());
		learningActivityTryImpl.setComments(getComments());

		learningActivityTryImpl.resetOriginalValues();

		return learningActivityTryImpl;
	}

	public int compareTo(LearningActivityTry learningActivityTry) {
		int value = 0;

		if (getLatId() < learningActivityTry.getLatId()) {
			value = -1;
		}
		else if (getLatId() > learningActivityTry.getLatId()) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		LearningActivityTry learningActivityTry = null;

		try {
			learningActivityTry = (LearningActivityTry)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long primaryKey = learningActivityTry.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	@Override
	public void resetOriginalValues() {
		LearningActivityTryModelImpl learningActivityTryModelImpl = this;

		learningActivityTryModelImpl._originalUuid = learningActivityTryModelImpl._uuid;

		learningActivityTryModelImpl._originalActId = learningActivityTryModelImpl._actId;

		learningActivityTryModelImpl._setOriginalActId = false;

		learningActivityTryModelImpl._originalUserId = learningActivityTryModelImpl._userId;

		learningActivityTryModelImpl._setOriginalUserId = false;

		learningActivityTryModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<LearningActivityTry> toCacheModel() {
		LearningActivityTryCacheModel learningActivityTryCacheModel = new LearningActivityTryCacheModel();

		learningActivityTryCacheModel.uuid = getUuid();

		String uuid = learningActivityTryCacheModel.uuid;

		if ((uuid != null) && (uuid.length() == 0)) {
			learningActivityTryCacheModel.uuid = null;
		}

		learningActivityTryCacheModel.latId = getLatId();

		learningActivityTryCacheModel.actId = getActId();

		learningActivityTryCacheModel.userId = getUserId();

		Date startDate = getStartDate();

		if (startDate != null) {
			learningActivityTryCacheModel.startDate = startDate.getTime();
		}
		else {
			learningActivityTryCacheModel.startDate = Long.MIN_VALUE;
		}

		learningActivityTryCacheModel.result = getResult();

		Date endDate = getEndDate();

		if (endDate != null) {
			learningActivityTryCacheModel.endDate = endDate.getTime();
		}
		else {
			learningActivityTryCacheModel.endDate = Long.MIN_VALUE;
		}

		learningActivityTryCacheModel.tryData = getTryData();

		String tryData = learningActivityTryCacheModel.tryData;

		if ((tryData != null) && (tryData.length() == 0)) {
			learningActivityTryCacheModel.tryData = null;
		}

		learningActivityTryCacheModel.tryResultData = getTryResultData();

		String tryResultData = learningActivityTryCacheModel.tryResultData;

		if ((tryResultData != null) && (tryResultData.length() == 0)) {
			learningActivityTryCacheModel.tryResultData = null;
		}

		learningActivityTryCacheModel.comments = getComments();

		String comments = learningActivityTryCacheModel.comments;

		if ((comments != null) && (comments.length() == 0)) {
			learningActivityTryCacheModel.comments = null;
		}

		return learningActivityTryCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{uuid=");
		sb.append(getUuid());
		sb.append(", latId=");
		sb.append(getLatId());
		sb.append(", actId=");
		sb.append(getActId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", startDate=");
		sb.append(getStartDate());
		sb.append(", result=");
		sb.append(getResult());
		sb.append(", endDate=");
		sb.append(getEndDate());
		sb.append(", tryData=");
		sb.append(getTryData());
		sb.append(", tryResultData=");
		sb.append(getTryResultData());
		sb.append(", comments=");
		sb.append(getComments());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(34);

		sb.append("<model><model-name>");
		sb.append("com.liferay.lms.model.LearningActivityTry");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>uuid</column-name><column-value><![CDATA[");
		sb.append(getUuid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>latId</column-name><column-value><![CDATA[");
		sb.append(getLatId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>actId</column-name><column-value><![CDATA[");
		sb.append(getActId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userId</column-name><column-value><![CDATA[");
		sb.append(getUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>startDate</column-name><column-value><![CDATA[");
		sb.append(getStartDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>result</column-name><column-value><![CDATA[");
		sb.append(getResult());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>endDate</column-name><column-value><![CDATA[");
		sb.append(getEndDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>tryData</column-name><column-value><![CDATA[");
		sb.append(getTryData());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>tryResultData</column-name><column-value><![CDATA[");
		sb.append(getTryResultData());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>comments</column-name><column-value><![CDATA[");
		sb.append(getComments());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static ClassLoader _classLoader = LearningActivityTry.class.getClassLoader();
	private static Class<?>[] _escapedModelProxyInterfaces = new Class[] {
			LearningActivityTry.class
		};
	private String _uuid;
	private String _originalUuid;
	private long _latId;
	private long _actId;
	private long _originalActId;
	private boolean _setOriginalActId;
	private long _userId;
	private String _userUuid;
	private long _originalUserId;
	private boolean _setOriginalUserId;
	private Date _startDate;
	private long _result;
	private Date _endDate;
	private String _tryData;
	private String _tryResultData;
	private String _comments;
	private long _columnBitmask;
	private LearningActivityTry _escapedModelProxy;
}