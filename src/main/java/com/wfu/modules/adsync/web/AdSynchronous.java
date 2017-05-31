package com.wfu.modules.adsync.web;

import com.google.common.collect.Lists;
import com.wfu.common.utils.ResData;
import com.wfu.common.utils.StringUtils;
import com.wfu.common.web.BaseController;
import com.wfu.modules.adsync.config.AdConfig;
import com.wfu.modules.adsync.service.AdUpdateService;
import com.wfu.modules.sys.dao.OfficeDao;
import com.wfu.modules.sys.entity.Area;
import com.wfu.modules.sys.entity.Office;
import com.wfu.modules.sys.entity.Role;
import com.wfu.modules.sys.entity.User;
import com.wfu.modules.sys.service.AreaService;
import com.wfu.modules.sys.service.OfficeService;
import com.wfu.modules.sys.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by Admin on 2017/2/21.
 * AD域同步
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/adsync")
public class AdSynchronous extends BaseController {

    @Autowired
    private AdUpdateService adUpdateService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private OfficeService officeService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private OfficeDao officeDao;

    //存放AD域组信息
    private Map oaGroupMap = new HashMap();
    private DirContext ctx;
    private Map adIdMap =  new HashMap();
    private String updDateTime;//最近一次同步事件

    private Office zeroOffice;//顶级组织
    private Role zeroRole;//普通用户角色
    private Area zeroArea;//基础区域

    //组织添加存放sqlLIst
    List<Office> addAdGroupList = new ArrayList<Office>();

    //组织修改存放sqlLIst
    List<Office> updAdGroupList = new ArrayList<Office>();

    //用户添加
    List<User> addAdUserList = new ArrayList<User>();

    //用户修改
    List<User> upUserList = new ArrayList<User>();

    //存放无效的组织信息
    Map<String, Office> unValidGroupMap = new HashMap<String, Office>();

    //存放无效的用户信息
    Map<String, User> unValidUserMap = new HashMap<String, User>();

    List<Office> officeList = new ArrayList<Office>();

    //构造函数
    public AdSynchronous() {
    }

    @RequestMapping(value = "initSync")
    public String initSync(){
        return "modules/rcs/adSynchronous";
    }

    /**
     * 进行同步
     * @return
     */
    @RequestMapping(value = "doSync")
    @ResponseBody
    public Object doSync(){
        ResData rData = new ResData();
        try {
            boolean flag = synchronization();
            rData.setSuccess(flag);
            rData.setMsg("同步成功。");
        }catch (Exception e){
            rData.setMsg("同步失败。");
            logger.error("[AdSynchronous doSync]", e);
        }
        return rData;
    }

    /**
     * 执行同步
     * @return
     */
    public boolean synchronization(){
        try {
            AdConfig adf = AdConfig.getAdConfig();
            Hashtable env = new Hashtable();
            env.put(Context.PROVIDER_URL, adf.getUrl() + URLEncoder.encode(adf.getOrg(), "utf-8"));
            env.put(Context.SECURITY_PRINCIPAL, adf.getUser());
            env.put(Context.SECURITY_CREDENTIALS, adf.getPassword());
            env.put(Context.INITIAL_CONTEXT_FACTORY,adf.getLdapfactory());
            env.put(Context.SECURITY_AUTHENTICATION, adf.getAuthentication());

            ctx = new InitialDirContext(env);

            zeroOffice = officeService.get("1");
            zeroRole = systemService.getRole("6");
            zeroArea = areaService.get("1");
            //组织用户同步
            return doSync(ctx);
        }catch (Exception e){
            logger.error("[AdSynchronous synchronization]:同步账号或密码错误！", e);
        }
        return false;
    }

