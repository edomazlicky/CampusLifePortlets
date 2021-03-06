/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.jasig.portlet.campuslife.athletics.mvc.portlet;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasig.portlet.campuslife.athletics.dao.IAthleticsDao;
import org.jasig.portlet.campuslife.athletics.model.feed.xml.AthleticsFeed;
import org.jasig.portlet.campuslife.athletics.model.feed.xml.Sport;
import org.jasig.portlet.campuslife.mvc.IViewSelector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

/**
 * Main portlet view.
 */
@Controller
@RequestMapping("VIEW")
public class MainController {

    protected final Log logger = LogFactory.getLog(getClass());
    
    private IViewSelector viewSelector;
    
    @Autowired(required = true)
    public void setViewSelector(IViewSelector viewSelector) {
        this.viewSelector = viewSelector;
    }
    
    private IAthleticsDao athleticsDao;
    
    @Autowired(required = true)
    public void setAthleticsDao(IAthleticsDao athleticsDao) {
        this.athleticsDao = athleticsDao;
    }
    
    @RenderMapping
    public ModelAndView showMainView(
            final RenderRequest request, final RenderResponse response) {

        // determine if the request represents a mobile browser and set the
        // view name accordingly
        final boolean isMobile = viewSelector.isMobile(request);
        final String viewName = isMobile ? "main-jQM" : "main";        
        final ModelAndView mav = new ModelAndView("athletics/" + viewName);
        
        if(logger.isDebugEnabled()) {
            logger.debug("Using view name " + viewName + " for main view");
        }
        
        final AthleticsFeed feed = athleticsDao.getFeed();
        mav.addObject("sports", feed.getSport());

        if(logger.isDebugEnabled()) {
            logger.debug("Rendering main view");
        }

        return mav;

    }

    @RenderMapping(params = "action=sportScores")
    public ModelAndView showSportScores(final String sport,
            final RenderRequest request, final RenderResponse response) {

        // determine if the request represents a mobile browser and set the
        // view name accordingly
        final boolean isMobile = viewSelector.isMobile(request);
        final String viewName = isMobile ? "sportScores-jQM" : "sportScores";        
        final ModelAndView mav = new ModelAndView("athletics/" + viewName);
        
        if(logger.isDebugEnabled()) {
            logger.debug("Using view name " + viewName + " for main view");
        }
        
        final Sport s = athleticsDao.getSport(sport);
        mav.addObject("sport", s);

        if(logger.isDebugEnabled()) {
            logger.debug("Rendering main view");
        }

        return mav;

    }

    @RenderMapping(params = "action=sportNews")
    public ModelAndView showSportNews(final String sport,
            final RenderRequest request, final RenderResponse response) {

        // determine if the request represents a mobile browser and set the
        // view name accordingly
        final boolean isMobile = viewSelector.isMobile(request);
        final String viewName = isMobile ? "sportNews-jQM" : "sportNews";        
        final ModelAndView mav = new ModelAndView("athletics/" + viewName);
        
        if(logger.isDebugEnabled()) {
            logger.debug("Using view name " + viewName + " for main view");
        }
        
        final Sport s = athleticsDao.getSport(sport);
        mav.addObject("sport", s);

        if(logger.isDebugEnabled()) {
            logger.debug("Rendering main view");
        }

        return mav;

    }

    @ActionMapping
    public void doAction() {
        // no-op action mapping to prevent accidental calls to this URL from
        // crashing the portlet
    }

}
