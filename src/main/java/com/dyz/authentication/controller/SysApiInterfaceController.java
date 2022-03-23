package com.dyz.authentication.controller;

import com.dyz.authentication.entity.SysApiInterface;
import com.dyz.authentication.entity.Vo.SysApiInterfaceVo;
import com.dyz.authentication.service.SysApiInterfaceService;
import com.dyz.authentication.util.ResultUtil.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @ClassName SysApiInterfaceController
 * @Author Duan yuzhe
 * @Date 2020/9/3
 * @description api接口管理Controller层
 */
@RestController
@RequestMapping(value = "/apiInterface")
public class SysApiInterfaceController {

    @Autowired
    SysApiInterfaceService apiInterfaceService;

    /*
     * @Description 查询api列表
     **/
    @GetMapping
    public AjaxResult getAllList(){
        List<SysApiInterfaceVo> allList = apiInterfaceService.getAllList();
        return AjaxResult.success(allList);
    }

    /*
     * @Description 通过id获取api信息
     * @param id
     **/
    @GetMapping("/{id}")
    public AjaxResult getApiById(@PathVariable("id") String id){
        SysApiInterface apiInterface = apiInterfaceService.getById(id);
        return AjaxResult.success(apiInterface);
    }
    
    /*
     * @Description 添加api接口
     * @param sysApiInterface 
     **/
    @PostMapping
    public AjaxResult insert(@RequestBody SysApiInterface sysApiInterface){
        if (StringUtils.isEmpty(sysApiInterface.getPid())){
            sysApiInterface.setPid("NONE");
        }
        boolean save = apiInterfaceService.save(sysApiInterface);
        return save ? AjaxResult.success() : AjaxResult.error();
    }

    /*
     * @Description 修改api接口
     * @param sysApiInterface 
     **/
    @PutMapping
    public AjaxResult update(@RequestBody SysApiInterface sysApiInterface){
        boolean b = apiInterfaceService.updateById(sysApiInterface);
        return b ? AjaxResult.success() : AjaxResult.error();
    }

    /*
     * @Description 删除api接口
     * @param ids 
     **/
    @DeleteMapping
    public AjaxResult delete(@RequestParam("idList") List<String> ids){
        boolean delete = apiInterfaceService.delete(ids);
        return delete ? AjaxResult.success() : AjaxResult.error();
    }

}
