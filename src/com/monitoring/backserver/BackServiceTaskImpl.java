/*
package com.monitoring.backserver;

import com.monitoring.entity.Operator;
import com.monitoring.entity.Permission;
import com.monitoring.entity.User;
import com.monitoring.service.OperatorService;
import com.monitoring.service.UserService;
import com.monitoring.util.Pager;
import com.monitoring.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class BackServiceTaskImpl implements BackServiceTask {
    @Autowired
    OperatorService operatorService;

    */
/**
     * 每天0点执行该方法,查看每个用户的使用权限时间是否已到期，
     * 如果已到期，则禁用用户，将permission改为-1
     *//*

    @Scheduled(cron="0 1 0 * * ?")//每天0点延迟一分钟执行该方法
    public void checkUserPermissionTime() {
        //创建日期转换对象HH:mm:ss为时分秒，年月日为yyyy-MM-dd
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(df.format(new Date()) + "********每天0点问你们好******");
        Date date = new Date();
        date.setTime(0);
        Pager<Operator> pager = operatorService.getOperatorByHigherPermissionIdAndPermissionTime(1,1,date);
        List<Operator> operators =  operatorService.getOperators();

        if (operators != null) {
            try {
                for (int i=0;i<operators.size();i++) {
                    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                    Date bt = sdf.parse(TimeUtil.dateToStrNoTime(new Date()));
                    Date et = sdf.parse(TimeUtil.dateToStrNoTime(new Date()));
                    if (operators.get(i).getPermissionTime() == 0) {
                        et = sdf.parse(TimeUtil.dateToStrNoTime(operators.get(i).getPermissionTime()));
                    }
                    if (bt.before(et)){
                        //表示bt小于et
                        System.out.println(operators.get(i).getOperatorName()+"未到期");
                    }else{
                        System.out.println(operators.get(i).getOperatorName()+"已到期");
                        if (operators.get(i).getPermission() != Permission.PERMISSION1) {
                            if (operatorService.updateOperatorPermission(operators.get(i).getOperatorId(), -1)) {
                                System.out.println(operators.get(i).getOperatorName()+"到期已被禁用");
                            }
                        }

                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }


    }
}
*/
