// ***** BEGIN LICENSE BLOCK *****
// Version: MPL 1.1/GPL 2.0/LGPL 2.1
// 
// The contents of this file are subject to the Mozilla Public License Version 
// 1.1 (the "License"); you may not use this file except in compliance with 
// the License. You may obtain a copy of the License at http://www.mozilla.org/MPL/
// 
// Software distributed under the License is distributed on an "AS IS" basis,
// WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
// for the specific language governing rights and limitations under the
// License.
// 
// The Original Code is part of dcm4che, an implementation of DICOM(TM) in Java(TM), hosted at http://sourceforge.net/projects/dcm4che
//  
// The Initial Developer of the Original Code is Agfa Healthcare.
// Portions created by the Initial Developer are Copyright (C) 2009 the Initial Developer. All Rights Reserved.
// 
// Contributor(s):
// Andrew Cowan <andrew.cowan@agfa.com>
// 
// Alternatively, the contents of this file may be used under the terms of
// either the GNU General Public License Version 2 or later (the "GPL"), or
// the GNU Lesser General Public License Version 2.1 or later (the "LGPL"),
// in which case the provisions of the GPL or the LGPL are applicable instead
// of those above. If you wish to allow use of your version of this file only
// under the terms of either the GPL or the LGPL, and not to allow others to
// use your version of this file under the terms of the MPL, indicate your
// decision by deleting the provisions above and replace them with the notice
// and other provisions required by the GPL or the LGPL. If you do not delete
// the provisions above, a recipient may use your version of this file under
// the terms of any one of the MPL, the GPL or the LGPL.
// 
// ***** END LICENSE BLOCK *****
package org.dcm4chee.xero.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Filter that will read in the client context and write it into the MDC class
 * so that it can be used by loggers.
 * 
 * @author Andrew Cowan (andrew.cowan@agfa.com)
 */
public class LoggingDiagnosticContextFilter implements Filter
{
   @SuppressWarnings("unused")
private static Logger log = LoggerFactory.getLogger(LoggingDiagnosticContextFilter.class);
   
   public static final String REMOTE_HOST = "remote.host";
   public static final String REMOTE_ADDRESS = "remote.address";
   public static final String REMOTE_USER = "remote.user";
   public static final String SESSION_ID = "session.id";

   /**
    * Fill in the Logging MDC with information about the current user
    * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
    */
   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
         ServletException
   {
      // Clear the MDC context if it is already defined
      if(MDC.getContext()!=null) MDC.getContext().clear();
      
      addToMDC(REMOTE_HOST, request.getRemoteHost());
      addToMDC(REMOTE_ADDRESS, request.getRemoteAddr());
      
      HttpServletRequest httpRequest = request instanceof HttpServletRequest
         ? (HttpServletRequest)request : null;
         
      if(httpRequest != null)
      {
         addToMDC(REMOTE_USER,httpRequest.getRemoteUser());
         
         // We only observe the session, we don't want to create one.
         HttpSession session = httpRequest.getSession(false);
         if(session!=null)
            addToMDC(SESSION_ID,session.getId());
      }
      
      chain.doFilter(request, response);
   }

   
   public void init(FilterConfig config) throws ServletException
   {
      // Do nothing...
   }

   public void destroy()
   {
      // Do nothing...
   }




   
   
   /**
    * Add the indicated key/value to the MDC if non-null.
    */
   private static void addToMDC(String key, Object value)
   {
      if(key == null || value == null)
         return;
      
      MDC.put(key, value);
   }

}
