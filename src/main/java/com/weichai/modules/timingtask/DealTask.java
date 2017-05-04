package com.weichai.modules.timingtask;



import com.google.common.collect.Lists;
import com.weichai.common.utils.IdGen;
import com.weichai.modules.filegen.dao.InOutDetailDao;
import com.weichai.modules.filegen.dao.InOutDetailUsedDao;
import com.weichai.modules.filegen.service.InOutDetailUsedService;
import com.weichai.modules.material.entity.Material;
import com.weichai.modules.material.service.MaterialService;
import com.weichai.modules.timingtask.dao.ImtOiValidCodeDao;
import com.weichai.modules.timingtask.entity.ImtOiScan;
import com.weichai.modules.timingtask.entity.ImtOiValidCode;
import com.weichai.modules.timingtask.service.ImtOiScanService;
import com.weichai.modules.timingtask.service.ImtOiValidCodeService;
import com.weichai.modules.timingtask.service.MonitorFileService;
import com.weichai.modules.validatecode.entity.InOutProduct;
import com.weichai.modules.validatecode.service.InOutProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;


@Component
@Lazy(value=false)
public class DealTask {
    @Autowired
    private InOutDetailUsedService inOutDetailUsedService;
    @Autowired
    private ImtOiValidCodeService imtOiValidCodeService;
    @Autowired
    private ImtOiScanService imtOiScanService;
    @Autowired
    private MaterialService materialService;
    @Autowired
    private InOutProductService inOutProductService;


    @Autowired
    private MonitorFileService monitorFileService;

    //SCMST--SSP
    /* @Scheduled(cron = "0 30 20 * * ?")*/
    @Scheduled(cron = "0 0/30 * * * ?")
    public void doInOutDetailUsedTask(){
         System.out.println("----------------------doInOutDetailUsedTask---------------------------");
         //1. 从 in_out_detail_used表中取数据
        List<ImtOiValidCode> imtOiValidCodeList = Lists.newArrayList();
        System.out.println("start get from in_out_detail_used----"+new Date());
        imtOiValidCodeList = inOutDetailUsedService.getInOutDetailUsedList();
        System.out.println("end get from in_out_detail_used----"+new Date());


       // System.out.println("end insert IMT_OI_VALID_CODE----"+new Date());

        if(imtOiValidCodeList.size()>0){
            //2.取出的数据批量插入IMT_OI_VALID_CODE表中

            imtOiValidCodeService.batchAddImtOiValidCode(imtOiValidCodeList);
            //3.更新in_out_detail_used表中imt_status
            inOutDetailUsedService.batchUpdateInOutDetails(imtOiValidCodeList);
        }
        // System.out.println("end update IN_OUT_DETAILS----"+new Date());
        System.out.println("----------------------end---doInOutDetailUsedTask---------------------------");

      }

    //SSP--SCMST
    /*@Scheduled(cron = "0 30 20 * * ?")*/
    @Scheduled(cron = "0 0/30 * * * ?")
    public void doMaterielTask(){
        System.out.println("----------------------doMaterielTask---------------------------");
        //1.从 IMT_OI_SCAN表中取数据
        List<ImtOiScan> imtOiScanList = Lists.newArrayList();
       // System.out.println("start get from in_out_product----"+new Date());
        imtOiScanList = imtOiScanService.getInOutProductListUnion();
       // System.out.println("end get from in_out_product----"+new Date());

        if(imtOiScanList.size()>0){
            //2. 数据存到订单表
            materialService.batchAddMaterial(imtOiScanList);
            // System.out.println("end insert material----"+new Date());
            //3.数据存到三码关联表
            inOutProductService.batchAddInOutProduct(imtOiScanList);
            // System.out.println("end insert in_out_product----"+new Date());
            //4.更新IMT_OI_SCAN表imt_status状态
            imtOiScanService.batchUpdateImtScanStatus(imtOiScanList);
        }

        System.out.println("------------------end----doMaterielTask---------------------------");

        // System.out.println("end update imt_scan----"+new Date());
    }
    @Scheduled(cron = "0 30 20 * * ?")
    public void doMonitorFileTask(){
        monitorFileService.validateDownLoadCode();
    }

}