    /**
     * 同步
     * @param ctx
     * @return
     */
    public boolean doSync(DirContext ctx){
        try {
            oaGroupMap = getAllGroups("");
            String groupName = "潍柴用户";
            if(StringUtils.isNotBlank(groupName)){
                //存放属性结构组织ID和名称
                Map treeGroupMap = new LinkedHashMap();
                //该组织是否叶子节点
                Map leafGroupMap = new LinkedHashMap();
                //存放AD组织各个属性信息
                Map syncGroupMap = new LinkedHashMap();
                Map groupsCompareMap = new LinkedHashMap();

                this.getAllGroupsTree("", "", groupName, treeGroupMap, leafGroupMap,
                        syncGroupMap, groupsCompareMap);

                Map syncMap = new HashMap();
                syncMap.put("groups", treeGroupMap);
                syncMap.put("syncGroup", syncGroupMap);
                syncMap.put("groupLeaf", leafGroupMap);
                syncMap.put("groupsCompare", groupsCompareMap);

                try{
                    //获取最新一次AD更新时间
                    updDateTime = adUpdateService.getUpdate();
                    Date nowDate = new Date();
                    if(updDateTime == null || updDateTime.equals("")){
                        //同步组织
                        this.groupSync(syncMap);
                    }
                    //同步组织
                    this.groupSync(syncMap);
                    //同步用户
                    List userList = this.getAllUsers("OA成员组");
                    this.userSync(userList, syncMap);
                    //更新同步时间
                    adUpdateService.updateInfo(nowDate);
                    return true;
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }catch (Exception e){
            logger.error("[AdSynchronous userSyn]", e);
        }
        return false;
    }


    public boolean groupSync(Map groupMap){
        //清空数据信息
        if (!addAdGroupList.isEmpty()) {
            addAdGroupList.clear();
        }
        if (!updAdGroupList.isEmpty()) {
            updAdGroupList.clear();
        }
        if (!unValidGroupMap.isEmpty()) {
            unValidGroupMap.clear();
        }
        try {
            Map syncMap = (Map)groupMap.get("syncGroup");

            //------------------------------------------------------------
            //第一步：同步组->组织
            //------------------------------------------------------------
            Set s = syncMap.entrySet();

            //获取计划管理系统中所以组织信息
            Office o = new Office();
            o.setRemarks("AD");
            officeList = officeDao.findList(o);//查询AD同步过来的所有的组织信息
            Map<String, Office> wcGroupMap = new HashMap<String, Office>();

            //list转化为map
            for (Office office : officeList) {
                wcGroupMap.put(office.getAdId(), office);
                unValidGroupMap.put(office.getAdId(), office);
            }

            //循环同步组织信息
            for(Iterator itor=s.iterator(); itor.hasNext();){
                Map.Entry ent = (Map.Entry)itor.next();
                Map map = (Map)ent.getValue();
                String organizationID = map.get("organizationID")==null || ((List)map.get("organizationID")).get(0)==null ? "" : ((List)map.get("organizationID")).get(0).toString();
                if(StringUtils.isNotBlank(organizationID)){
                    ldapToDBForGroup(map, adIdMap, wcGroupMap);
                }
            }

            //批量添加组织数据
            if (addAdGroupList != null && addAdGroupList.size() > 0) {
                officeService.saveList(addAdGroupList);
            }

            //批量修改组织数据
            if(updAdGroupList != null && updAdGroupList.size() > 0) {
                officeService.saveList(updAdGroupList);
            }

            //批量删除组织和所有子组织以及组织下的用户
            if(unValidGroupMap != null && unValidGroupMap.size() > 0) {
                for(Office office : unValidGroupMap.values()){
                    if("AD".equals(office.getRemarks())){//AD代表数据来源
                        officeService.delete(office);
                    }
                }
            }

        }catch (Exception e){
            logger.error("[AdSynchronous groupSync]", e);
        }
        return true;
    }

    /**
     * 同步用户
     * @param userList List
     * @param groupMap Map
     * @throws Exception
     */
    public void userSync(List userList, Map groupMap) throws Exception {
        //重置数据
        if (!addAdUserList.isEmpty()) {
            addAdUserList.clear();
        }
        if (!upUserList.isEmpty()) {
            upUserList.clear();
        }

        if (!unValidUserMap.isEmpty()) {
            unValidUserMap.clear();
        }
        try {
            Map gMap = (Map)groupMap.get("groups");
            Map groupsCompareMap = (Map)groupMap.get("groupsCompare");

            List<Office> wcOfficeList = officeService.findAll();

            List<User> wcUserList = systemService.findAllUser();
            Map<String, User> wcUserMap = new HashMap<String, User>();
            //用户list转化为map
            for (User user : wcUserList) {
                if(!user.getId().equals("1")){
                    wcUserMap.put(user.getLoginName(), user);
                    unValidUserMap.put(user.getLoginName(), user);
                }
            }

            if(userList!=null && userList.size()>0){
                for(int i=0; i<userList.size(); i++){
                    Map map = (Map)userList.get(i);

                    //用于主组判断
                    String mainGroup = map.get("organizationID")==null || ((List)map.get("organizationID")).get(0)==null ? "" : ((List)map.get("organizationID")).get(0).toString();//所属主组

                    String groupOrganizationID = "";
                    List tempGroupNameList = new ArrayList();
                    Map sidelineOrgMap = new HashMap();//用于存放多个组（对兼职组织）
                    Object ownerObj = map.get("memberOf");
                    if(ownerObj!=null){
                        List mList = (List)ownerObj;
                        for(int k=0; k<mList.size(); k++){
                            String val2 = (String)mList.get(k);
                            if(val2.startsWith("CN=")){
                                String organizationID_ = (String)oaGroupMap.get(val2);
                                if(StringUtils.isNotBlank(organizationID_)){
                                    tempGroupNameList.add(organizationID_);
                                    sidelineOrgMap.put(organizationID_, gMap.get(organizationID_));
                                }
                            }
                        }
                    }

                    //判断是否为主组（对于OA中所属组织）
                    if(tempGroupNameList!=null && tempGroupNameList.size()>0){
                        boolean isMainGroup = false;
                        if(StringUtils.isNotBlank(mainGroup)){
                            for(int j=0; j<tempGroupNameList.size(); j++){
                                String temp = (String)tempGroupNameList.get(j);
                                String organizationID = (String)groupsCompareMap.get(temp);
                                if(mainGroup.equals(organizationID)){
                                    groupOrganizationID = temp;
                                    sidelineOrgMap.put(groupOrganizationID, "");
                                    isMainGroup = true;
                                    break;
                                }
                            }
                        }

                        if(isMainGroup==false){
                            groupOrganizationID = (String)tempGroupNameList.get(0);//默认取第一个
                            sidelineOrgMap.put(groupOrganizationID, "");
                        }
                    }

                    //是否同步用户
                    if(StringUtils.isNotBlank(groupOrganizationID)){
                        String fullGroupName = (String)gMap.get(groupOrganizationID);
                        map.put("fullGroupName", fullGroupName);
                        map.put("sidelineOrgMap", sidelineOrgMap);
                        ldapToDBForUser(map, adIdMap,wcUserMap, wcOfficeList);
                    }
                }
            }


            //批量添加用户数据并给用户附成员角色和新增AD用户组织
            if (addAdUserList != null && addAdUserList.size() > 0) {
                systemService.saveUserList(addAdUserList);
            }

            //批量修改用户数据和AD用户组织
            if(upUserList != null && upUserList.size() > 0) {
                systemService.saveUserList(upUserList);
            }

            //批量把AD中无效的用户在系统中标志为无效
            if(unValidUserMap != null && unValidUserMap.size() > 0) {
                for(User user : unValidUserMap.values()){
                    if("AD".equals(user.getRemarks())){//AD代表数据来源
                        systemService.deleteUser(user);
                    }
                }
            }
        }catch (Exception e){
            logger.error("[AdSynchronous userSync]", e);
        }
    }

    /**
     * 获取所有用户
     * @return List
     */
    public List getAllUsers(String gn) {
        List ret = new ArrayList();
        //检索AD用户过滤条件
        StringBuffer filter_user = new StringBuffer();
        filter_user.append("(&(objectClass=user)");
        filter_user.append("(!(objectClass=computer))(!(objectClass=inetOrgPerson)))");

        NamingEnumeration namingEnum = null;
        //
        String[] atts={"cn", "name", "distinguishedName", "displayName", "objectGUID", "sAMAccountName", "sAMaccountType", "initials", "givenName", "memberOf", "mail", "whenChanged", "department", "organizationID", "userAccountControl"};
        try {
            SearchControls searchCons = new SearchControls();
            searchCons.setSearchScope(2);
            searchCons.setCountLimit(0);
            searchCons.setTimeLimit(0);
            searchCons.setReturningAttributes(atts);
            namingEnum = ctx.search("", filter_user.toString(), searchCons);

            //循环每个条目
            while (namingEnum != null && namingEnum.hasMore()) {
                Map map = new HashMap();
                boolean isOA = false;
                SearchResult result = (SearchResult) namingEnum.next();

                Attributes attrs = result.getAttributes();
                if (attrs.size() != 0) {
                    Object attValue = null;
                    for (NamingEnumeration namingEnum_1 = attrs.getAll();
                         namingEnum_1.hasMoreElements(); ) {
                        Attribute attribute = (Attribute) namingEnum_1.next();
                        String attID = attribute.getID();
                        for (NamingEnumeration namingEnum_2 = attribute.getAll();
                             namingEnum_2.hasMoreElements(); ) {
                            attValue = namingEnum_2.nextElement();

                            if(attID.equals("memberOf")){
                                String tmp = (String)attValue;
                                if(tmp.startsWith("CN="+gn+",")){
                                    isOA = true;
                                }
                            }

                            Object oldObj = (Object)map.get(attID);

                            if(oldObj != null){
                                List ll = (List)oldObj;
                                ll.add(attValue);
                            }else{
                                if (attID.equals("objectGUID")) {
                                    StringBuffer buffer = new StringBuffer();
                                    byte[] b = attValue.toString().getBytes();
                                    for (int i = 0; i < b.length; i++) {
                                        buffer.append(b[i]);
                                    }
                                    List ll = new ArrayList();
                                    ll.add(buffer.toString());
                                    map.put(attID, ll);
                                } else {
                                    List ll = new ArrayList();
                                    ll.add(attValue);
                                    map.put(attID, ll);
                                }
                            }
                        }
                    }

                    if(isOA){
                        ret.add(map);
                    }
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return ret;
    }

    /**
     * 得到所有的组
     * @param filter
     * @return
     */
    public Map getAllGroups(String filter) {

        Map ret = new HashMap();
        StringBuffer filter_group = new StringBuffer();
        filter_group.append("(&(objectClass=group)");
        filter_group.append("(!(objectClass=computer))(!(objectClass=inetOrgPerson)))");

        // 控制搜索的搜索控件，如果为 null，则使用默认的搜索控件
        NamingEnumeration namingEnum;
        // 限制要查询的字段内容
        String[] atts={"distinguishedName","organizationID"};
        try {
            SearchControls searchCons = new SearchControls();
            searchCons.setSearchScope(SearchControls.SUBTREE_SCOPE);
            searchCons.setCountLimit(0);
            searchCons.setTimeLimit(0);
            // 设置将被返回的Attribute
            searchCons.setReturningAttributes(atts);
            // 上下文；
            // 要搜索的属性，如果为空或 null，则返回目标上下文中的所有对象；
            // 控制搜索的搜索控件，如果为 null，则使用默认的搜索控件
            namingEnum = ctx.search(filter, filter_group.toString(), searchCons);

            //循环每个条目
            while (namingEnum != null && namingEnum.hasMore()) {
                SearchResult result = (SearchResult) namingEnum.next();
                Attributes attrs = result.getAttributes();
                if (attrs.size() != 0) {
                    String dn = "";
                    String organizationID = "";
                    Object attValue;
                    for (NamingEnumeration namingEnum_1 = attrs.getAll();namingEnum_1.hasMoreElements();) {
                        Attribute attribute = (Attribute) namingEnum_1.next();
                        String attID = attribute.getID();
                        for (NamingEnumeration namingEnum_2 = attribute.getAll();namingEnum_2.hasMoreElements();) {
                            attValue = namingEnum_2.nextElement();
                            if (attID.equals("distinguishedName")) {
                                dn = (String)attValue;
                            }else if(attID.equals("organizationID")){
                                organizationID = (String)attValue;
                            }
                        }
                    }
                    ret.put(dn, organizationID);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ret;
    }

    /**
     * 获取指定组下的所有组（包括隶属关系），如“潍柴用户”组下的所有组
     * @param parentDistinguishedName 父级组织显示名称
     * @param parentOrganizationID 父级组织adId
     * @param groupName 组织名称
     * @param treeGroupMap 存放属性结构组织ID和名称
     * @param leafGroupMap 该组织是否叶子节点
     * @param syncGroupMap //存放AD组织各个属性信息
     * @param groupsCompareMap
     * @return
     */
    public Map getAllGroupsTree(String parentDistinguishedName, String parentOrganizationID, String groupName, Map treeGroupMap, Map leafGroupMap, Map syncGroupMap, Map groupsCompareMap){
        Map ret = new LinkedHashMap();
        List groupList = this.getGroupsByGroupName(groupName);//获取AD中的组织列表
        if(groupList!=null && groupList.size()>0){
            for(int i=0; i<groupList.size(); i++){
                Map map = (Map)groupList.get(i);

                String selfGroupName = (String)((List)map.get("cn")).get(0);
                String distinguishedName = (String)((List)map.get("distinguishedName")).get(0);

                //组的编码
                String organizationID = map.get("organizationID")==null || ((List)map.get("organizationID")).get(0)==null ? "" : ((List)map.get("organizationID")).get(0).toString();
                if(StringUtils.isBlank(organizationID)){
                    continue;
                }

                //显示名称
                String displayName = selfGroupName;
                if(StringUtils.isBlank(displayName)){
                    continue;
                }
                String parentGroupName = "";//displayName str
                Object ownerObj = map.get("memberOf");
                if(ownerObj!=null){
                    List mList = (List)ownerObj;
                    if(StringUtils.isNotBlank(parentOrganizationID)){
                        for(int k=0; k<mList.size(); k++){
                            String val2 = (String)mList.get(k);
                            if(val2.startsWith("CN=")){
                                if(parentDistinguishedName.equals(val2)){
                                    parentGroupName = (String)treeGroupMap.get(parentOrganizationID);
                                    break;
                                }
                            }
                        }
                        if(StringUtils.isBlank(parentGroupName)){
                            continue;
                        }
                    }
                }

                String fullGroupName = parentGroupName;
                if(parentGroupName!=null){
                    if(StringUtils.isNotBlank(parentGroupName)){
                        fullGroupName += ".";
                        leafGroupMap.put(parentOrganizationID, "1");
                    }
                    fullGroupName += displayName;
                }

                groupsCompareMap.put(organizationID, organizationID);
                treeGroupMap.put(organizationID, fullGroupName);
                leafGroupMap.put(organizationID, "0");

                map.put("fullGroupName", fullGroupName);//组全称 对应 OA中ORGNAMESTRING
                map.put("selfGroupName", selfGroupName);//组CN，唯一

                syncGroupMap.put(organizationID, map);

                if(map.get("groupType")!=null && map.get("member")!=null){
                    List ll = (List)map.get("member");
                    for(int j=0; j<ll.size(); j++){
                        String val2 = (String)ll.get(j);
                        if(val2.startsWith("CN=")){
                            String memberName = val2.substring(3, val2.indexOf(","));
                            if(isContainWithMap(oaGroupMap, val2)){
                                ret.put(memberName, getAllGroupsTree(distinguishedName, organizationID, memberName, treeGroupMap, leafGroupMap, syncGroupMap, groupsCompareMap));
                            }
                        }
                    }
                }
            }
        }
        return ret;
    }
    /**
     * 根据组名称获取指定的属性信息
     * @param groupName String
     * @return List
     */
    public List getGroupsByGroupName(String groupName) {
        if (groupName.contains("（") || groupName.contains("）")){
            groupName = groupName.replaceAll("（", "(");
            groupName = groupName.replaceAll("）", ")");
        }
        List ret = new ArrayList();
        StringBuffer filter_group = new StringBuffer();
        filter_group.append("(&(objectClass=group)");
        if(StringUtils.isNotBlank(groupName)){
            filter_group.append("(CN="+groupName+")");
        }
        filter_group.append("(!(objectClass=computer))(!(objectClass=inetOrgPerson)))");
        NamingEnumeration namingEnum = null;
        String[] atts={"cn", "name", "distinguishedName", "displayName", "objectGUID", "sAMAccountName", "sAMaccountType", "memberOf", "member", "groupType", "whenChanged", "organizationID"};
        try {
            SearchControls searchCons = new SearchControls();
            searchCons.setSearchScope(SearchControls.SUBTREE_SCOPE);
            searchCons.setCountLimit(0);
            searchCons.setTimeLimit(0);
            // 设置将被返回的Attribute
            searchCons.setReturningAttributes(atts);
            namingEnum = ctx.search("", filter_group.toString(), searchCons);

            //循环每个条目
            while (namingEnum != null && namingEnum.hasMore()) {
                SearchResult result = (SearchResult) namingEnum.next();
                Attributes attrs = result.getAttributes();
                if (attrs.size() != 0) {
                    Map map = new HashMap();
                    Object attValue = null;
                    for (NamingEnumeration namingEnum_1 = attrs.getAll();
                         namingEnum_1.hasMoreElements(); ) {
                        Attribute attribute = (Attribute) namingEnum_1.next();
                        String attID = attribute.getID();
                        for (NamingEnumeration namingEnum_2 = attribute.getAll();
                             namingEnum_2.hasMoreElements(); ) {
                            attValue = namingEnum_2.nextElement();
                            Object oldObj = (Object)map.get(attID);
                            if(oldObj != null){
                                List ll = (List)oldObj;
                                ll.add(attValue);
                            }else{
                                if (attID.equals("objectGUID")) {
                                    StringBuffer buffer = new StringBuffer();
                                    byte[] b = attValue.toString().getBytes();
                                    for (int i = 0; i < b.length; i++) {
                                        buffer.append(b[i]);
                                    }
                                    List ll = new ArrayList();
                                    ll.add(buffer.toString());
                                    map.put(attID, ll);
                                } else {
                                    List ll = new ArrayList();
                                    ll.add(attValue);
                                    map.put(attID, ll);
                                }
                            }
                        }
                    }
                    ret.add(map);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return ret;
    }

    //判断该map中是否包含制定值
    public boolean isContainWithMap(Map map, String member){
        Object temp = map.get(member);
        if(temp != null){
            return true;
        }
        return false;
    }


    /**
     * 构建组织数据
     * @param map
     * @param adIdMap <组织名称，AD>集合
     * @param groupMap <AD, office>系统现有组织结构
     * @return
     */
    public boolean ldapToDBForGroup(Map map, Map adIdMap, Map<String,Office> groupMap) {
        if(map == null) {
            return false;
        }
        try {
            Office parentOffic = new Office();

            //AD域组织全称
            String dn = map.get("fullGroupName").toString();

            if(dn.indexOf(".") < 0) {
                return false;
            }
            dn = dn.substring(dn.indexOf(".")+1);

            String orgName = ((List)map.get("cn")).get(0).toString();
            String parentOrgName;

            //组编码
            String organizationID = map.get("organizationID")==null || ((List)map.get("organizationID")).get(0)==null ? "" : ((List)map.get("organizationID")).get(0).toString();

            //组织编码
            String orgSerial;
            if(StringUtils.isNotBlank(organizationID)) {
                orgSerial = organizationID;
            }else{
                return false;
            }

            //获取删除组织的map集合
            unValidGroupMap.remove(organizationID);

            String[] ous = dn.split("\\.");
            int orgLevel = ous.length - 1;

            //查询数据库中是否已经存在这个组织
            //------------------------------------------------------
            //第一次根据组织编码进行判断
            //根据OAID组编码判断是否存在该组织

            //与上次更新时间作比较，如果大于上次更新时间，则更新组织；否则，不更新
            String whenChange = ((List)map.get("whenChanged")).get(0).toString();

            if(orgLevel == 0){
                parentOffic = zeroOffice;
                parentOrgName = zeroOffice.getName();
            }else{
                parentOrgName = ous[orgLevel -1];
                for(Office office : officeList){
                    if(office.getName().equals(parentOrgName)){
                        parentOffic = office;
                        break;
                    }
                }
            }

            Office office;
            if(groupMap.containsKey(orgSerial)) {

                if (updDateTime.equals("") || BigDecimal.valueOf(Long.valueOf(whenChange.substring(0, 14)))
                        .compareTo(BigDecimal.valueOf(Long.valueOf(updDateTime))) == 1) {
                    office = groupMap.get(orgSerial);
                    office.setAdId(orgSerial);
                    office.setParent(parentOffic);
                    office.setName(orgName);
                    office.setParentName(parentOrgName);
                    office.setGrade(Integer.toString(orgLevel+1));
                    office.setType("2");
                    office.setUseable("1");
                    office.setRemarks("AD");
                    // 把对象放入list进行批量操作
                    updAdGroupList.add(office);
                }
            } else {
                office = new Office();
                // 把数据放入对象中
                office.setAdId(orgSerial);
                office.setArea(new Area());
                office.setParent(parentOffic);
                office.setName(orgName);
                office.setParentName(parentOrgName);
                office.setGrade(Integer.toString(orgLevel+1));
                office.setCode("");
                office.setType("2");
                office.setDelFlag("0");
                office.setUseable("1");
                office.setArea(zeroArea);
                office.setRemarks("AD");
                // 把对象放入list进行批量操作
                addAdGroupList.add(office);
            }
            //父组织ID存放MAP
            adIdMap.put(orgName, orgSerial);

        } catch(Exception e) {
            logger.error("[AdSynchronous ldapToDBForGroup]", e);
        }
        return false;
    }

    /**
     * 构建账号数据
     * @param map
     * @param adIdMap
     * @param wcUserMap
     * @param wcOfficeList
     * @return
     */
    public boolean ldapToDBForUser(Map map, Map adIdMap,Map<String,User> wcUserMap, List<Office> wcOfficeList) {
        String account;
        try {
            //AD域用户账号
            String countName = ((List)map.get("sAMAccountName")).get(0).toString();
            account = ((List)map.get("sAMAccountName")).get(0).toString();
            String whenChange = ((List)map.get("whenChanged")).get(0).toString();
            //AD域用户是否有效
            String valid = ((List)map.get("userAccountControl")).get(0).toString();

            if(StringUtils.isBlank(account)) {
                return false;
            }

            //AD域组织全称
            String dn = map.get("fullGroupName").toString();
            if(dn.indexOf(".") < 0) {
                return false;
            }

            String[] officeArr = dn.split("\\.");
            //将存在的账号从map中移除
            unValidUserMap.remove(countName);

            //如果valid=512，则是有效用户，否则无效
            boolean isValid;
            if ("512".equals(valid)) {
                isValid = true;
            } else {
                isValid = false;
            }

            String empName = ((List)map.get("cn")).get(0).toString();
            String parentOrgNameString = officeArr[officeArr.length-1];

            if (updDateTime.equals("") || BigDecimal.valueOf(Long.valueOf(whenChange.substring(0, 14)))
                    .compareTo(BigDecimal.valueOf(Long.valueOf(updDateTime))) == 1) {

                String adId = adIdMap.get(parentOrgNameString).toString();
                //人员所属组织
                if(StringUtils.isNotBlank(adId)) {
                    Office office = new Office();
                    for(Office o : wcOfficeList){
                        if(adId.equals(o.getAdId())){
                            office = o;
                            break;
                        }
                    }
                    List<Role> roleList = Lists.newArrayList();
                    if(wcUserMap.containsKey(countName)) {
                        User user = wcUserMap.get(countName);
                        //如果该人员部门发生变化，则同步更新部门；否则不更新该人员部门
                        Office userOffice = wcUserMap.get(countName).getOffice();
                        Role role = new Role();
                        role.setUser(user);
                        roleList = systemService.findRole(role);
                        user.setRoleList(roleList);
                        user.setLoginName(countName);
                        user.setName(empName);
                        user.setDelFlag(isValid?"0":"1");
                        user.setLoginFlag(isValid?"1":"0");
                        user.setEmail(account+"@wfu.com");
                        user.setRemarks("AD");
                        if (!adId.equals(userOffice.getAdId())) {//部门变化
                            user.setOffice(office);
                            user.setCompany(office.getParent());
                            user.setAdId(adId);
                        }
                        upUserList.add(user);
                    } else {//新增用户
                        roleList.add(zeroRole);
                        User user = new User();
                        user.setOffice(office);
                        user.setCompany(office.getParent());
                        user.setLoginName(account);
                        user.setPassword(SystemService.entryptPassword("123"));
                        user.setName(empName);
                        user.setAdId(adId);
                        user.setRoleList(roleList);
                        user.setDelFlag(isValid?"0":"1");
                        user.setLoginFlag(isValid?"1":"0");
                        user.setEmail(account+"@wfu.com");
                        user.setRemarks("AD");
                        addAdUserList.add( user);
                    }
                }
            }
        }catch (Exception e){
            logger.error("[AdSynchronous ldapToDBForUser]", e);
        }
        return false;
    }

}
