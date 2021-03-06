/* ***** BEGIN LICENSE BLOCK *****
 * Version: MPL 1.1/GPL 2.0/LGPL 2.1
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is part of dcm4che, an implementation of DICOM(TM) in
 * Java(TM), hosted at http://sourceforge.net/projects/dcm4che.
 *
 * The Initial Developer of the Original Code is
 * Bill Wallace, Agfa HealthCare Inc., 
 * Portions created by the Initial Developer are Copyright (C) 2007
 * the Initial Developer. All Rights Reserved.
 *
 * Contributor(s):
 * Bill Wallace <bill.wallace@agfa.com>
 *
 * Alternatively, the contents of this file may be used under the terms of
 * either the GNU General Public License Version 2 or later (the "GPL"), or
 * the GNU Lesser General Public License Version 2.1 or later (the "LGPL"),
 * in which case the provisions of the GPL or the LGPL are applicable instead
 * of those above. If you wish to allow use of your version of this file only
 * under the terms of either the GPL or the LGPL, and not to allow others to
 * use your version of this file under the terms of the MPL, indicate your
 * decision by deleting the provisions above and replace them with the notice
 * and other provisions required by the GPL or the LGPL. If you do not delete
 * the provisions above, a recipient may use your version of this file under
 * the terms of any one of the MPL, the GPL or the LGPL.
 *
 * ***** END LICENSE BLOCK ***** */
package org.dcm4chee.xero.search.macro;

import java.util.Map;

import javax.xml.namespace.QName;

import org.dcm4chee.xero.search.study.Macro;


/**
 *  Represents a window level default - other window levels are represented by other macros.
 *  Attributes: windowCenter, windowWidth, windowExplanation.
 *  TODO support multi-valued choices for the window levels to allow a selection to be made.
 */
public class WindowLevelMacro implements Macro {
   public static final QName Q_WINDOW_CENTER = new QName(null,"windowCenter");
   public static final QName Q_WINDOW_WIDTH = new QName(null,"windowWidth");
   public static final QName Q_WINDOW_EXPLANATION = new QName(null,"windowExplanation");
   private float center, width;
   private String explanation;
   
   public WindowLevelMacro(float center, float width, String reason) {
	  this.width = width;
	  this.center = center;
	  this.explanation = reason;
   }

   public float getCenter() {
      return center;
   }

   public void setCenter(float center) {
      this.center = center;
   }

   public String getExplanation() {
      return explanation;
   }

   public void setExplanation(String reason) {
      this.explanation = reason;
   }

   public float getWidth() {
      return width;
   }

   public void setWidth(float width) {
      this.width = width;
   }

   public int updateAny(Map<QName, String> attrs) {
	 attrs.put(Q_WINDOW_CENTER, Float.toString(center));
	 attrs.put(Q_WINDOW_WIDTH,Float.toString(width));
	 attrs.put(Q_WINDOW_EXPLANATION,explanation);
	 return 3;
   }

   public String toString() {
	  return "WL(C:"+center+",W:"+width+",E:"+explanation+")";
   }
}
