/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.weichai.modules.material.dao;



import com.weichai.common.persistence.annotation.MyBatisDao;
import com.weichai.modules.filegen.entity.InOutDetail;
import com.weichai.modules.material.entity.Material;
import com.weichai.modules.timingtask.entity.ImtOiScan;

import java.util.List;

/**
 * 内外码明细DAO接口
 * @author 张丽
 * @version 2017-03-14
 */
@MyBatisDao
public interface MaterialDao {

    public void insertMaterial(List<ImtOiScan> inOutDetail);
    /*public List<Material> selectMaterialList();*/

    public Material selectMaterial(Material material);

}