/**
 * Copyright (C) 2018-2020
 * All rights reserved, Designed By www.yixiang.co
 * 注意：
 * 本软件为www.yixiang.co开发研制
 */
package co.yixiang.modules.system.rest;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.sax.Excel03SaxReader;
import cn.hutool.poi.excel.sax.handler.RowHandler;
import co.yixiang.annotation.Limit;
import co.yixiang.config.DataScope;
import co.yixiang.dozer.service.IGenerator;
import co.yixiang.exception.BadRequestException;
import co.yixiang.logging.aop.log.Log;
import co.yixiang.modules.aop.ForbidSubmit;
import co.yixiang.modules.mnt.service.dto.DatabaseDto;
import co.yixiang.modules.mnt.util.SqlUtils;
import co.yixiang.modules.system.domain.Dept;
import co.yixiang.modules.system.service.DeptService;
import co.yixiang.modules.system.service.dto.DeptDto;
import co.yixiang.modules.system.service.dto.DeptQueryCriteria;
import co.yixiang.utils.FileUtil;
import co.yixiang.utils.ValidationUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author hupeng
 * @date 2019-03-25
 */
@RestController
@Api(tags = "系统：部门管理")
@RequestMapping("/api/dept")
public class DeptController {

    private final DeptService deptService;

    private final DataScope dataScope;

    private final IGenerator generator;

    private static final String ENTITY_NAME = "dept";

    public DeptController(DeptService deptService, DataScope dataScope, IGenerator generator) {
        this.deptService = deptService;
        this.dataScope = dataScope;
        this.generator = generator;
    }

    private static List<List<Object>> lineList = new ArrayList<>();

    @Log("导出部门数据")
    @ApiOperation("导出部门数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','dept:list')")
    public void download(HttpServletResponse response, DeptQueryCriteria criteria) throws IOException {
        deptService.download(generator.convert(deptService.queryAll(criteria), DeptDto.class), response);
    }

    @Log("导入部门数据")
    @ApiOperation(value = "导入部门数据")
    @PostMapping(value = "/upload")
    @PreAuthorize("@el.check('admin','dept:list')")
    public ResponseEntity<Object> upload(@RequestBody MultipartFile file, HttpServletRequest request) throws Exception {
        String id = request.getParameter("id");
        String fileName = file.getOriginalFilename();
        //后缀
//        String substring = fileName.substring(fileName.lastIndexOf("."));
        // 上传文件为空
        if (StringUtils.isEmpty(fileName)) {
//            throw new OperationException(ReturnCodeEnum.OPERATION_EXCEL_ERROR, "没有导入文件");
        }
        //上传文件大小为1000条数据
        if (file.getSize() > 1024 * 1024 * 10) {
            System.out.println("upload | 上传失败: 文件大小超过10M，文件大小为：{}" + file.getSize());
//            throw new OperationException(ReturnCodeEnum.OPERATION_EXCEL_ERROR, "上传失败: 文件大小不能超过10M!");
        }
        // 上传文件名格式不正确
        if (fileName.lastIndexOf(".") != -1 && !".xlsx".equals(fileName.substring(fileName.lastIndexOf(".")))) {
//            throw new OperationException(ReturnCodeEnum.OPERATION_EXCEL_ERROR, "文件名格式不正确, 请使用后缀名为.XLSX的文件");
        }
        ExcelReader excelReader = ExcelUtil.getReader(file.getInputStream());
        List<List<Object>> excelData = excelReader.read(2, excelReader.getRowCount());
        List<Map<String, Object>> dataList = new ArrayList<>();
        for (int i = 0; i < lineList.size(); i++) {
            if (null != lineList.get(i)) {
                Map<String, Object> hashMap = new HashMap<>();
//                for (int j = 0; j < columNames.length; j++) {
//                    Object property = lineList.get(i).get(j);
//                    hashMap.put(columNames[j], property);
//                }
                dataList.add(hashMap);
            } else {
                break;
            }
        }
        String result = "";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Log("查询部门")
    @ApiOperation("查询部门")
    @GetMapping
    @PreAuthorize("@el.check('user:list','admin','dept:list')")
    public ResponseEntity<Object> getDepts(DeptQueryCriteria criteria) {
        // 数据权限
        criteria.setIds(dataScope.getDeptIds());
        List<DeptDto> deptDtos = generator.convert(deptService.queryAll(criteria), DeptDto.class);
        return new ResponseEntity<>(deptService.buildTree(deptDtos), HttpStatus.OK);
    }

    @Limit(key = "deptCreate", period = 60, count = 10, name = "组织新增限流", prefix = "deptCreateLimit")
    @Log("新增部门")
    @ApiOperation("新增部门")
    @PostMapping
    @PreAuthorize("@el.check('admin','dept:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Dept resources) {
        if (resources.getId() != null) {
            throw new BadRequestException("A new " + ENTITY_NAME + " cannot already have an ID");
        }
        return new ResponseEntity<>(deptService.save(resources), HttpStatus.CREATED);
    }

    @Limit(key = "deptUpdate", period = 60, count = 10, name = "组织修改限流", prefix = "deptUpdateLimit")
    @Log("修改部门")
    @ApiOperation("修改部门")
    @PutMapping
    @PreAuthorize("@el.check('admin','dept:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody Dept resources) {
        if (resources.getId().equals(resources.getPid())) {
            throw new BadRequestException("上级不能为自己");
        }
        Dept dept = deptService.getOne(new LambdaQueryWrapper<Dept>()
                .eq(Dept::getId, resources.getId()));
        ValidationUtil.isNull(dept.getId(), "Dept", "id", resources.getId());
        resources.setId(dept.getId());
        deptService.saveOrUpdate(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ForbidSubmit
    @Log("删除部门")
    @ApiOperation("删除部门")
    @DeleteMapping
    @PreAuthorize("@el.check('admin','dept:del')")
    public ResponseEntity<Object> delete(@RequestBody Set<Long> ids) {
        List<Long> deptIds = new ArrayList<>();
        for (Long id : ids) {
            List<Dept> deptList = deptService.findByPid(id);
            Dept dept = deptService.getOne(new LambdaQueryWrapper<Dept>().eq(Dept::getId, id));
            if (null != dept) {
                deptIds.add(dept.getId());
            }
            if (CollectionUtil.isNotEmpty(deptList)) {
                for (Dept d : deptList) {
                    deptIds.add(d.getId());
                }
            }
        }

        deptService.delDepts(deptIds);
//        try {
//            deptService.delDepts(deptIds);
//        }catch (Throwable e){
//            throw new BadRequestException( "所选部门中存在岗位或者角色关联，请取消关联后再试");
//        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
