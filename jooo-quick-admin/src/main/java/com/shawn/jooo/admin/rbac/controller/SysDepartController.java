package com.shawn.jooo.admin.rbac.controller;


import com.shawn.jooo.framework.core.option.OptionHelper;
import com.shawn.jooo.framework.core.option.TreeOption;
import com.shawn.jooo.framework.core.tree.Tree;
import com.shawn.jooo.framework.core.tree.TreeHelper;
import com.shawn.jooo.framework.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.shawn.jooo.framework.mybatis.condition.QueryHelper;
import com.shawn.jooo.framework.core.page.Page;
import com.shawn.jooo.framework.core.page.PageHelper;
import com.shawn.jooo.framework.core.response.Response;
import com.shawn.jooo.framework.core.response.Responses;

import com.shawn.jooo.admin.rbac.entity.SysDepart;
import com.shawn.jooo.admin.rbac.service.SysDepartService;

import java.util.List;
import java.util.Objects;


/**
 *
 * 部门表
 *
 * @author jooo.gen
 */
@RestController
@RequestMapping("/sysDepart")
public class SysDepartController extends BaseController {

    @Autowired
    private SysDepartService sysDepartService;

    /**
     * 查询一条数据
     *
     * @param departId
     * @return
     */
    @RequestMapping("/detail")
    @ResponseBody
    public Response detail(@RequestParam Integer departId) {
        SysDepart sysDepart = sysDepartService.findById(departId).get();
        return Responses.success(sysDepart);
    }


    /**
     * ajax分页查询，分页参数pageNo,pageSize
     *
     * @param sysDepart
     * @return
     */
    @RequestMapping( "/list")
    @ResponseBody
    public Response list(@RequestBody(required=false) SysDepart sysDepart) {
        Page page = sysDepartService.findAll(QueryHelper.getExample(sysDepart),  PageHelper.getPage());
        return Responses.success(page);
    }

    /**
     * 保存
     *
     * @param sysDepart
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Response add(@RequestBody SysDepart sysDepart) {
        if(Objects.isNull(sysDepart.getParentId()) ) {
            sysDepart.setParentId(0);
        }
        sysDepartService.save(sysDepart);
        return Responses.success();
    }

    /**
     * 编辑
     *
     * @param sysDepart
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Response edit(@RequestBody SysDepart sysDepart) {
        sysDepartService.save(sysDepart);
        return Responses.success();
    }

    /**
     * 删除
     *
     * @param departId
     * @return
     */
    @RequestMapping(value = "/remove")
    @ResponseBody
    public Response remove(@RequestParam Integer departId) {
        sysDepartService.deleteById(departId);
        return Responses.success();
    }



    /**
     * ajax分页查询，分页参数pageNo,pageSize
     *
     * @param sysDepart
     * @return
     */
    @RequestMapping( "/tree")
    @ResponseBody
    public Response tree(@RequestBody(required=false) SysDepart sysDepart) {
        List<SysDepart> list = sysDepartService.findAll(QueryHelper.getExample(sysDepart));
        Tree<SysDepart> tree = TreeHelper.listToTree(list,"departId","parentId");
        return Responses.success(tree);
    }



    /**
     * ajax分页查询，分页参数pageNo,pageSize
     *
     * @param sysDepart
     * @return
     */
    @RequestMapping( "/treeSelect")
    @ResponseBody
    public Response treeSelect(@RequestBody(required=false) SysDepart sysDepart) {
        List list = sysDepartService.findAll(QueryHelper.getExample(sysDepart));
        List<TreeOption> options = OptionHelper.listToTreeOption(list,"departId","parentId","departName");
        return Responses.success(options);
    }



 }














