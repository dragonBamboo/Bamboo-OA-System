<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="审批类型" prop="submitterId">
<!--        <el-input-->
<!--          v-model="queryParams.approvalType"-->
<!--          placeholder="请选择审批类型"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
        <el-select
          v-model="queryParams.approvalType"
          placeholder="请选择审批类型"
        >
          <el-option
            v-for="item in typeOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['boa:examine:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['boa:examine:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['boa:examine:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['boa:examine:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="examineList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="提交人" align="center" prop="submitName"/>
      <el-table-column label="类型" align="center" prop="approvalType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.boa_examine_type" :value="scope.row.approvalType"/>
        </template>
      </el-table-column>
      <el-table-column label="具体事项" align="center" prop="message"/>
      <el-table-column label="审批状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.boa_examine_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
<!--          <el-button-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="el-icon-edit"-->
<!--            @click="handleUpdate(scope.row)"-->
<!--            v-hasPermi="['boa:examine:edit']"-->
<!--          >修改-->
<!--          </el-button>-->
          <el-button
            size="medium"
            type="primary"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['boa:examine:remove']"
          >撤销
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改审批管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="提交人" prop="submitName">
          <el-input v-model="form.submitName" type="text" placeholder="请输入内容" style="width: auto" disabled/>
        </el-form-item>
        <el-form-item label="审批类型" prop="submitterId">
          <el-select v-model="form.approvalType" clearable placeholder="请选择" style="width: auto">
            <el-option
              v-for="item in dict.type.boa_examine_type"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="具体事项" prop="message">
          <el-input v-model="form.message" type="textarea" placeholder="请输入内容"/>

        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {listExamine, getExamine, delExamine, addExamine, updateExamine} from "@/api/boa/examine";
import Cookies from "js-cookie";
import {userSmallList} from "@/api/boa/common";

export default {
  name: "Examine",
  dicts: ['boa_examine_type','boa_examine_status'],
  data() {
    return {
      submitName: Cookies.get('user') ? JSON.parse(Cookies.get('user')).user.nickName : '',
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 审批管理表格数据
      examineList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        approvalType: null,
        message: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      typeOptions:[
        {label: '请假',value:'1'},
        {label: '报销',value:'2'},
        {label: '采购',value:'3'},
      ],
    };
  },
  mounted() {

  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询审批管理列表 */
    getList() {
      this.loading = true;
      listExamine(this.queryParams).then(response => {
        this.examineList = response.rows.map(item => {
          item.approvalType = "" + item.approvalType
          return item
        });
        // console.log('examineList',this.examineList)
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        submitName: null,
        submitterId: null,
        approvalType: null,
        message: null,
        createTime: null,
        updateTime: null,
        isDeleted: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.queryParams.approvalType = null
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.form.submitName = this.submitName
      this.title = "添加审批事项";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getExamine(id).then(response => {
        let data = response.data
        data.approvalType = "" + data.approvalType
        this.form = data

        this.open = true;
        this.title = "修改审批事项";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateExamine(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addExamine(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认撤销该请求？').then(function () {
        return delExamine(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('boa/examine/export', {
        ...this.queryParams
      }, `examine_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
