package common.service.impl;

import common.service.IBaeReflectService;

/**
 * @author snail
 * 2020/8/31 2:12
 */
public class BaeReflectService implements IBaeReflectService {

    @Override
    public String demoStr(String val) {
        System.out.println("val: "+val);
        return val;
    }
}
