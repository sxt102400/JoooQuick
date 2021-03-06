package com.shawn.jooo.module.dict.controller;


import com.shawn.jooo.framework.base.BaseController;
import com.shawn.jooo.framework.core.page.Page;
import com.shawn.jooo.framework.core.page.PageHelper;
import com.shawn.jooo.framework.core.response.Response;
import com.shawn.jooo.framework.core.response.Responses;
import com.shawn.jooo.framework.mybatis.condition.QueryHelper;
import com.shawn.jooo.module.dict.entity.SysDictType;
import com.shawn.jooo.module.dict.service.SysDictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 
 * @author jooo.gen
 */
@RestController
@RequestMapping("/sysDictType")
public class SysDictTypeController extends BaseController {

    @Autowired
    private SysDictTypeService sysDictTypeService;

    /**
     * 查询一条数据
     *
     * @param dictId
     * @return
     */
    @RequestMapping("/detail")
    @ResponseBody
    public Response detail(@RequestParam Integer dictId) {
        SysDictType sysDictType = sysDictTypeService.findById(dictId).get();
        return Responses.success(sysDictType);
    }

    /**
     * ajax分页查询，分页参数pageNo,pageSize
     *
     * @param sysDictType
     * @return
     */
    @RequestMapping( "/list")
    @ResponseBody
    public Response list(@RequestBody SysDictType sysDictType) {
        Page page = sysDictTypeService.findAll(QueryHelper.getExample(sysDictType),  PageHelper.getPage());
        return Responses.success(page);
    }

    /**
     * 保存
     *
     * @param sysDictType
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Response add(@RequestBody  SysDictType sysDictType) {
        sysDictTypeService.save(sysDictType);
        return Responses.success();
    }

    /**
     * 编辑
     *
     * @param sysDictType
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Response edit(@RequestBody SysDictType sysDictType) {
        sysDictTypeService.update(sysDictType);
        return Responses.success();
    }

    /**
     * 删除
     *
     * @param dictId
     * @return
     */
    @RequestMapping(value = "/remove")
    @ResponseBody
    public Response remove(@RequestParam Integer dictId) {
        sysDictTypeService.deleteById(dictId);
        return Responses.success();
    }

 }














