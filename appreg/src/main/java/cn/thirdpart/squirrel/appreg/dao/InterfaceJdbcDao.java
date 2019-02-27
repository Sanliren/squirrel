package cn.thirdpart.squirrel.appreg.dao;

import cn.thirdpart.squirrel.appreg.entity.AppInterfaceRelation;
import cn.thirdpart.squirrel.appreg.entity.AppReg;
import cn.thirdpart.squirrel.appreg.entity.Interface;
import cn.thirdpart.squirrel.appreg.rowmapper.AppInterfaceRelationRowMapper;
import cn.thirdpart.squirrel.appreg.rowmapper.AppRegRowMapper;
import cn.thirdpart.squirrel.appreg.rowmapper.InterfaceRowMapper;
import cn.thirdpart.squirrel.appreg.util.CommonUtil;
import cn.thirdpart.squirrel.appreg.vo.AppInterfaceRelationVo;
import cn.thirdpart.squirrel.appreg.vo.InterfaceVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class InterfaceJdbcDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 查找到是否存在有应用和接口关系数据
     * @param app_id
     * @param interface_id
     * @return
     */
    public AppInterfaceRelation find(String app_id, long interface_id){
        String sql = "select * from t_app_interface_r where app_id = ? and interface_id = ?";
        Object[] params = new Object[] {
                app_id,
                interface_id
        };
        List<AppInterfaceRelation> resultList = jdbcTemplate.query(sql, params, new AppInterfaceRelationRowMapper());
        if(resultList == null || resultList.isEmpty()){
            return  null;
        } else {
            return resultList.get(0);
        }
    }

    /**
     * 应用要申请接入接口
     * @param relationVo
     * @return
     */
    public boolean relationReg(AppInterfaceRelationVo relationVo){
        boolean flag = false;
        String sql = "insert into t_app_interface_r (id, app_id, interface_id, status, create_time, creator, modifier, modify_time) values(?, ?, ?, ?, ?, ?, ?, ?)";
        Object[] params = new Object[] {
                relationVo.getId(),
                relationVo.getAppId(),
                relationVo.getInterfaceId(),
                1,//表示可以进行访问
                relationVo.getCreateTime(),
                relationVo.getCreator(),
                relationVo.getModifier(),
                relationVo.getModifyTime()
        };

        int num = jdbcTemplate.update(sql, params);

        if(num > 0){
            flag = true;
            log.info("应用要申请接入接口成功！！");
        } else {
            flag = false;
            log.error("应用申请接口失败！！");
        }

        return flag;
    }

    /**
     * 新插入接口数据
     * @param interfaceVo
     * @return
     */
    public boolean interfaceReg(InterfaceVo interfaceVo){

        boolean flag = false;

        String sql = "insert into t_interface (name, status, address, method, description, address_example, format, icon, cost, amount, params_explain, result_explain, create_time, creator, modifier, modify_time) " +
                "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Object[] params = new Object[] {
            interfaceVo.getName(),
                1,//表示接口状态可以访问
            interfaceVo.getAddress(),
            interfaceVo.getMethod(),
            interfaceVo.getDescription(),
            interfaceVo.getAddressExample(),
            interfaceVo.getFormat(),
            interfaceVo.getIcon(),
            interfaceVo.getCost(),
            interfaceVo.getAmount(),
            interfaceVo.getParamsExplain(),
            interfaceVo.getResultExplain(),
            new Date(),
            interfaceVo.getCreator(),
            interfaceVo.getModifier(),
            new Date()
        };
        int num = jdbcTemplate.update(sql, params);

        if(num > 0){
            log.info("接口数据插入成功！！");
            flag = true;
        } else {
            log.info("接口数据插入失败！！");
            flag = false;
        }


        return flag;

    }


    public Interface findInterface(String interface_id){
        String sql = "select * from t_interface where id = ?";
        Object[] params = new Object[] {
             interface_id
        };
        List<Interface> resultList = jdbcTemplate.query(sql, params, new InterfaceRowMapper());

        if(resultList == null || resultList.isEmpty()){
            return  null;
        } else {
            return resultList.get(0);
        }

    }


    /**
     * 根据应用id和接口id获取两者关系记录数据
     * @param appkey
     * @param interface_id
     * @return
     */
    public AppInterfaceRelation findAppInterface(String appkey, long interface_id){
        String sql = "select * from t_app_interface_r where app_id = ? and interface_id = ?";
        Object[] params = new Object[]{
                appkey,
                interface_id
        };

        List<AppInterfaceRelation> resultList = jdbcTemplate.query(sql, params, new AppInterfaceRelationRowMapper());

        if(resultList == null || resultList.isEmpty()){
            return  null;
        } else {
            return resultList.get(0);
        }

    }

    /**
     * 通过id查找到应用和接口的关系记录数据
     * @param id
     * @return
     */
    public AppInterfaceRelation findAppInterface(String id){
        String sql = "select * from t_app_interface_r where id = ?";
        Object[] params = new Object[]{
                id
        };

        List<AppInterfaceRelation> resultList = jdbcTemplate.query(sql, params, new AppInterfaceRelationRowMapper());

        if(resultList == null || resultList.isEmpty()){
            return  null;
        } else {
            return resultList.get(0);
        }
    }

    /**
     * 查找应用所有申请的接口列表
     * @param appkey
     * @return
     */
    public List<Interface> findInterfacesByAppkey(String appkey){
        List<Interface> interfaces = new ArrayList<>();
        String sql = "select b.id, b.address, b.address_example, b.amount, b.status, b.cost, b.create_time, b.creator, b.modifier, b.modify_time, b.description, b.format, b.icon, b.method, b.`name`, b.params_explain, b.result_explain from t_app_interface_r t join t_app a on t.app_id = a.app_key join t_interface b on t.interface_id = b.id where t.app_id = ?";
        Object[] params = new Object[]{
                appkey
        };

        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, params);
        if(maps != null){
            for(Map<String, Object> map : maps){
                Interface anInterface = new Interface();
                anInterface.setId((long)map.get("id"));
                anInterface.setName((String) map.get("name"));
                anInterface.setDescription((String) map.get("description"));
                anInterface.setCost((int) map.get("cost"));
                anInterface.setAmount((int) map.get("amount"));
                anInterface.setStatus((int) map.get("status"));
                anInterface.setAddress((String) map.get("address"));
                anInterface.setAddressExample((String) map.get("address_example"));
                anInterface.setCreateTime((Timestamp) map.get("create_time"));
                anInterface.setCreator((String) map.get("creator"));
                anInterface.setFormat((String) map.get("format"));
                anInterface.setIcon((String) map.get("icon"));
                anInterface.setMethod((String) map.get("method"));
                anInterface.setModifier((String) map.get("modifier"));
                anInterface.setModifyTime((Timestamp) map.get("modify_time"));
                anInterface.setParamsExplain((String) map.get("params_explain"));
                anInterface.setResultExplain((String) map.get("result_explain"));

                interfaces.add(anInterface);
            }

        }
        if(interfaces.isEmpty() || interfaces.size() == 0){
            return  null;
        } else {

            return interfaces;
        }
    }


    /**
     * 查找所有的接口
     * @return
     */
    public List<Interface> findInterfaces(){
        List<Interface> interfaces = new ArrayList<>();
        String sql = "select b.id, b.status, b.address, b.address_example, b.amount, b.cost, b.create_time, b.creator, b.modifier, b.modify_time, b.description, b.format, b.icon, b.method, b.`name`, b.params_explain, b.result_explain from  t_interface b ";
        Object[] params = new Object[]{
        };

        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, params);
        if(maps != null){
            for(Map<String, Object> map : maps){
                Interface anInterface = new Interface();
                anInterface.setId((long)map.get("id"));
                anInterface.setName((String) map.get("name"));
                anInterface.setDescription((String) map.get("description"));
                anInterface.setCost((int) map.get("cost"));
                anInterface.setAmount((int) map.get("amount"));
                anInterface.setStatus((int) map.get("status"));
                anInterface.setAddress((String) map.get("address"));
                anInterface.setAddressExample((String) map.get("address_example"));
                anInterface.setCreateTime((Timestamp) map.get("create_time"));
                anInterface.setCreator((String) map.get("creator"));
                anInterface.setFormat((String) map.get("format"));
                anInterface.setIcon((String) map.get("icon"));
                anInterface.setMethod((String) map.get("method"));
                anInterface.setModifier((String) map.get("modifier"));
                anInterface.setModifyTime((Timestamp) map.get("modify_time"));
                anInterface.setParamsExplain((String) map.get("params_explain"));
                anInterface.setResultExplain((String) map.get("result_explain"));

                interfaces.add(anInterface);
            }

        }
        if(interfaces.isEmpty() || interfaces.size() == 0){
            return  null;
        } else {

            return interfaces;
        }
    }



}
