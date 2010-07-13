/*******************************************************************************
 * Copyright 2009, 2010 Omnidroid - http://code.google.com/p/omnidroid 
 *  
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 *  
 *     http://www.apache.org/licenses/LICENSE-2.0 
 *     
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License. 
 *******************************************************************************/
package edu.nyu.cs.omnidroid.app.controller.actions;

import java.util.HashMap;

import edu.nyu.cs.omnidroid.app.controller.Action;
import edu.nyu.cs.omnidroid.app.controller.util.ExceptionMessageMap;
import edu.nyu.cs.omnidroid.app.controller.util.OmnidroidException;

import android.content.Intent;

/**
 * This action wrap an intent to send to GMail service. It contains necessary information for the
 * GMail service to send a email out.
 */
public class SendGmailAction extends Action {

  /**
   * attributes field names
   */
  public static final String ACTION_NAME = "GMAIL SEND";
  public static final String APP_NAME = "GMAIL";
  public static final String GMAIL_INTENT = "omnidroid.intent.action.GMAIL_SEND";

  public static final String PARAM_USERNAME = "Username"; // @deprecated
  public static final String PARAM_PASSWORD = "Password"; // @deprecated
  public static final String PARAM_USER_ACCOUNT = "UserAccount";
  public static final String PARAM_TO = "EmailTo";
  public static final String PARAM_SUBJECT = "Subject";
  public static final String PARAM_BODY = "Body";

  /**
   * Content of the action
   */
  private String accountID;
  private String to;
  private String subject;
  private String body;

  public SendGmailAction(HashMap<String, String> parameters) throws OmnidroidException {
    super(SendGmailAction.GMAIL_INTENT, Action.BY_SERVICE);
    accountID = parameters.get(PARAM_USER_ACCOUNT);
    to = parameters.get(PARAM_TO);
    subject = parameters.get(PARAM_SUBJECT);
    body = parameters.get(PARAM_BODY);
    if (accountID == null || to == null || subject == null || body == null) {
      throw new OmnidroidException(120002, ExceptionMessageMap.getMessage(new Integer(120002)
          .toString()));
    }
  }

  @Override
  public Intent getIntent() {
    Intent intent = new Intent(getActionName());
    intent.putExtra(accountID, accountID);
    intent.putExtra(PARAM_TO, to);
    intent.putExtra(PARAM_SUBJECT, subject);
    intent.putExtra(PARAM_BODY, body);
    intent.putExtra(NOTIFICATION, notificationIsOn);
    return intent;
  }

  @Override
  public String getDescription() {
    return APP_NAME + "-" + ACTION_NAME;
  }

  public String getAppName() {
    return APP_NAME;
  }
}
