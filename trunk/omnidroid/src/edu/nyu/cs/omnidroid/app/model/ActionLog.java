/*******************************************************************************
 * Copyright 2010 OmniDroid - http://code.google.com/p/omnidroid
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
package edu.nyu.cs.omnidroid.app.model;

import android.content.Context;
import edu.nyu.cs.omnidroid.app.controller.Action;

/**
 * This class represents an Action {@code Log}. Logs are displayed on the ActivityLogs for
 * users to see what is going on.
 */
public class ActionLog extends Log {
  public static final String TAG = ActionLog.class.getSimpleName();

  // Extended Log Constructs
  private Long logEventID;
  private String ruleName;
  private String appName;
  private String actionName;
  private String parameters;

  /**
   * @param context
   *          application context for the db connection
   * @param action
   *          the Action to create a {@code Log} out of
   * @param logEventID
   *          the {@code Event} that caused this action.
   * 
   */
  public ActionLog(Context context, Action action, Long logEventID) {
    this.context = context;
    this.ruleName = action.getRuleName();
    this.logEventID = logEventID;
    this.appName = action.getAppName();
    this.actionName = action.getActionName();
    this.parameters = action.getParameters();
    this.text = action.getDescription();
  }

  public ActionLog(ActionLog log) {
    super(log);
    this.context = log.context;
    this.ruleName = log.ruleName;
    this.logEventID = log.logEventID;
    this.appName = log.appName;
    this.actionName = log.actionName;
    this.parameters = log.parameters;
  }

  public ActionLog(Context context, long id, long timestamp, long logEventID, String ruleName,
      String appName, String actionName, String parameters, String text) {
    super(context, id, timestamp, text);
    this.ruleName = ruleName;
    this.logEventID = logEventID;
    this.appName = appName;
    this.actionName = actionName;
    this.parameters = parameters;
  }

  public void setLogEventID(Long logEventID) {
    this.logEventID = logEventID;
  }

  public Long getLogEventID() {
    return logEventID;
  }

  public void setRuleName(String ruleName) {
    this.ruleName = ruleName;
  }

  public String getRuleName() {
    return ruleName;
  }

  public void setAppName(String appName) {
    this.appName = appName;
  }

  public String getAppName() {
    return appName;
  }

  public void setActionName(String actionName) {
    this.actionName = actionName;
  }

  public String getActionName() {
    return actionName;
  }

  public void setParameters(String parameters) {
    this.parameters = parameters;
  }

  public String getParameters() {
    return parameters;
  }

  public String toString() {
    return "ID: " + id + "\nTimestamp: " + timestamp + "\nLogEventID: " + logEventID
        + "\nRuleName: " + ruleName + "\nApplication Name: " + appName + "\nAction Name: "
        + actionName + "\nParameters: " + parameters;
  }

  @Override
  public long insert() {
    CoreLogsDbHelper dbHelper = new CoreActionLogsDbHelper(context);
    long rowid = dbHelper.insert(this);
    dbHelper.close();
    return rowid;
  }
}