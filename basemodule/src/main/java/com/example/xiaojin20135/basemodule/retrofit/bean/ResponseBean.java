package com.example.xiaojin20135.basemodule.retrofit.bean;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by lixiaojin on 2018-07-12.
 * 功能描述：
 */

public class ResponseBean {
    private String[] actionErrors;
    private String[] actionMessages;
    private ActionResult actionResult;
    private int adminlevel;

    private String[] cityList;
    private String[] companycategoryList;
    private String[] companynatureList;
    private String[] countyList;
    private List<Map> enabledList;
    private List<Map> stateList;
    private String mapJson;
    private Map paraDataMap;
    private String[] provinceList;
    private Map queryParamMap;
    private String treeJson;
    private JSONArray treeJsonMobile;
    private UserBean userBean;
    private Map dataMap;
    private String method;
    private List<Map> listDataMap;
    private List<Map> dataList;
    private List<Map> flowidList;
    private List<Map> mailtypeList;
    private boolean timeout;

    private String code;
    private String message;
    private Map result;

    private boolean success;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map getResult() {
        return result;
    }

    public void setResult(Map result) {
        this.result = result;
    }

    private String records;//共有多少条记录
    private String total;//共有多少条页
    private String page; //当前页号
    //文件流
    private InputStream inputStream;

    public String[] getActionErrors() {
        return actionErrors;
    }

    public void setActionErrors(String[] actionErrors) {
        if(actionErrors != null)
            this.actionErrors = actionErrors;
    }

    public boolean isTimeout () {
        return timeout;
    }

    public void setTimeout (boolean timeout) {
        this.timeout = timeout;
    }

    public String[] getActionMessages() {
        return actionMessages;
    }

    public void setActionMessages(String[] actionMessages) {
        if(actionMessages != null)
            this.actionMessages = actionMessages;
    }

    public ActionResult getActionResult() {
        return actionResult;
    }

    public void setActionResult(ActionResult actionResult) {
        if(actionResult != null)
            this.actionResult = actionResult;
    }

    public int getAdminlevel() {
        return adminlevel;
    }

    public void setAdminlevel(int adminlevel) {
        this.adminlevel = adminlevel;
    }

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        if(userBean != null)
            this.userBean = userBean;
    }

    public Map getDataMap() {
        return dataMap;
    }

    public void setDataMap(Map dataMap) {
        if(dataMap != null)
            this.dataMap = dataMap;
    }

    public List<Map> getListDataMap() {
        return listDataMap;
    }

    public void setListDataMap(List<Map> listDataMap) {
        if(listDataMap != null)
            this.listDataMap = listDataMap;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        if(inputStream != null)
            this.inputStream = inputStream;
    }

    public String getRecords() {
        return records;
    }

    public void setRecords(String records) {
        this.records = records;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public List<Map> getFlowidList() {
        return flowidList;
    }

    public void setFlowidList(List<Map> flowidList) {
        this.flowidList = flowidList;
    }

    public String getMethod () {
        return method;
    }

    public void setMethod (String method) {
        this.method = method;
    }

    public String[] getCityList () {
        return cityList;
    }

    public void setCityList (String[] cityList) {
        this.cityList = cityList;
    }

    public String[] getCompanycategoryList () {
        return companycategoryList;
    }

    public void setCompanycategoryList (String[] companycategoryList) {
        this.companycategoryList = companycategoryList;
    }

    public String[] getCompanynatureList () {
        return companynatureList;
    }

    public void setCompanynatureList (String[] companynatureList) {
        this.companynatureList = companynatureList;
    }

    public String[] getCountyList () {
        return countyList;
    }

    public void setCountyList (String[] countyList) {
        this.countyList = countyList;
    }

    public List<Map> getEnabledList () {
        return enabledList;
    }

    public void setEnabledList (List<Map> enabledList) {
        this.enabledList = enabledList;
    }

    public List<Map> getStateList () {
        return stateList;
    }

    public void setStateList (List<Map> stateList) {
        this.stateList = stateList;
    }

    public String getMapJson () {
        return mapJson;
    }

    public void setMapJson (String mapJson) {
        this.mapJson = mapJson;
    }

    public Map getParaDataMap () {
        return paraDataMap;
    }

    public void setParaDataMap (Map paraDataMap) {
        this.paraDataMap = paraDataMap;
    }


    public String[] getProvinceList () {
        return provinceList;
    }

    public void setProvinceList (String[] provinceList) {
        this.provinceList = provinceList;
    }

    public Map getQueryParamMap () {
        return queryParamMap;
    }

    public void setQueryParamMap (Map queryParamMap) {
        this.queryParamMap = queryParamMap;
    }

    public String getTreeJson () {
        return treeJson;
    }

    public void setTreeJson (String treeJson) {
        this.treeJson = treeJson;
    }

    public JSONArray getTreeJsonMobile () {
        if(treeJson.equals ("")){
            return null;
        }else{
            try {
                return new JSONArray (treeJson);
            } catch (JSONException e) {
                e.printStackTrace ();
                return null;
            }
        }
    }

    public List<Map> getDataList () {
        return dataList;
    }

    public void setDataList (List<Map> dataList) {
        this.dataList = dataList;
    }

    public List<Map> getMailtypeList () {
        return mailtypeList;
    }

    public void setMailtypeList (List<Map> mailtypeList) {
        this.mailtypeList = mailtypeList;
    }
}
