package com.liferay.lms.activitymanager;

import com.liferay.lms.model.Course;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.Module;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.ModuleLocalServiceUtil;
import com.liferay.manager.CleanLearningActivityTries;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.util.bridges.mvc.MVCPortlet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.ProcessAction;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

public class ActivityManagerPortlet extends MVCPortlet {
	Log log = LogFactoryUtil.getLog(ActivityManagerPortlet.class);

	protected String viewJSP;
	protected String detailJSP;

	public void init() throws PortletException {
		this.viewJSP = getInitParameter("view-template");
		this.detailJSP = getInitParameter("detail-template");
	}

	public void doView(RenderRequest renderRequest,RenderResponse renderResponse) throws IOException, PortletException {
		String view = ParamUtil.getString(renderRequest, "view", "");
		if (view != null && "detail".equals(view)) {
			String id = ParamUtil.getString(renderRequest, "id", "");
			Long lid = null;
			HashMap<Long, List<LearningActivity>> learningActivities = new HashMap<Long, List<LearningActivity>>();
			try{
				lid = Long.parseLong(id);
			}catch(NumberFormatException nfe){
				nfe.printStackTrace();
			}
			
			if(lid!=null){
				Course course = null;
				try {
					course = CourseLocalServiceUtil.getCourse(lid);
				} catch (PortalException e) {
					e.printStackTrace();
				} catch (SystemException e) {
					e.printStackTrace();
				}
				if(course!=null){
					List<Module> modules = null;
					try {
						modules = ModuleLocalServiceUtil.findAllInGroup(course.getGroupCreatedId());
					} catch (SystemException e) {
						e.printStackTrace();
					}
					
					if(modules!=null){
						for(Module module:modules){
							List<Long> lacts = null;
							try {
								lacts = LearningActivityLocalServiceUtil.getLearningActivityIdsOfModule(module.getModuleId());
							} catch (SystemException e) {
								e.printStackTrace();
							}
							
							if(lacts!=null){
								List<LearningActivity> acts = new ArrayList<LearningActivity>();
								for(Long idact : lacts){
									try {
										acts.add(LearningActivityLocalServiceUtil.getLearningActivity(idact));
									} catch (PortalException e) {
										e.printStackTrace();
									} catch (SystemException e) {
										e.printStackTrace();
									}
								}
								
								learningActivities.put(module.getModuleId(), acts);
							}
						}
					}
					renderRequest.setAttribute("modules", modules);
					renderRequest.setAttribute("learningActivities", learningActivities);
					renderRequest.setAttribute("course", course);
				}

				include(this.detailJSP, renderRequest, renderResponse);
			}
		} else {
			String freetext = ParamUtil
					.getString(renderRequest, "freetext", "");

			SearchContext scon = new SearchContext();

			ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest
					.getAttribute("THEME_DISPLAY");
			scon.setCompanyId(themeDisplay.getCompanyId());

			if ((freetext != null) && (!"".equals(freetext))) {
				renderRequest.setAttribute("freetext", freetext);
				scon.setKeywords(freetext + "*");
			}

			Indexer indexer = IndexerRegistryUtil.getIndexer(Course.class);
			Hits hits = null;
			Document[] docs = (Document[]) null;
			try {
				hits = indexer.search(scon);
				docs = hits.getDocs();
			} catch (SearchException e) {
				e.printStackTrace();
			}

			if (docs != null) {
				if (this.log.isDebugEnabled())
					this.log.debug("Result size::" + docs.length);
				renderRequest.setAttribute("docs", docs);
			}

			renderRequest.setAttribute("state", Integer.valueOf(0));
			include(this.viewJSP, renderRequest, renderResponse);
		}
	}

	@ProcessAction(name = "search")
	public void search(ActionRequest request, ActionResponse response) {
		String freetext = ParamUtil.getString(request, "freetext", "");
		String state = ParamUtil.getString(request, "state", "");

		response.setRenderParameter("freetext", freetext);
		response.setRenderParameter("state", state);
	}

	@ProcessAction(name = "viewCourse")
	public void viewCourse(ActionRequest request, ActionResponse response) {
		if (log.isDebugEnabled())log.debug("viewCourse");
		String view = ParamUtil.getString(request, "view", "detail");
		String id = ParamUtil.getString(request, "id", "");

		response.setRenderParameter("view", view);
		response.setRenderParameter("id", id);
	}

	@ProcessAction(name = "fix")
	public void fix(ActionRequest request, ActionResponse response) {
		if (log.isDebugEnabled())log.debug("fix");
		String sid = ParamUtil.getString(request, "id", "");
		
		Long id = null;
		LearningActivity la = null;
		try{
			id = Long.parseLong(sid);
			la = LearningActivityLocalServiceUtil.getLearningActivity(id);
		}catch(Exception e){
			e.printStackTrace();
			response.setRenderParameter("error", "no-activity");
		}

		if(la!=null){
			PermissionChecker permissionChecker = PermissionThreadLocal.getPermissionChecker();
			if( permissionChecker.hasPermission(la.getGroupId(), LearningActivity.class.getName(), la.getActId(), ActionKeys.UPDATE)){
				switch (la.getTypeId()) {
				case 0:
					cleanLearningActivityTries(la);
					response.setRenderParameter("status", "ok");
					break;
				case 2:
					cleanLearningActivityTries(la);
					response.setRenderParameter("status", "ok");
					break;
				case 3:
					cleanLearningActivityTries(la);
					response.setRenderParameter("status", "ok");
					break;
				case 7:
					cleanLearningActivityTries(la);
					response.setRenderParameter("status", "ok");
					break;
				default:

					response.setRenderParameter("status", "ko");
					break;
				}
			}
		}else{
			response.setRenderParameter("error", "no-permissions");
		}
	}
	
	private void cleanLearningActivityTries(LearningActivity la){
		CleanLearningActivityTries clat = new CleanLearningActivityTries(la);
		Thread tclat = new Thread (clat);
		tclat.start();
	}

	protected void include(String path, RenderRequest renderRequest,RenderResponse renderResponse) throws IOException, PortletException {
		PortletRequestDispatcher portletRequestDispatcher = getPortletContext().getRequestDispatcher(path);

		if (portletRequestDispatcher != null) {
			portletRequestDispatcher.include(renderRequest, renderResponse);
		}
	}
}